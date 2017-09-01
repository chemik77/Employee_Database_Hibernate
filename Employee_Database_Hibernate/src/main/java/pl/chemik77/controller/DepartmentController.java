package pl.chemik77.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import pl.chemik77.database.DepartmentDataManager;
import pl.chemik77.model.Department;

@ManagedBean
@SessionScoped
public class DepartmentController {

	// --------FIELDS----------------

	private List<Department> departments;

	// --------FIELDS CONFIG---------

	private DepartmentDataManager departmentDataManager;

	// --------INITIALIZE----------------

	@PostConstruct
	private void init() {
		departmentDataManager = new DepartmentDataManager();
		departments = departmentDataManager.getAllDepartments();
	}

	// --------GETTERS AND SETTERS----------------

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

}
