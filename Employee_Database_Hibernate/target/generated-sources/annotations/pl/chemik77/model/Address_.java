package pl.chemik77.model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ {

	public static volatile SingularAttribute<Address, String> zipCode;
	public static volatile SingularAttribute<Address, String> country;
	public static volatile SingularAttribute<Address, String> city;
	public static volatile SingularAttribute<Address, String> street;
	public static volatile SingularAttribute<Address, LocalDateTime> lastUpdate;
	public static volatile SingularAttribute<Address, String> houseNo;
	public static volatile SingularAttribute<Address, Integer> id;
	public static volatile SingularAttribute<Address, LocalDateTime> createDate;

}

