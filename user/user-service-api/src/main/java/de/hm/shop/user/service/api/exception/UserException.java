package de.hm.shop.user.service.api.exception;

/**
 * User-Exception.
 */
public class UserException extends Exception {

	private static final long serialVersionUID = 1L;



	/**
	 * Erzeugt eine {@link UserException} mit der uebergebenen {@code message}.
	 *
	 * @param message
	 *            String, Fehlertext der Exception.
	 */
	public UserException(final String message) {
		super(message);
	}



	/**
	 * Erzeugt eine {@link UserException} mit der uebergebenen {@code cause}.
	 *
	 * @param cause
	 *            {@link Throwable}, das diese Exception verursacht hat.
	 */
	public UserException(final Throwable cause) {
		super(cause);
	}



	/**
	 * Erzeugt eine {@link UserException} mit der uebergebenen {@code message} und {@code cause}.
	 *
	 * @param message
	 *            String, Fehlertext der Exception.
	 * @param cause
	 *            {@link Throwable}, das diese Exception verursacht hat.
	 */
	public UserException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
