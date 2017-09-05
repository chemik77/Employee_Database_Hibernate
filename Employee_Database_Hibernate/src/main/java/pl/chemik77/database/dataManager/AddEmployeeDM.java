package pl.chemik77.database.dataManager;

import javax.persistence.EntityManager;

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

	// syso new Employee
	public void addEmployee(Employee employee, Department department) {
		connect();
		 
		entityManager.getTransaction().begin();
		entityManager.persist(department);
		entityManager.persist(employee);
		entityManager.getTransaction().commit();
		
		disconnect();
	}
}
