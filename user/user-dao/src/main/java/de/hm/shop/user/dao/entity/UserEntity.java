package de.hm.shop.user.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * User-Entität.
 * @author Maximilian.Auch
 */
@Entity
@Table(name = "User")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "SEQ_User", initialValue = 1,
allocationSize = 1)
public class UserEntity {
	
	@Column(unique=true)
	private Long id;

	@Column
	private String firstname;
	
	@Column
	private String lastname;

	@Column
	private String address;

	@Column
	private String postcode;

	@Column
	private String city;
	
	@Column
	private String country;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	private Long supplierId;
	

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", address=" + address
				+ ", postcode=" + postcode + ", city=" + city + ", country=" + country + ", supplierId=" + supplierId
				+ "]";
	}



	public Long getId() {
		return id;
	}



	public void setId(final Long id) {
		this.id = id;
	}

	

	public String getFirstname() {
		return firstname;
	}



	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}



	public String getLastname() {
		return lastname;
	}



	public void setLastname(String lastname) {
		this.lastname = lastname;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getPostcode() {
		return postcode;
	}



	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public Long getSupplierId() {
		return supplierId;
	}



	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}


	
	
	public String getCity() {
		return city;
	}

	


	public void setCity(String city) {
		this.city = city;
	}


	

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	


	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}