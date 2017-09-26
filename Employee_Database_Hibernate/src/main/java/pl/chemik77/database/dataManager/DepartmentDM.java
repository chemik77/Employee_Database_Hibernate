package pl.chemik77.database.dataManager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import pl.chemik77.database.utils.*;
import pl.chemik77.model.Department;
import pl.chemik77.model.Employee;

public class DepartmentDM {

	private EntityManager entityManager;

	private void connect() {
		entityManager = EMF.createEntityManager();
	}

	private void disconnect() {
		entityManager.close();
	}

	// SELECT d FROM Depratment d ORDER BY name
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

	// SELECT d FROM Department d WHERE name=?
	public Department getDepartmentByName(String name) {

		connect();

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Department> q = cb.createQuery(Department.class);

		Root<Department> d = q.from(Department.class);
		q.select(d).where(cb.equal(d.get("name"), name));

		TypedQuery<Department> typedQuery = entityManager.createQuery(q);
		Department department = typedQuery.getSingleResult();

		disconnect();
		return department;
	}

	public void deleteDepartment(Department dept) {
		connect();

		Department department = entityManager.find(Department.class, dept.getId());
		clearDepartmentReferences(department);
		
		entityManager.getTransaction().begin();
		entityManager.remove(department);
		entityManager.getTransaction().commit();

		disconnect();
	}
	
	private void clearDepartmentReferences(Department department) {
		if(department.getManager() != null) {
			department.getManager().setDepartment_manager(null);
		}
		department.setManager(null);
		if(department.getEmployees() != null) {
			for(Employee employee : department.getEmployees()) {
				employee.setDepartment(null);
			}
			department.getEmployees().clear();
		}
		
		
	}
}
