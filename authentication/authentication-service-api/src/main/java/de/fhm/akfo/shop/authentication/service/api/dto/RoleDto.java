package de.fhm.akfo.shop.authentication.service.api.dto;

public class RoleDto implements Cloneable {

	/**
	 * Roles of User
	 */
	private String role;
	
	
	/**
	 * Default Constructor
	 */
	public RoleDto() {
	}

	
	/**
	 * Constructor
	 * @param role
	 */
	public RoleDto(String role) {
		this.role = role;
	}
	

	@Override
	public String toString() {
		return "RoleDto [role=" + role + "]";
	}

	
	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}
	
}
