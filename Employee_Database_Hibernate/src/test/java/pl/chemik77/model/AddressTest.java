package pl.chemik77.model;

import java.util.ArrayList;
import java.util.List;

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

@RunWith(Arquillian.class)
public class AddressTest {

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Address.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction utx;

	private final String SQL_ADDRESS = "SELECT a FROM Address a ORDER BY a.id";

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
	public void shouldPersistAddress() {
		System.out.println("Persist new address...");

		// given
		List<Address> addresses = new ArrayList<>();

		Address address1 = new Address();
		address1.setStreet("Jesionowa");
		address1.setHouseNo("2");
		address1.setZipCode("53-521");
		address1.setCity("Wrocław");
		address1.setCountry("Polska");
		addresses.add(address1);
		Address address2 = new Address();
		address2.setStreet("Główna");
		address2.setHouseNo("5/20");
		address2.setZipCode("00-562");
		address2.setCity("Warszawa");
		address2.setCountry("Polska");
		addresses.add(address2);

		// when
		em.persist(address1);
		em.persist(address2);
		em.flush();
		em.clear();

		List<Address> retrievedAddresses = em.createQuery(SQL_ADDRESS, Address.class).getResultList();

		// then
		Assert.assertEquals(addresses.size(), retrievedAddresses.size());
		Assert.assertTrue(retrievedAddresses.contains(addresses.get(0)));
	}
}
