package pl.chemik77;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import pl.chemik77.model.Address;
import pl.chemik77.model.Contact;
import pl.chemik77.model.Department;
import pl.chemik77.model.Employee;
import pl.chemik77.model.Gender;
import pl.chemik77.model.PersonalInfo;
import pl.chemik77.model.Phone;

public class Main {

	public static void main(String[] args) {

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("manager1");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		fillDatabase(entityManager);

		entityManager.close();
		entityManagerFactory.close();
	}

	private static void fillDatabase(EntityManager entityManager) {

		Department department = new Department();
		Employee manager = new Employee();
		Contact contact_dep = new Contact();
		Contact contact_man = new Contact();

		Employee employee = new Employee();
		Address address_man = new Address();
		Address address_emp = new Address();
		PersonalInfo personalInfo_man = new PersonalInfo();
		PersonalInfo personalInfo_emp = new PersonalInfo();
		Contact contact_emp = new Contact();

		Phone phone_dep = new Phone();
		Phone phone_man1 = new Phone();
		Phone phone_man2 = new Phone();
		Phone phone_emp = new Phone();

		department.setName("Production");
		department.setManager(manager);
		department.setContact(contact_dep);
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(manager);
		employees.add(employee);
		department.setEmployees(employees);

		manager.setFirstName("Maria");
		manager.setLastName("Wilczynska");
		manager.setOffice("General Manager of Production");
		manager.setSalary(8500);
		manager.setHireDate(LocalDate.parse("2010-05-01"));
		manager.setAddress(address_man);
		manager.setContact(contact_man);
		manager.setDepartment(department);
		manager.setPersonalInfo(personalInfo_man);

		employee.setFirstName("Jerzy");
		employee.setLastName("Kowalski");
		employee.setOffice("Quality Production Engineer");
		employee.setSalary(4200);
		employee.setHireDate(LocalDate.parse("2015-05-01"));
		employee.setAddress(address_emp);
		employee.setContact(contact_emp);
		employee.setDepartment(department);
		employee.setPersonalInfo(personalInfo_emp);

		address_man.setStreet("Opolska");
		address_man.setHouseNo("15");
		address_man.setZipCode("05-520");
		address_man.setCity("Bydgoszcz");
		address_man.setCountry("Polska");

		address_emp.setStreet("Diamentowa");
		address_emp.setHouseNo("1/2");
		address_emp.setZipCode("00-254");
		address_emp.setCity("Warszawa");
		address_emp.setCountry("Polska");

		contact_dep.setEmail("production_dept@company.com");
		List<Phone> phones_dep = new ArrayList<Phone>();
		phones_dep.add(phone_dep);
		contact_dep.setPhones(phones_dep);

		contact_man.setEmail("mwilczynska@company.com");
		List<Phone> phones_man = new ArrayList<Phone>();
		phones_man.add(phone_man1);
		phones_man.add(phone_man2);
		contact_man.setPhones(phones_man);

		contact_emp.setEmail("jkowalski@company.com");
		List<Phone> phones_emp = new ArrayList<Phone>();
		phones_emp.add(phone_emp);
		contact_emp.setPhones(phones_emp);

		phone_dep.setType("Reception");
		phone_dep.setNumber("71 524 89 01");
		phone_man1.setType("Work");
		phone_man1.setNumber("71 524 89 74");
		phone_man2.setType("Private");
		phone_man2.setNumber("852 456 112");
		phone_emp.setType("Private");
		phone_emp.setNumber("508 525 455");

		personalInfo_man.setPesel("74091121005");
		personalInfo_man.setGender(Gender.F);
		personalInfo_man.setBirthDate(LocalDate.parse("1974-09-11"));
		personalInfo_man.setPhoto("photos/production/wilczynska_maria.png");
		personalInfo_man.setEmployee(manager);

		personalInfo_emp.setPesel("81011721005");
		personalInfo_emp.setGender(Gender.M);
		personalInfo_emp.setBirthDate(LocalDate.parse("1981-01-17"));
		personalInfo_emp.setPhoto("photos/production/kowalski_jerzy.png");
		personalInfo_emp.setEmployee(employee);

		entityManager.getTransaction().begin();
		entityManager.persist(department);
		entityManager.getTransaction().commit();

	}
}
