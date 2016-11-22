package de.fhm.akfo.shop.authentication.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ROLE")
public class Role extends AbstractEntity {

	/**
	 * Roles of User
	 */
	private String role;
	
	/**
	 * 
	 */
	@ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
	public List<User> user;
	
	/**
	 * Default-Constructor
	 */
	public Role() {
	}
	
	/**
	 * Constructor
	 * @param role
	 */
	public Role(String role) {
		super();
		this.role = role;
	}

	
	@Override
	public String toString() {
		return "Role [role=" + role + ", user=" + user + "]";
	}

	
	public List<User> getUser() {
		return this.user;
	}

	
	public void setUser(List<User> user) {
		this.user = user;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}
	
}
