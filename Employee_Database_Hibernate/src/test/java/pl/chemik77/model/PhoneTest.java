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
public class PhoneTest {

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Phone.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction utx;

	private final String SQL_PHONE = "SELECT p FROM Phone p ORDER BY p.id";

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
	public void shouldPersistPhone() {
		System.out.println("Persist new phone...");

		// given
		List<Phone> phones = new ArrayList<>();

		Phone phone1 = new Phone();
		phone1.setType("Work");
		phone1.setNumber("784 256 254");
		phones.add(phone1);
		Phone phone2 = new Phone();
		phone1.setType("Private");
		phone1.setNumber("589 256 254");
		phones.add(phone2);

		// when
		em.persist(phone1);
		em.persist(phone2);
		em.flush();
		em.clear();

		List<Phone> retrievedPhones = em.createQuery(SQL_PHONE, Phone.class).getResultList();

		// then
		Assert.assertEquals(phones.size(), retrievedPhones.size());
		Assert.assertTrue(retrievedPhones.contains(phones.get(0)));
	}
}
