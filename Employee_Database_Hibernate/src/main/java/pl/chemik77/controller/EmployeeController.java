package pl.chemik77.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import pl.chemik77.database.EmployeeDataManager;
import pl.chemik77.model.Employee;

@ManagedBean
@SessionScoped
public class EmployeeController {

	// --------FIELDS----------------

	private List<Employee> employees;

	// --------FIELDS CONFIG---------

	private EmployeeDataManager employeeDataManager;

	// --------INITIALIZE----------------

	@PostConstruct
	private void init() {
		employeeDataManager = new EmployeeDataManager();
		employees = employeeDataManager.getAllEmployees();

	}

	// --------GETTERS AND SETTERS----------------

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

}
