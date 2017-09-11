package pl.chemik77.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pl.chemik77.database.dataManager.DepartmentDM;
import pl.chemik77.model.Department;

@ManagedBean
@ViewScoped
public class DepartmentController {

	// --------FIELDS----------------

	private List<Department> departments;

	// --------FIELDS CONFIG---------

	private DepartmentDM departmentDataManager;

	// --------INITIALIZE----------------

	@PostConstruct
	private void init() {
		departmentDataManager = new DepartmentDM();
		departments = departmentDataManager.getAllDepartments();
	}
	
	// --------METHODS----------------

	// --------GETTERS AND SETTERS----------------

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

}
