package pl.chemik77.database.dataManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Path;

import pl.chemik77.database.utils.EMF;
import pl.chemik77.model.Department;
import pl.chemik77.model.Employee;

public class AddDepartmentDM {

	private EntityManager entityManager;

	private void connect() {
		entityManager = EMF.createEntityManager();
	}

	private void disconnect() {
		entityManager.close();
	}

	// 1. INSERT INTO department
	// 2. UPDATE employee SET department='new_department' WHERE
	// employee=department.manager
	public void addDepartment(Department department) {
		connect();

		entityManager.getTransaction().begin();
		entityManager.persist(department);
		Employee oldEmployee = entityManager.find(Employee.class, department.getManager().getId());
		oldEmployee.setDepartment(department);
		entityManager.getTransaction().commit();

		disconnect();
	}

	// SELECT e FROM Employee e WHERE lastName='word1'
	// AND firstName='word2'
	public Employee getEmployeeByName(String word1, String word2) {
		connect();

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> q = cb.createQuery(Employee.class);

		Root<Employee> e = q.from(Employee.class);
		Path<String> lastName = e.get("lastName");
		Path<String> firstName = e.get("firstName");

		q.select(e).where(cb.and(cb.equal(lastName, word1), cb.equal(firstName, word2)));

		TypedQuery<Employee> typedQuery = entityManager.createQuery(q);
		Employee employee = typedQuery.getSingleResult();

		disconnect();
		return employee;
	}

}
