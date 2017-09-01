package pl.chemik77.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
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

	// SELECT e FROM Employee e ORDER BY lastName;
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
	
	// SELECT e FROM Employee e WHERE lastName LIKE 'letter%' ORDER BY lastName
	public List<Employee> getEmployeesLetter(String letter) {
		connect();
		
		String param = letter.concat("%");
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> q = cb.createQuery(Employee.class);
		
		Root<Employee> e = q.from(Employee.class);
		Path<String> path = e.get("lastName");
		
		q.select(e).where(cb.like(path, param)).orderBy(cb.asc(path));
		
		TypedQuery<Employee> typedQuery = entityManager.createQuery(q);
		List<Employee> employees = typedQuery.getResultList();
		
		disconnect();
		return employees;
	}
	
	// SELECT e FROM Employee e WHERE lastName LIKE '%word%' ORDER BY lastName
	public List<Employee> getEmployeesWord(String word) {
		connect();
		
		String param = "%" + word + "%";
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> q = cb.createQuery(Employee.class);
		
		Root<Employee> e = q.from(Employee.class);
		Path<String> path = e.get("lastName");
		
		q.select(e).where(cb.like(path, param)).orderBy(cb.asc(path));
		
		TypedQuery<Employee> typedQuery = entityManager.createQuery(q);
		List<Employee> employees = typedQuery.getResultList();
	
		disconnect();
		return employees;
	}
	
}
