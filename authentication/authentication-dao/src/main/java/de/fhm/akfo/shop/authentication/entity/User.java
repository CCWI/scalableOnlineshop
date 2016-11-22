package de.fhm.akfo.shop.authentication.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entität des Authentication-Objekts
 * 
 * @author Maximilian.Auch
 */
@Entity
@Table(name = "USER")
public class User extends AbstractEntity {

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
	@ManyToMany(targetEntity = Role.class, cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "id_user", referencedColumnName = "id") }, 
     inverseJoinColumns = { @JoinColumn(name = "id_role", referencedColumnName = "id") })
	private List<Role> roles;
	
	/**
	 * Failed Logins since last successful login 
	 */
	private Integer failedlogins;


	public List<Role> getRoles() {
        return this.roles;
    }
	
	
	public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
	
	/**
	 * Default Constructor
	 */
	public User(){
	}
	
	/**
	 * Constructor
	 * @param username
	 * @param firstname
	 * @param lastname
	 * @param password
	 * @param address
	 * @param city
	 * @param postcode
	 * @param country
	 * @param roleSet
	 * @param failedlogins
	 */
	public User(String username, String firstname, String lastname, String password, String address,
			String city, String postcode, String country, ArrayList<Role> roleSet, Integer failedlogins) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roleSet;
		this.failedlogins = failedlogins;
	}
	
	

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", roles=" + roles + ", failedlogins="
				+ failedlogins + "]";
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

	
	public Integer getFailedlogins() {
		return failedlogins;
	}

	
	public void setFailedlogins(Integer failedlogins) {
		this.failedlogins = failedlogins;
	}
	
}