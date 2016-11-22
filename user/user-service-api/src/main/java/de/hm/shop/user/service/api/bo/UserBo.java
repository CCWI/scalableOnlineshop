package de.hm.shop.user.service.api.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;



/**
 * User-Businessobjekt.
 * @author Maximilian.Auch
 */
public class UserBo extends AbstractBo {

	private String firstname;
	
	private String lastname;

	private String address;

	private String postcode;

	private String city;
	
	private String country;

	private Long supplierId;



	/**
	 * Default-Konstruktor.
	 */
	public UserBo() {
	}



	/**
	 * Konstruktor zur Befuellung aller Attribute.
	 * @param id
	 * @param firstname
	 * @param lastname
	 * @param address
	 * @param postcode
	 * @param city
	 * @param country
	 * @param supplierId
	 */
	public UserBo(final Long id, final String firstname,
			final String lastname, final String address,
			final String postcode, final String city,
			final String country, final Long supplierId) {
		super(id);
		this.lastname = lastname;
		this.firstname = firstname;
		this.address = address;
		this.postcode = postcode;
		this.city = city;
		this.country = country;
		this.supplierId = supplierId;
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



	public Long getSupplierId() {
		return supplierId;
	}



	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
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
