package pl.chemik77.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import pl.chemik77.controller.utils.ContextUtil;
import pl.chemik77.database.dataManager.AddEmployeeDM;
import pl.chemik77.model.*;

@ManagedBean
@SessionScoped
public class EditEmployeeController {

	// --------FIELDS----------------

	private int id;
	private String firstName;
	private String lastName;
	private String office;
	private int salary;
	private LocalDate hireDate;

	private String street;
	private String houseNo;
	private String zipCode;
	private String city;
	private String country;

	private String email;
	private String phone;

	private String pesel;
	private Gender gender;
	private LocalDate birthDate;
	private String photo;

	private Employee selectedEmployee;
	
	private Department department;

	private List<Department> departments;

	// --------FIELDS CONFIG---------

	private AddEmployeeDM addEmployeeDM;

	// --------INITIALIZE----------------

	@PostConstruct
	private void init() {
		addEmployeeDM = new AddEmployeeDM();
		departments = addEmployeeDM.getAllDepartments();
	}

	// --------METHODS----------------

	public void selectEmployee(Employee employee) throws IOException {

		this.selectedEmployee = employee;
		ContextUtil.redirectTo("editEmployee.jsf");
		this.id = employee.getId();
		this.firstName = employee.getFirstName();
		this.lastName = employee.getLastName();
		this.office = employee.getOffice();
		this.salary = employee.getSalary();
		this.hireDate = employee.getHireDate();
		this.street = employee.getAddress().getStreet();
		this.houseNo = employee.getAddress().getHouseNo();
		this.zipCode = employee.getAddress().getZipCode();
		this.city = employee.getAddress().getCity();
		this.country = employee.getAddress().getCountry();
		this.email = employee.getContact().getEmail();
		this.phone = employee.getContact().getPhone();
		this.department = employee.getDepartment();
		this.pesel = employee.getPersonalInfo().getPesel();
		this.gender = employee.getPersonalInfo().getGender();
		this.birthDate = employee.getPersonalInfo().getBirthDate();
		this.photo = employee.getPersonalInfo().getPhoto();
	}

	public void saveEmployee() {
		selectedEmployee.setId(id);
		selectedEmployee.setFirstName(firstName);
		selectedEmployee.setLastName(lastName);
		selectedEmployee.setOffice(office);
		selectedEmployee.setSalary(salary);
		selectedEmployee.setHireDate(hireDate);
		Address address = selectedEmployee.getAddress();
		address.setStreet(street);
		address.setHouseNo(houseNo);
		address.setZipCode(zipCode);
		address.setCity(city);
		address.setCountry(country);
		Contact contact = selectedEmployee.getContact();
		contact.setEmail(email);
		contact.setPhone(phone);
		selectedEmployee.setDepartment(department);
		PersonalInfo personalInfo = selectedEmployee.getPersonalInfo();
		personalInfo.setPesel(pesel);
		personalInfo.setGender(gender);
		personalInfo.setBirthDate(birthDate);
		personalInfo.setPhoto(photo);
		
		addEmployeeDM.updateEmployee(selectedEmployee);
	}

	// --------GETTERS AND SETTERS----------------

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

}
