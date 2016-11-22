package de.fhm.akfo.shop.authentication.rest.to;

public class RoleTo {

	/**
	 * Roles of User
	 */
	private String role;
	
	/**
	 * Default Constructor
	 */
	public RoleTo() {
	}
	
	public RoleTo(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}
	
}
