package pl.chemik77.model;

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

@RunWith(Arquillian.class)
public class DepartmentTest {

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

	private final String SQL_DEPART = "SELECT d FROM Department d ORDER BY d.id";
	private final String SQL_EMPLOYEE = "SELECT e FROM Employee e ORDER BY e.id";
	private final String SQL_CONTACT = "SELECT c FROM Contact c ORDER BY c.id";

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
	public void shouldPersistDepartment() throws Exception {
		System.out.println("Persist new departments...");

		// given
		List<Department> departments = new ArrayList<>();
		Department department1 = new Department();
		department1.setName("Quality");
		departments.add(department1);

		Department department2 = new Department();
		department2.setName("Production");
		departments.add(department2);

		// when
		em.persist(department1);
		em.persist(department2);

		em.flush();
		em.clear();

		List<Department> retrievedDepartments = em.createQuery(SQL_DEPART, Department.class).getResultList();

		// then
		Assert.assertEquals(departments.size(), retrievedDepartments.size());
	}

	@Test
	@InSequence(2)
	public void shouldPersistDepartmentWithManager() {
		System.out.println("Persist new department with manager...");

		// given
		List<Department> departments = new ArrayList<>();

		Department department1 = new Department();
		Department department2 = new Department();
		Employee manager1 = new Employee();
		manager1.setLastName("Zaremba");
		manager1.setDepartment(department1);
		Employee manager2 = new Employee();
		manager2.setLastName("Wiśniewska");
		manager2.setDepartment(department2);

		department1.setManager(manager1);
		departments.add(department1);
		department2.setManager(manager2);
		departments.add(department2);

		// when
		em.persist(department1);
		em.persist(department2);
		em.flush();
		em.clear();

		List<Department> retrievedDepartments = em.createQuery(SQL_DEPART, Department.class).getResultList();
		List<Employee> retrievedManagers = em.createQuery(SQL_EMPLOYEE, Employee.class).getResultList();

		// then
		Assert.assertEquals(departments.size(), retrievedDepartments.size());
		Assert.assertTrue(departments.contains(retrievedDepartments.get(0)));
		Assert.assertTrue(retrievedManagers.contains(departments.get(0).getManager()));
		Assert.assertNotNull(retrievedManagers.get(0));
		Assert.assertEquals(2, retrievedManagers.size());
	}

	@Test
	@InSequence(3)
	public void shouldPersistDepartmentWithContact() {
		System.out.println("Persist new department with contact...");

		// given
		List<Department> departments = new ArrayList<>();

		Department department1 = new Department();
		Department department2 = new Department();
		Contact contact1 = new Contact();
		contact1.setEmail("quality@company.com");
		contact1.setDepartment(department1);
		Contact contact2 = new Contact();
		contact2.setEmail("production@company.com");
		contact2.setDepartment(department2);

		department1.setContact(contact1);
		departments.add(department1);
		department2.setContact(contact2);
		departments.add(department2);

		// when
		em.persist(department1);
		em.persist(department2);
		em.flush();
		em.clear();

		List<Department> retrievedDepartments = em.createQuery(SQL_DEPART, Department.class).getResultList();
		List<Contact> retrievedContacts = em.createQuery(SQL_CONTACT, Contact.class).getResultList();

		// then
		Assert.assertEquals(departments.size(), retrievedDepartments.size());
		Assert.assertTrue(departments.contains(retrievedDepartments.get(0)));
		Assert.assertTrue(retrievedContacts.contains(departments.get(0).getContact()));
		Assert.assertNotNull(retrievedContacts.get(0));
		Assert.assertEquals(2, retrievedContacts.size());
	}

	@Test
	@InSequence(4)
	public void shouldPersistsDepartmentWithEmployees() throws Exception {
		System.out.println("Persist new department with employees...");

		// given
		List<Department> departments = new ArrayList<>();
		List<Employee> employees = new ArrayList<>();

		Department department = new Department();
		department.setName("Quality");
		department.setEmployees(employees);
		departments.add(department);

		Employee employee1 = new Employee();
		employee1.setLastName("Zaremba");
		employee1.setFirstName("Mateusz");
		employee1.setOffice("Manager");
		employee1.setDepartment(department);
		employees.add(employee1);

		Employee employee2 = new Employee();
		employee2.setLastName("Wiśniewska");
		employee2.setFirstName("Alicja");
		employee2.setOffice("Leader");
		employee2.setDepartment(department);
		employees.add(employee2);

		// when
		em.persist(department);
		em.flush();
		em.clear();

		List<Department> retrievedDepartments = em.createQuery(SQL_DEPART, Department.class).getResultList();

		// then
		Assert.assertEquals(departments.get(0).getEmployees().size(),
				retrievedDepartments.get(0).getEmployees().size());
		Assert.assertEquals(departments.get(0).getName(), retrievedDepartments.get(0).getName());
		Assert.assertTrue(
				departments.get(0).getEmployees().contains((retrievedDepartments.get(0).getEmployees().get(0))));

	}

}
