package de.fhm.akfo.shop.authentication.service.api.exception;

/**
 * Diese Exception wird geworfen, wenn bei der Validierung von Authentication ein fachlicher Fehler
 * auftritt.
 * 
 * @author Maximilian.Auch
 */
public class AuthenticationValidationException extends Exception {

	/** Generierte unique ID zur Serialisierung der Exception. */
	private static final long serialVersionUID = 4845965355617181747L;



	/**
	 * Erzeugt eine {@link AuthenticationValidationException} mit der uebergebenen {@code message}.
	 * 
	 * @param message
	 *            String, Fehlertext der Exception.
	 */
	public AuthenticationValidationException(String message) {
		super(message);
	}



	/**
	 * Erzeugt eine {@link AuthenticationValidationException} mit der uebergebenen {@code cause}.
	 * 
	 * @param cause
	 *            {@link Throwable}, das diese Exception verursacht hat.
	 */
	public AuthenticationValidationException(Throwable cause) {
		super(cause);
	}



	/**
	 * Erzeugt eine {@link AuthenticationValidationException} mit der uebergebenen {@code message} und
	 * {@code cause}.
	 * 
	 * @param message
	 *            String, Fehlertext der Exception.
	 * @param cause
	 *            {@link Throwable}, das diese Exception verursacht hat.
	 */
	public AuthenticationValidationException(String message, Throwable cause) {
		super(message, cause);
	}

}
