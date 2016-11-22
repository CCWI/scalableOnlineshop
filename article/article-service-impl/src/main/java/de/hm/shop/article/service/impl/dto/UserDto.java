package de.hm.shop.article.service.impl.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * UserDTO.
 * @author Maximilian.Auch
 */
@XmlRootElement
@JsonIgnoreProperties
public class UserDto {

	@XmlElement
	private Long id;
	
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

	@JsonIgnore
	@XmlElement
	private String links;


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
		this.id = id;
		this.lastname = lastname;
		this.firstname = firstname;
		this.address = address;
		this.postcode = postcode;
		this.city = city;
		this.country = country;
		this.supplierId = supplierId;
	}

	
	


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
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
