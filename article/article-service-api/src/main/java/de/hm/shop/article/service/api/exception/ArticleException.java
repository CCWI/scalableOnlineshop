package de.hm.shop.article.service.api.exception;

/**
 * Article-Exception.
 */
public class ArticleException extends Exception {

	private static final long serialVersionUID = 1L;



	/**
	 * Erzeugt eine {@link ArticleException} mit der uebergebenen {@code message}.
	 *
	 * @param message
	 *            String, Fehlertext der Exception.
	 */
	public ArticleException(final String message) {
		super(message);
	}



	/**
	 * Erzeugt eine {@link ArticleException} mit der uebergebenen {@code cause}.
	 *
	 * @param cause
	 *            {@link Throwable}, das diese Exception verursacht hat.
	 */
	public ArticleException(final Throwable cause) {
		super(cause);
	}



	/**
	 * Erzeugt eine {@link ArticleException} mit der uebergebenen {@code message} und {@code cause}.
	 *
	 * @param message
	 *            String, Fehlertext der Exception.
	 * @param cause
	 *            {@link Throwable}, das diese Exception verursacht hat.
	 */
	public ArticleException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
