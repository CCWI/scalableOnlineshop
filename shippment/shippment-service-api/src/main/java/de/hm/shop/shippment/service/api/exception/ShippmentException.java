package de.hm.shop.shippment.service.api.exception;

/**
 * Shippment-Exception.
 */
public class ShippmentException extends Exception {

	private static final long serialVersionUID = 1L;



	/**
	 * Erzeugt eine {@link ShippmentException} mit der uebergebenen {@code message}.
	 *
	 * @param message
	 *            String, Fehlertext der Exception.
	 */
	public ShippmentException(final String message) {
		super(message);
	}



	/**
	 * Erzeugt eine {@link ShippmentException} mit der uebergebenen {@code cause}.
	 *
	 * @param cause
	 *            {@link Throwable}, das diese Exception verursacht hat.
	 */
	public ShippmentException(final Throwable cause) {
		super(cause);
	}



	/**
	 * Erzeugt eine {@link ShippmentException} mit der uebergebenen {@code message} und {@code cause}.
	 *
	 * @param message
	 *            String, Fehlertext der Exception.
	 * @param cause
	 *            {@link Throwable}, das diese Exception verursacht hat.
	 */
	public ShippmentException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
