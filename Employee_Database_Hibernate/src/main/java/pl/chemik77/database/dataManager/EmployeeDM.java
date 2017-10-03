package pl.chemik77.database.dataManager;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import pl.chemik77.database.utils.*;
import pl.chemik77.model.*;

public class EmployeeDM {

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

	// SELECT p FROM PersonalInfo p WHERE pesel='?'
	public PersonalInfo getPersonalInfoByPesel(String pesel) {
		connect();

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<PersonalInfo> q = cb.createQuery(PersonalInfo.class);

		Root<PersonalInfo> p = q.from(PersonalInfo.class);
		Path<String> peselPath = p.get("pesel");

		q.select(p).where(cb.equal(peselPath, pesel));

		TypedQuery<PersonalInfo> typedQuery = entityManager.createQuery(q);

		List<PersonalInfo> pInfos = typedQuery.getResultList();
		PersonalInfo personalInfo = null;
		if (pInfos.size() != 0) {
			personalInfo = pInfos.get(0);
		}
		disconnect();
		return personalInfo;
	}

	// SELECT employee_id FROM personalinfo WHERE pesel='?';
	public Employee getEmployeeByPesel(String pesel) {

		PersonalInfo personalInfo = getPersonalInfoByPesel(pesel);
		Employee employee = null;
		if (personalInfo != null) {
			employee = personalInfo.getEmployee();
		}

		return employee;
	}

	// DELETE FROM Employee WHERE id='?'
	public void deleteEmployee(Employee emp) {
		connect();

		Employee employee = entityManager.find(Employee.class, emp.getId());
		clearEmployeeReferences(employee);

		entityManager.getTransaction().begin();
		entityManager.remove(employee);
		entityManager.getTransaction().commit();

		disconnect();
	}

	private void clearEmployeeReferences(Employee employee) {
		if (employee.getDepartment() != null) {
			employee.getDepartment().getEmployees().remove(employee);
		}
		if (employee.getDepartment_manager() != null) {
			employee.getDepartment_manager().setManager(null);
			employee.setDepartment_manager(null);
		}
		employee.setDepartment(null);

	}

	// INSERT INTO employee
	public void addEmployee(Employee employee) {
		connect();

		entityManager.getTransaction().begin();
		entityManager.persist(employee);
		entityManager.getTransaction().commit();

		disconnect();
	}

	// UPDATE Employee
	public void updateEmployee(Employee employee) {
		connect();

		entityManager.getTransaction().begin();
		if (employee.getDepartment_manager() != null) {
			if (!employee.getDepartment().equals(employee.getDepartment_manager())) {
				employee.setDepartment_manager(null);
			}
		}
		employee.setLastUpdate(LocalDateTime.now().withNano(0));
		entityManager.merge(employee);
		entityManager.getTransaction().commit();

		disconnect();
	}

	// SELECT e FROM Employee e WHERE id='?'
	public Employee findEmployeeById(int id) {
		connect();

		Employee employee = entityManager.find(Employee.class, id);

		disconnect();
		return employee;
	}
}
