package pl.chemik77.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import pl.chemik77.database.utils.EMF;
import pl.chemik77.model.Employee;

public class EmployeeDataManager {

	private EntityManager entityManager;

	private void connect() {
		entityManager = EMF.createEntityManager();
	}

	private void disconnect() {
		entityManager.close();
	}

	// SELECT e FROM Employee e ORDER BY last_name;
	public List<Employee> getAllEmployees() {
		
		connect();
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> q = cb.createQuery(Employee.class);
		
		Root<Employee> e = q.from(Employee.class);
		
		q.select(e).orderBy(cb.asc(e.get("lastName")));
		
		TypedQuery<Employee> typedQuery = entityManager.createQuery(q);
		List<Employee> employees = typedQuery.getResultList();

		disconnect();

		return employees;
	}
	
}
