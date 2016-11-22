package de.hm.shop.payment.service.api;

import java.util.Collection;
import java.util.List;

import de.hm.shop.payment.service.api.bo.PaymentBo;
import de.hm.shop.payment.service.api.exception.PaymentException;


/**
 * Payment-Service.
 * @author Maximilian.Auch
 */
public interface PaymentService {

	/**
	 * Liefert alle {@link PaymentBo}s zurück.
	 *
	 * @return alle {@link PaymentBo}s
	 */
	List<PaymentBo> getAll();



	/**
	 * Liefert das {@link PaymentBo} zu der übergebenen <code>supplierId</code> zurück.
	 *
	 * @param id
	 *            die Id eines {@link PaymentBo}
	 *
	 * @return das {@link PaymentBo} zu der übergebenen <code>id</code> oder {@code null}, wenn keines gefunden wird.
	 */
	Collection<PaymentBo> getBySupplierId(long id);



	/**
	 * Speichert das übergebene {@link PaymentBo}.
	 *
	 * @param exampleBo
	 *            das zu speichernde {@link PaymentBo}
	 * @return das gespeicherte {@link PaymentBo}
	 * @throws PaymentException
	 */
	PaymentBo save(PaymentBo exampleBo) throws PaymentException;



	/**
	 * Löscht das {@link PaymentBo} zu der gegebenen <code>id</code>.
	 *
	 * @param id
	 *            die Id des zu löschenden {@link PaymentBo}
	 */
	void delete(final Long id);

}
