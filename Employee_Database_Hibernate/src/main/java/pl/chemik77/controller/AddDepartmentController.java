package pl.chemik77.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import pl.chemik77.controller.utils.ContextUtil;
import pl.chemik77.database.dataManager.DepartmentDM;
import pl.chemik77.database.dataManager.EmployeeDM;
import pl.chemik77.model.Department;
import pl.chemik77.model.Employee;

@ManagedBean
@RequestScoped
public class AddDepartmentController {

	// --------FIELDS----------------
	private String name;
	private String managerPesel;
	private String phone;

	// --------FIELDS CONFIG---------
	private DepartmentDM departmentDM;
	private EmployeeDM employeeDM;

	// --------INITIALIZE----------------
	@PostConstruct
	private void init() {
		departmentDM = new DepartmentDM();
		employeeDM = new EmployeeDM();
	}

	// --------METHODS----------------
	public void addDepartment() throws IOException {

		Department department = new Department();
		department.setName(name);

		Employee employee = employeeDM.getEmployeeByPesel(managerPesel);
		department.setManager(employee);

		department.setPhone(phone);

		departmentDM.addDepartment(department);

		clearFields();

	}

	private void clearFields() throws IOException {
		ContextUtil.redirectNewForm();
	}

	// --------GETTERS AND SETTERS----------------
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManagerPesel() {
		return managerPesel;
	}

	public void setManagerPesel(String managerPesel) {
		this.managerPesel = managerPesel;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
