package pl.chemik77.database;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import pl.chemik77.database.utils.EMF;
import pl.chemik77.model.Department;

public class DepartmentDataManager {
	private EntityManager entityManager;

	private void connect() {
		entityManager = EMF.createEntityManager();
	}

	private void disconnect() {
		entityManager.close();
	}
	
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
