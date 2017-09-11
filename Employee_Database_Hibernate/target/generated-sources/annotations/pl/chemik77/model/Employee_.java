package pl.chemik77.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Employee.class)
public abstract class Employee_ {

	public static volatile SingularAttribute<Employee, String> lastName;
	public static volatile SingularAttribute<Employee, PersonalInfo> personalInfo;
	public static volatile SingularAttribute<Employee, LocalDate> hireDate;
	public static volatile SingularAttribute<Employee, Address> address;
	public static volatile SingularAttribute<Employee, String> office;
	public static volatile SingularAttribute<Employee, Integer> salary;
	public static volatile SingularAttribute<Employee, String> firstName;
	public static volatile SingularAttribute<Employee, LocalDateTime> lastUpdate;
	public static volatile SingularAttribute<Employee, Contact> contact;
	public static volatile SingularAttribute<Employee, Integer> id;
	public static volatile SingularAttribute<Employee, Department> department;
	public static volatile SingularAttribute<Employee, LocalDateTime> createDate;
	public static volatile SingularAttribute<Employee, Department> department_manager;

}

