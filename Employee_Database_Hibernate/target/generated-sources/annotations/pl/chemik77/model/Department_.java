package pl.chemik77.model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Department.class)
public abstract class Department_ {

	public static volatile SingularAttribute<Department, Employee> manager;
	public static volatile SingularAttribute<Department, LocalDateTime> lastUpdate;
	public static volatile SingularAttribute<Department, Contact> contact;
	public static volatile SingularAttribute<Department, String> name;
	public static volatile SingularAttribute<Department, Integer> id;
	public static volatile ListAttribute<Department, Employee> employees;
	public static volatile SingularAttribute<Department, LocalDateTime> createDate;

}

