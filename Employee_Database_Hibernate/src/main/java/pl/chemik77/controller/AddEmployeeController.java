package pl.chemik77.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import pl.chemik77.database.dataManager.AddEmployeeDM;
import pl.chemik77.model.*;

@ManagedBean
@ViewScoped
public class AddEmployeeController {

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

	private Department department;

	private String pesel;
	private Gender gender;
	private LocalDate birthDate;
	private String photo;
	
	private AddEmployeeDM addEmployeeDM;
	
	@PostConstruct
	private void init() {
		addEmployeeDM = new AddEmployeeDM();
	}
	
	public void addEmployee() {
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setOffice(office);
		employee.setSalary(salary);
		//employee.setHireDate(hireDate);
		
		Department department = new Department();
		employee.setDepartment(department);
		
		Address address = new Address();
		address.setStreet(street);
		address.setHouseNo(houseNo);
		address.setZipCode(zipCode);
		address.setCity(city);
		address.setCountry(country);
		
		employee.setAddress(address);
		
		Contact contact = new Contact();
		contact.setEmail(email);
		Phone phone = new Phone();
		phone.setType("work");
		phone.setNumber(this.phone);
		List<Phone> phones = new ArrayList<>();
		phones.add(phone);
		contact.setPhones(phones);
		
		employee.setContact(contact);
		
		PersonalInfo personalInfo = new PersonalInfo();
		personalInfo.setPesel(pesel);
		personalInfo.setGender(gender);
		//personalInfo.setBirthDate(birthDate);
		personalInfo.setPhoto(photo);
		
		employee.setPersonalInfo(personalInfo);
		personalInfo.setEmployee(employee);
		
		addEmployeeDM.addEmployee(employee, department);
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

}
