package pl.chemik77.database.dataManager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import pl.chemik77.database.utils.EMF;
import pl.chemik77.model.Department;

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
	
}
