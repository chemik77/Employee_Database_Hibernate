package pl.chemik77.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.NoResultException;

import pl.chemik77.controller.utils.ContextUtil;
import pl.chemik77.controller.utils.MessageUtil;
import pl.chemik77.database.dataManager.DepartmentDM;
import pl.chemik77.database.dataManager.EmployeeDM;
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
	private DepartmentDM departmentDM;
	private EmployeeDM employeeDM;

	// --------INITIALIZE----------------
	@PostConstruct
	private void init() {
		departmentDM = new DepartmentDM();
		employeeDM = new EmployeeDM();
	}

	// --------METHODS----------------

	public void selectDepartment(Department department) {

		this.selectedDepartment = department;
		try {
			ContextUtil.redirectTo("editDepartment.jsf");
		} catch (IOException e) {
			MessageUtil.showErrorMessage(e.getMessage());
		}
		this.id = department.getId();
		this.name = department.getName();
		if (department.getManager() != null) {
			this.managerPesel = department.getManager().getPersonalInfo().getPesel();
		}
		this.phone = department.getPhone();

	}

	public void saveDepartment() {
		
		selectedDepartment.setId(id);
		selectedDepartment.setName(name);
		Employee employeeByPesel = null;
		try {
			employeeByPesel = employeeDM.getEmployeeByPesel(managerPesel);
		} catch (NoResultException nre) {
			MessageUtil.showErrorMessage("Employee not found by given pesel!");
		}
		Employee oldManager = selectedDepartment.getManager();
		selectedDepartment.setManager(employeeByPesel);
		selectedDepartment.setPhone(phone);

		departmentDM.updateDepartment(selectedDepartment, oldManager);

		MessageUtil.addInfoMessage("Department updated");

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
