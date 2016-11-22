package de.hm.shop.user.rest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * UserDTO.
 * @author Maximilian.Auch
 */
@XmlRootElement
public class UserDto extends AbstractDto {

	@XmlElement
	private String firstname;
	
	@XmlElement
	private String lastname;

	@XmlElement
	private String address;

	@XmlElement
	private String postcode;

	@XmlElement
	private String city;
	
	@XmlElement
	private String country;

	@XmlElement
	private Long supplierId;



	/**
	 * Default-Konstruktor.
	 */
	public UserDto() {
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
	public UserDto(final Long id, final String firstname,
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
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
