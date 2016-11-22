package de.hm.shop.payment.service.api.exception;

/**
 * Payment-Exception.
 */
public class PaymentException extends Exception {

	private static final long serialVersionUID = 1L;



	/**
	 * Erzeugt eine {@link PaymentException} mit der uebergebenen {@code message}.
	 *
	 * @param message
	 *            String, Fehlertext der Exception.
	 */
	public PaymentException(final String message) {
		super(message);
	}



	/**
	 * Erzeugt eine {@link PaymentException} mit der uebergebenen {@code cause}.
	 *
	 * @param cause
	 *            {@link Throwable}, das diese Exception verursacht hat.
	 */
	public PaymentException(final Throwable cause) {
		super(cause);
	}



	/**
	 * Erzeugt eine {@link PaymentException} mit der uebergebenen {@code message} und {@code cause}.
	 *
	 * @param message
	 *            String, Fehlertext der Exception.
	 * @param cause
	 *            {@link Throwable}, das diese Exception verursacht hat.
	 */
	public PaymentException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
