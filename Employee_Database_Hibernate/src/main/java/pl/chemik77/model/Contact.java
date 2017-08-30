package pl.chemik77.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String email;
	@Column(name = "create_date", columnDefinition = "datetime(0) DEFAULT NULL")
	private LocalDateTime createDate;
	@Column(name = "last_update", columnDefinition = "datetime(0) DEFAULT NULL")
	private LocalDateTime lastUpdate;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Phone> phones;

	public Contact() {
		this.createDate = LocalDateTime.now().withNano(0);
		this.lastUpdate = LocalDateTime.now().withNano(0);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public List<Phone> getPhones() {
		return phones;
	}

	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", email=" + email + "]";
	}
}
