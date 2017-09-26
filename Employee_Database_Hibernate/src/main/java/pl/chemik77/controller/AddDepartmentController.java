package pl.chemik77.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import pl.chemik77.controller.utils.ContextUtil;
import pl.chemik77.database.dataManager.AddDepartmentDM;
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
	private AddDepartmentDM addDepartmentDM;

	// --------INITIALIZE----------------
	@PostConstruct
	private void init() {
		addDepartmentDM = new AddDepartmentDM();
	}

	// --------METHODS----------------
	public void addDepartment() throws IOException {

		Department department = new Department();
		department.setName(name);

		Employee employee = addDepartmentDM.getEmployeeByPesel(managerPesel);
		department.setManager(employee);

		department.setPhone(phone);

		addDepartmentDM.addDepartment(department);

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
