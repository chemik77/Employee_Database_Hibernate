package pl.chemik77.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.chemik77.model.Employee;

@RunWith(Arquillian.class)
public class EmployeeTest {

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Employee.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction utx;

	private final String SQL_EMPLOYEE = "SELECT e FROM Employee e ORDER BY e.id";
	private final String SQL_ADDRESS = "SELECT a FROM Address a ORDER BY a.id";
	private final String SQL_CONTACT = "SELECT c FROM Contact c ORDER BY c.id";
	private final String SQL_PERSONAL = "SELECT p FROM PersonalInfo p ORDER BY p.id";

	@Before
	public void preparePersistenceTest() throws Exception {
		clearData();
		startTransaction();
	}

	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Dumping old records...");
		em.createQuery("DELETE FROM PersonalInfo").executeUpdate();
		em.createQuery("DELETE FROM Phone").executeUpdate();
		em.createQuery("DELETE FROM Address").executeUpdate();
		em.createQuery("DELETE FROM Contact").executeUpdate();
		em.createQuery("DELETE FROM Employee").executeUpdate();
		em.createQuery("DELETE FROM Department").executeUpdate();
		utx.commit();
	}

	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}

	@After
	public void commitTransaction() throws Exception {
		utx.commit();
	}

	@Test
	@InSequence(1)
	public void shouldPersistsEmployee() throws Exception {
		System.out.println("Persist new employees...");

		// given
		List<Employee> employees = new ArrayList<>();
		Employee employee1 = new Employee();
		employee1.setLastName("Zaremba");
		employee1.setFirstName("Mateusz");
		employee1.setOffice("Manager");
		employee1.setSalary(5200);
		employee1.setHireDate(LocalDate.parse("2017-05-04"));
		employees.add(employee1);

		Employee employee2 = new Employee();
		employee2.setLastName("Wiśniewska");
		employee2.setFirstName("Alicja");
		employee2.setOffice("Leader");
		employee2.setSalary(3900);
		employee2.setHireDate(LocalDate.parse("2016-12-14"));
		employees.add(employee2);

		// when
		em.persist(employee1);
		em.persist(employee2);
		em.flush();
		em.clear();

		List<Employee> retrievedEmployees = em.createQuery(SQL_EMPLOYEE, Employee.class)
				.getResultList();

		// then
		Assert.assertEquals(employees.size(), retrievedEmployees.size());
		Assert.assertTrue(employees.contains(retrievedEmployees.get(0)));
	}

	@Test
	@InSequence(2)
	public void shouldPersistEmployeeWithAddress() {
		System.out.println("Persist new employee with address...");

		// given
		List<Employee> employees = new ArrayList<>();

		Employee employee1 = new Employee();
		employee1.setLastName("Wiśniewska");
		Employee employee2 = new Employee();
		employee2.setLastName("Zaremba");
		Address address1 = new Address();
		address1.setStreet("Jesionowa");
		address1.setHouseNo("2");
		address1.setZipCode("50-654");
		address1.setCity("Wrocław");
		address1.setCountry("Polska");
		address1.setEmployee(employee1);
		Address address2 = new Address();
		address2.setStreet("Główna");
		address2.setHouseNo("1/12");
		address2.setZipCode("00-322");
		address2.setCity("Warszawa");
		address2.setCountry("Polska");
		address2.setEmployee(employee2);

		employee1.setAddress(address1);
		employees.add(employee1);
		employee2.setAddress(address2);
		employees.add(employee2);

		// when
		em.persist(employee1);
		em.persist(employee2);
		em.flush();
		em.clear();

		List<Employee> retrievedEmployees = em.createQuery(SQL_EMPLOYEE, Employee.class).getResultList();
		List<Address> retrievedAddresses = em.createQuery(SQL_ADDRESS, Address.class).getResultList();

		// then
		Assert.assertEquals(employees.size(), retrievedEmployees.size());
		Assert.assertTrue(employees.contains(retrievedEmployees.get(0)));
		Assert.assertEquals(2, retrievedAddresses.size());
		Assert.assertTrue(retrievedAddresses.contains(employees.get(0).getAddress()));
	}

	@Test
	@InSequence(3)
	public void shouldPersistEmployeeWithContact() {
		System.out.println("Persist new employee with contact...");

		// given
		List<Employee> employees = new ArrayList<>();

		Employee employee1 = new Employee();
		employee1.setLastName("Wiśniewska");
		Employee employee2 = new Employee();
		employee2.setLastName("Zaremba");
		Contact contact1 = new Contact();
		contact1.setEmail("awisniewska@company.com");
		contact1.setEmployee(employee1);

		Contact contact2 = new Contact();
		contact2.setEmail("mzaremba@company.com");
		contact2.setEmployee(employee2);

		employee1.setContact(contact1);
		employees.add(employee1);
		employee2.setContact(contact2);
		employees.add(employee2);

		// when
		em.persist(employee1);
		em.persist(employee2);
		em.flush();
		em.clear();

		List<Employee> retrievedEmployees = em.createQuery(SQL_EMPLOYEE, Employee.class).getResultList();
		List<Contact> retrievedContacts = em.createQuery(SQL_CONTACT, Contact.class).getResultList();

		// then
		Assert.assertEquals(employees.size(), retrievedEmployees.size());
		Assert.assertTrue(employees.contains(retrievedEmployees.get(0)));
		Assert.assertEquals(2, retrievedContacts.size());
		Assert.assertTrue(retrievedContacts.contains(employees.get(0).getContact()));
	}

	@Test
	@InSequence(4)
	public void shouldPersistEmployeeWithPersonalInfo() {
		System.out.println("Persist new employee with personal info...");

		// given
		List<Employee> employees = new ArrayList<>();

		Employee employee1 = new Employee();
		employee1.setLastName("Wiśniewska");
		Employee employee2 = new Employee();
		employee2.setLastName("Zaremba");
		PersonalInfo personalInfo1 = new PersonalInfo();
		personalInfo1.setPesel("85120526541");
		personalInfo1.setGender(Gender.F);
		personalInfo1.setBirthDate(LocalDate.parse("1985-12-05"));
		personalInfo1.setPhoto("wisniewska_anna.jpg");
		personalInfo1.setEmployee(employee1);
		PersonalInfo personalInfo2 = new PersonalInfo();
		personalInfo2.setPesel("90060716253");
		personalInfo2.setGender(Gender.M);
		personalInfo2.setBirthDate(LocalDate.parse("1990-06-07"));
		personalInfo2.setPhoto("zaremba_mateusz.jpg");
		personalInfo2.setEmployee(employee2);

		employee1.setPersonalInfo(personalInfo1);
		employees.add(employee1);
		employee2.setPersonalInfo(personalInfo2);
		employees.add(employee2);

		// when
		em.persist(employee1);
		em.persist(employee2);
		em.flush();
		em.clear();

		List<Employee> retrievedEmployees = em.createQuery(SQL_EMPLOYEE, Employee.class).getResultList();
		List<PersonalInfo> retrievedPersonalInfos = em.createQuery(SQL_PERSONAL, PersonalInfo.class).getResultList();
		
		// then
		Assert.assertEquals(employees.size(), retrievedEmployees.size());
		Assert.assertTrue(employees.contains(retrievedEmployees.get(0)));
		Assert.assertEquals(2, retrievedPersonalInfos.size());
		Assert.assertTrue(retrievedPersonalInfos.contains(employees.get(0).getPersonalInfo()));
	}
}
