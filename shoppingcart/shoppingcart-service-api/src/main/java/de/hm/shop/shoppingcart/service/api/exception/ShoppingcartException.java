package de.hm.shop.shoppingcart.service.api.exception;

/**
 * Beispiel-Exception.
 */
public class ShoppingcartException extends Exception {

	private static final long serialVersionUID = 1L;



	/**
	 * Erzeugt eine {@link ShoppingcartException} mit der uebergebenen {@code message}.
	 *
	 * @param message
	 *            String, Fehlertext der Exception.
	 */
	public ShoppingcartException(final String message) {
		super(message);
	}



	/**
	 * Erzeugt eine {@link ShoppingcartException} mit der uebergebenen {@code cause}.
	 *
	 * @param cause
	 *            {@link Throwable}, das diese Exception verursacht hat.
	 */
	public ShoppingcartException(final Throwable cause) {
		super(cause);
	}



	/**
	 * Erzeugt eine {@link ShoppingcartException} mit der uebergebenen {@code message} und {@code cause}.
	 *
	 * @param message
	 *            String, Fehlertext der Exception.
	 * @param cause
	 *            {@link Throwable}, das diese Exception verursacht hat.
	 */
	public ShoppingcartException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
