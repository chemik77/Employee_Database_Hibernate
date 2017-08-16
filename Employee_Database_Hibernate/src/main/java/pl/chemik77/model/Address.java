package pl.chemik77.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String street;
	@Column(name = "house_no")
	private String houseNo;
	@Column(name = "zip_code")
	private String zipCode;
	private String city;
	private String country;
	@Column(name = "create_date", columnDefinition = "datetime(0) DEFAULT NULL")
	private LocalDateTime createDate;
	@Column(name = "last_update", columnDefinition = "datetime(0) DEFAULT NULL")
	private LocalDateTime lastUpdate;

	public Address() {
		this.createDate = LocalDateTime.now().withNano(0);
		this.lastUpdate = LocalDateTime.now().withNano(0);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", houseNo=" + houseNo + ", city=" + city + "]";
	}
}
