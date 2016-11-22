package de.fhm.akfo.shop.authentication.service.api.dto;

import java.util.List;

public class UserDto implements Cloneable {

	/**
	 *  Technical User-Id 
	 */
	private Long id;
	
	/**
	 * Email of User
	 */
	private String username;
	
	/**
	 * Password of User 
	 */
	private String password;
	
	/**
	 * Roles of User
	 */
	private List<RoleDto> roles;

	/**
	 * Failed Logins since last successful login 
	 */
	private Integer failedlogins;

	/**
	 * Default Constructor
	 */
	public UserDto(){
	}
	
	/**
	 * Constructor
	 * @param id
	 * @param username
	 * @param firstname
	 * @param lastname
	 * @param password
	 * @param roles
	 * @param address
	 * @param city
	 * @param postcode
	 * @param country
	 * @param failedlogins
	 */
	public UserDto(Long id, String username, String firstname, String lastname, String password, List<RoleDto> roles, String address,
			String city, String postcode, String country, Integer failedlogins) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.failedlogins = failedlogins;
	}
	
	
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", username=" + username + ", password=" + password + ", roles=" + roles
				+ ", failedlogins=" + failedlogins + "]";
	}

	public UserDto(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public List<RoleDto> getRoles() {
		return roles;
	}


	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}


	public Integer getFailedlogins() {
		return failedlogins;
	}


	public void setFailedlogins(Integer failedlogins) {
		this.failedlogins = failedlogins;
	}

	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
}
