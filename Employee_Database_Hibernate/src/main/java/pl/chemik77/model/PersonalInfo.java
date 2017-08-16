package pl.chemik77.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class PersonalInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(length = 11)
	private String pesel;
	private Gender gender;
	@Column(name = "birth_date")
	private LocalDate birthDate;
	private String photo;
	@Column(name = "create_date", columnDefinition = "datetime(0) DEFAULT NULL")
	private LocalDateTime createDate;
	@Column(name = "last_update", columnDefinition = "datetime(0) DEFAULT NULL")
	private LocalDateTime lastUpdate;

	@OneToOne(mappedBy = "personalInfo")
	private Employee employee;

	public PersonalInfo() {
		this.createDate = LocalDateTime.now().withNano(0);
		this.lastUpdate = LocalDateTime.now().withNano(0);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "PersonalInfo [id=" + id + ", pesel=" + pesel + ", gender=" + gender + "]";
	}
}
