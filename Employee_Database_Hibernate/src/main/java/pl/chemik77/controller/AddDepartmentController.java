package pl.chemik77.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pl.chemik77.database.dataManager.AddDepartmentDM;
import pl.chemik77.model.Contact;
import pl.chemik77.model.Department;
import pl.chemik77.model.Employee;
import pl.chemik77.model.Phone;

@ManagedBean
@ViewScoped
public class AddDepartmentController {

	// --------FIELDS----------------
	private String name;
	private String manager;
	private String email;
	private String phone;

	// --------FIELDS CONFIG---------
	private AddDepartmentDM addDepartmentDM;

	// --------INITIALIZE----------------
	@PostConstruct
	private void init() {
		addDepartmentDM = new AddDepartmentDM();
	}

	// --------METHODS----------------
	public void addDepartment() {
		Department department = new Department();
		department.setName(name);

		String managerName = manager;
		String[] word = managerName.split(" ");
		Employee employee = addDepartmentDM.getEmployeeByName(word[0], word[1]);
		department.setManager(employee);
		employee.setDepartment(department);

		List<Employee> employees = new ArrayList<Employee>();
		employees.add(employee);
		department.setEmployees(employees);

		Contact contact = new Contact();
		contact.setEmail(email);
		List<Phone> phones = new ArrayList<Phone>();
		Phone phone = new Phone();
		phone.setType("Reception");
		phone.setNumber(this.phone);
		phones.add(phone);
		contact.setPhones(phones);

		department.setContact(contact);

		addDepartmentDM.addDepartment(department);

		reset();
	}

	public void reset() {
		setName("");
	}

	// --------GETTERS AND SETTERS----------------
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
