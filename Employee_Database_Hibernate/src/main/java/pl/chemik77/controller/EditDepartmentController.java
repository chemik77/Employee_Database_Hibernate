package pl.chemik77.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import pl.chemik77.controller.utils.ContextUtil;
import pl.chemik77.database.dataManager.AddDepartmentDM;
import pl.chemik77.model.Department;
import pl.chemik77.model.Employee;

@ManagedBean
@SessionScoped
public class EditDepartmentController {

	// --------FIELDS----------------
	private int id;
	private String name;
	private String managerPesel;
	private String phone;

	private Department selectedDepartment;

	// --------FIELDS CONFIG---------
	private AddDepartmentDM addDepartmentDM;

	// --------INITIALIZE----------------
	@PostConstruct
	private void init() {
		addDepartmentDM = new AddDepartmentDM();
	}

	// --------METHODS----------------

	public void selectDepartment(Department department) throws IOException {

		this.selectedDepartment = department;
		ContextUtil.redirectTo("editDepartment.jsf");
		this.id = department.getId();
		this.name = department.getName();
		if (department.getManager() != null) {
			this.managerPesel = department.getManager().getPersonalInfo().getPesel();
		}
		this.phone = department.getPhone();

	}

	public void saveDepartment() throws IOException {
		selectedDepartment.setId(id);
		selectedDepartment.setName(name);
		Employee employeeByPesel = addDepartmentDM.getEmployeeByPesel(managerPesel);
		selectedDepartment.setManager(employeeByPesel);
		selectedDepartment.setPhone(phone);

		addDepartmentDM.updateDepartment(selectedDepartment);

		ContextUtil.redirectTo("departments.jsf");
	}

	// --------GETTERS AND SETTERS----------------

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public Department getSelectedDepartment() {
		return selectedDepartment;
	}

	public void setSelectedDepartment(Department selectedDepartment) {
		this.selectedDepartment = selectedDepartment;
	}

}
