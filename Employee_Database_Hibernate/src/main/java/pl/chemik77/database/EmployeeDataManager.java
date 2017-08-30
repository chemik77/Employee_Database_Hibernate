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

	public List<Employee> getAllEmployees() {
		
		connect();
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> employeeQuery = criteriaBuilder.createQuery(Employee.class);
		Root<Employee> employeeRoot = employeeQuery.from(Employee.class);
		employeeQuery.select(employeeRoot);
		TypedQuery<Employee> typedQuery = entityManager.createQuery(employeeQuery);
		List<Employee> employees = typedQuery.getResultList();

		disconnect();

		return employees;
	}
	
}
