package de.hm.shop.template.service.api.exception;

/**
 * Template-Exception.
 */
public class TemplateException extends Exception {

	private static final long serialVersionUID = 1L;



	/**
	 * Erzeugt eine {@link TemplateException} mit der uebergebenen {@code message}.
	 *
	 * @param message
	 *            String, Fehlertext der Exception.
	 */
	public TemplateException(final String message) {
		super(message);
	}



	/**
	 * Erzeugt eine {@link TemplateException} mit der uebergebenen {@code cause}.
	 *
	 * @param cause
	 *            {@link Throwable}, das diese Exception verursacht hat.
	 */
	public TemplateException(final Throwable cause) {
		super(cause);
	}



	/**
	 * Erzeugt eine {@link TemplateException} mit der uebergebenen {@code message} und {@code cause}.
	 *
	 * @param message
	 *            String, Fehlertext der Exception.
	 * @param cause
	 *            {@link Throwable}, das diese Exception verursacht hat.
	 */
	public TemplateException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
