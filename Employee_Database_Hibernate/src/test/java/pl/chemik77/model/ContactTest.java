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
public class ContactTest {

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Contact.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction utx;

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
	public void shouldPersistContact() throws Exception {
		System.out.println("Persist new contact...");

		// given
		List<Contact> contacts = new ArrayList<>();
		Contact contact1 = new Contact();
		contact1.setEmail("quality@company.com");
		contact1.setPhone("854 412 225");
		contacts.add(contact1);

		Contact contact2 = new Contact();
		contact2.setEmail("mzaremba@company.com");
		contact2.setPhone("652 211 442");
		contacts.add(contact2);

		// when
		em.persist(contact1);
		em.persist(contact2);

		em.flush();
		em.clear();

		List<Contact> retrievedContacts = em.createQuery(SQL_CONTACT, Contact.class).getResultList();

		// then
		Assert.assertEquals(contacts.size(), retrievedContacts.size());
		Assert.assertTrue(retrievedContacts.contains(contacts.get(0)));

	}

}
