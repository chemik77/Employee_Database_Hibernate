package pl.chemik77.database.dataManager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Path;

import pl.chemik77.database.utils.EMF;
import pl.chemik77.model.Department;
import pl.chemik77.model.Employee;
import pl.chemik77.model.PersonalInfo;

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

		Employee man = entityManager.find(Employee.class, department.getManager().getId());
		man.setDepartment(department);
		man.setDepartment_manager(department);
		department.setManager(man);

		List<Employee> employees = new ArrayList<Employee>();
		employees.add(man);
		department.setEmployees(employees);

		entityManager.persist(department);
		entityManager.getTransaction().commit();

		disconnect();
	}

	// SELECT e FROM Employee e WHERE lastName='word1'
	// AND firstName='word2'
	public Employee getEmployeeByName(String name) {
		String[] word = name.split(", ");
		connect();

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> q = cb.createQuery(Employee.class);

		Root<Employee> e = q.from(Employee.class);
		Path<String> lastName = e.get("lastName");
		Path<String> firstName = e.get("firstName");

		q.select(e).where(cb.and(cb.equal(lastName, word[0]), cb.equal(firstName, word[1])));

		TypedQuery<Employee> typedQuery = entityManager.createQuery(q);
		Employee employee = typedQuery.getSingleResult();

		disconnect();
		return employee;
	}

	// SELECT p FROM PersonalInfo p WHERE pesel='?'
	public PersonalInfo getPersonalInfoByPesel(String pesel) {
		connect();

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<PersonalInfo> q = cb.createQuery(PersonalInfo.class);

		Root<PersonalInfo> p = q.from(PersonalInfo.class);
		Path<String> peselPath = p.get("pesel");

		q.select(p).where(cb.equal(peselPath, pesel));

		TypedQuery<PersonalInfo> typedQuery = entityManager.createQuery(q);
		PersonalInfo personalInfo = typedQuery.getSingleResult();

		disconnect();
		return personalInfo;
	}

	public Employee getEmployeeByPesel(String pesel) {

		PersonalInfo personalInfo = getPersonalInfoByPesel(pesel);
		Employee employee = personalInfo.getEmployee();

		return employee;
	}
	
	// 
	public void updateDepartment(Department department) {
		connect();
		
		entityManager.getTransaction().begin();
		Employee manager = department.getManager();
		department.getEmployees().add(manager);
		manager.setDepartment(department);
		manager.setDepartment_manager(department);
		entityManager.merge(manager);
		entityManager.merge(department);
		entityManager.getTransaction().commit();
		
		disconnect();
	}

}
