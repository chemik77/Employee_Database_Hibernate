package pl.chemik77.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PersonalInfo.class)
public abstract class PersonalInfo_ {

	public static volatile SingularAttribute<PersonalInfo, Gender> gender;
	public static volatile SingularAttribute<PersonalInfo, LocalDateTime> lastUpdate;
	public static volatile SingularAttribute<PersonalInfo, String> photo;
	public static volatile SingularAttribute<PersonalInfo, Integer> id;
	public static volatile SingularAttribute<PersonalInfo, String> pesel;
	public static volatile SingularAttribute<PersonalInfo, Employee> employee;
	public static volatile SingularAttribute<PersonalInfo, LocalDate> birthDate;
	public static volatile SingularAttribute<PersonalInfo, LocalDateTime> createDate;

}

