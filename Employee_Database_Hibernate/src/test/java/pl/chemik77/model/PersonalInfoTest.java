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
public class PersonalInfoTest {

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(PersonalInfo.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction utx;

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
	public void shouldPersistPersonalInfo() {
		System.out.println("Persist new personal info...");

		// given
		List<PersonalInfo> personalInfos = new ArrayList<>();

		PersonalInfo personalInfo1 = new PersonalInfo();
		personalInfo1.setPesel("85120152145");
		personalInfo1.setGender(Gender.F);
		personalInfo1.setBirthDate(LocalDate.parse("1985-12-01"));
		personalInfo1.setPhoto("wisniewska_alicja.jpg");
		personalInfos.add(personalInfo1);
		PersonalInfo personalInfo2 = new PersonalInfo();
		personalInfo2.setPesel("90061416205");
		personalInfo2.setGender(Gender.M);
		personalInfo2.setBirthDate(LocalDate.parse("1990-06-14"));
		personalInfo2.setPhoto("zaremba_mateusz.jpg");
		personalInfos.add(personalInfo2);

		// when
		em.persist(personalInfo1);
		em.persist(personalInfo2);
		em.flush();
		em.clear();

		List<PersonalInfo> retrievedPersonalInfos = em.createQuery(SQL_PERSONAL, PersonalInfo.class).getResultList();

		// then
		Assert.assertEquals(personalInfos.size(), retrievedPersonalInfos.size());
		Assert.assertTrue(retrievedPersonalInfos.contains(personalInfos.get(0)));
	}
}
