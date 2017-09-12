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
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(Employee.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction utx;

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
	public void shouldPersistsEmployees() throws Exception {
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

		List<Employee> retrievedEmployees = em.createQuery("SELECT e FROM Employee e ORDER BY e.id", Employee.class)
				.getResultList();

		// then
		Assert.assertEquals(employees.size(), retrievedEmployees.size());
		Assert.assertTrue(employees.contains(retrievedEmployees.get(0)));
	}
}
