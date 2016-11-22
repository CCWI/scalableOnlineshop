package de.fhm.akfo.shop.authentication.rest.to;

/**
 * ServiceTemplate Business-Objekt.
 * 
 * @author Maximilian.Auch
 */
public class AuthenticateTo {

	/** ServiceTemplate-Id */
	private String jwtToken;
	
	
	/**
	 * Default-Konstruktor.
	 */
	public AuthenticateTo() {
	}
	

	/**
	 * Konstruktor zur Befuellung aller Attribute.
	 * 
	 * @param name ServiceTemplate-Name String
	 */
	public AuthenticateTo(String jwtToken) {
		this.jwtToken = jwtToken;
	}


	public String getJwtToken() {
		return jwtToken;
	}


	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
}
