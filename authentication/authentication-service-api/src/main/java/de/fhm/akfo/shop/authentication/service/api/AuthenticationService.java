package de.fhm.akfo.shop.authentication.service.api;

import de.fhm.akfo.shop.authentication.service.api.dto.UserDto;
import de.fhm.akfo.shop.authentication.service.api.exception.AuthenticationValidationException;

/**
 * Business Service zur Erzeugung eines neuen bzw. zum Ueberschreiben eines bestehenden Eintrags für Authentication.
 * 
 * @author Maximilian.Auch
 */
public interface AuthenticationService {

	/**
	 * Liefert einen gueltigen Authentifizierungs-Token für die mitgelieferten Credentials.
	 * @param username
	 * @param password
	 * @return {@link TokenDto} mit dem aktuell gueltigen Eintrag für Authentication, oder einem
	 *         leeren Dto - niemals {@code null}.
	 */
	public String getAuthenticationToken(String username, String password);
	
	
	/**
	 * Zur Registrierung eines neuen Nutzers, werden die Nutzerdaten gespeichert.
	 * @param dto
	 * @return userId
	 * @throws AuthenticationValidationException 
	 */
	public Long saveUserData(UserDto dto) throws AuthenticationValidationException;


	/**
	 * Liefert alle Nutzerinformationen
	 * @param username
	 * @param firstname
	 * @param lastname
	 * @return
	 * @throws AuthenticationValidationException 
	 */
	public UserDto getUserData(String username) throws AuthenticationValidationException;
	
}
