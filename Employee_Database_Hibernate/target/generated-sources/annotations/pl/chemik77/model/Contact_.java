package pl.chemik77.model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Contact.class)
public abstract class Contact_ {

	public static volatile SingularAttribute<Contact, String> phone;
	public static volatile SingularAttribute<Contact, LocalDateTime> lastUpdate;
	public static volatile SingularAttribute<Contact, Integer> id;
	public static volatile SingularAttribute<Contact, Employee> employee;
	public static volatile SingularAttribute<Contact, String> email;
	public static volatile SingularAttribute<Contact, LocalDateTime> createDate;

}

