package de.fhm.akfo.shop.authentication.rest.to;

import java.util.List;

public class UserTo {

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
	private List<RoleTo> roles;
	
	/**
	 * Failed Logins since last successful login 
	 */
	private Integer failedlogins;
	
	/**
	 * Default Constructor
	 */
	public UserTo(){
	}
	
	/**
	 * Constructor
	 * @param id
	 * @param username
	 * @param password
	 * @param roles
	 */
	public UserTo(Long id, String username, String password, List<RoleTo> roles, Integer failedlogins) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.failedlogins = failedlogins;
	}
	
	/**
	 * Constructor
	 * @param username
	 * @param password
	 */
	public UserTo(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	

	@Override
	public String toString() {
		return "UserTo [id=" + id + ", username=" + username + ", password=" + password + ", roles=" + roles
				+ ", failedlogins=" + failedlogins + "]";
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


	public List<RoleTo> getRoles() {
		return roles;
	}


	public void setRoles(List<RoleTo> roles) {
		this.roles = roles;
	}


	public Integer getFailedlogins() {
		return failedlogins;
	}


	public void setFailedlogins(Integer failedlogins) {
		this.failedlogins = failedlogins;
	}
	
}
