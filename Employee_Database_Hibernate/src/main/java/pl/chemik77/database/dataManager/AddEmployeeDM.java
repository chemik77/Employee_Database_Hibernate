package pl.chemik77.database.dataManager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import pl.chemik77.database.utils.EMF;
import pl.chemik77.model.Department;
import pl.chemik77.model.Employee;

public class AddEmployeeDM {

	private EntityManager entityManager;

	private void connect() {
		entityManager = EMF.createEntityManager();
	}

	private void disconnect() {
		entityManager.close();
	}

	// INSERT INTO employees
	public void addEmployee(Employee employee) {
		connect();
		 
		entityManager.getTransaction().begin();
		entityManager.persist(employee);
		entityManager.getTransaction().commit();
		
		disconnect();
	}
	
	// SELECT d FROM Department d ORDER BY name;
	public List<Department> getAllDepartments() {
		connect();
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Department> q = cb.createQuery(Department.class);
		
		Root<Department> d = q.from(Department.class);
		q.select(d).orderBy(cb.asc(d.get("name")));
		
		TypedQuery<Department> typedQuery = entityManager.createQuery(q);
		List<Department> departments = typedQuery.getResultList();
		
		disconnect();
		return departments;
	}
}
