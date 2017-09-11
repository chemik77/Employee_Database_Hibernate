package pl.chemik77.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
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
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(Employee.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction utx;

	private static final String[] EMPLOYEE_LASTNAMES = { "Kowal", "Malachowska", "Bielak" };

	@Before
	public void preparePersistenceTest() throws Exception {
		clearData();
		insertData();
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

	private void insertData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Inserting records...");
		for (String lastName : EMPLOYEE_LASTNAMES) {
			Employee employee = new Employee();
			employee.setLastName(lastName);
			em.persist(employee);
		}
		utx.commit();
		em.clear();
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
	public void shouldFindAllEmployees() throws Exception {
		// given
		String fetchingAllEmployees = "SELECT e FROM Employee e ORDER BY e.id";

		// when
		System.out.println("Selecting...");
		List<Employee> employees = em.createQuery(fetchingAllEmployees, Employee.class).getResultList();

		// then
		System.out.println("Found " + employees.size() + " employees: ");
		assertContainsAllEmployees(employees);
	}

	private static void assertContainsAllEmployees(Collection<Employee> retrievedEmployees) {
		Assert.assertEquals(EMPLOYEE_LASTNAMES.length, retrievedEmployees.size());
		final Set<String> retrievedEmployeeLastNames = new HashSet<String>();
		for (Employee employee : retrievedEmployees) {
			System.out.println("*  " + employee.getLastName());
			retrievedEmployeeLastNames.add(employee.getLastName());
		}
		Assert.assertTrue(retrievedEmployeeLastNames.containsAll(Arrays.asList(EMPLOYEE_LASTNAMES)));
	}
}
