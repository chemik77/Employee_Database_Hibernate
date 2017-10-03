package pl.chemik77.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.NoResultException;

import pl.chemik77.controller.utils.MessageUtil;
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
	public void addDepartment() {

		Department department = new Department();
		department.setName(name);

		Employee employee = null;
		try {
			employee = employeeDM.getEmployeeByPesel(managerPesel);
		} catch (NoResultException nre) {
			MessageUtil.showErrorMessage("Nie znaleziono pracownika o podanym peselu!");
		}

		department.setManager(employee);
		department.setPhone(phone);

		Department departmentByName = departmentDM.getDepartmentByName(name);
		if (department.equals(departmentByName)) {
			MessageUtil.showErrorMessage("Department exists!");
		} else {
			departmentDM.addDepartment(department);

			MessageUtil.addInfoMessage("New department saved");

		}

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
