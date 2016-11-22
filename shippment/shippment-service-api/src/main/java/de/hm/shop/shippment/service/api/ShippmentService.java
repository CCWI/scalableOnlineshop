package de.hm.shop.shippment.service.api;

import java.util.List;

import de.hm.shop.shippment.service.api.bo.ShippmentBo;
import de.hm.shop.shippment.service.api.exception.ShippmentException;


/**
 * Shippment-Service.
 * @author Maximilian.Auch
 */
public interface ShippmentService {

	/**
	 * Liefert alle {@link ShippmentBo}s zurück.
	 *
	 * @return alle {@link ShippmentBo}s
	 */
	List<ShippmentBo> getAll();



	/**
	 * Liefert das {@link ShippmentBo} zu der übergebenen <code>id</code> zurück.
	 *
	 * @param id
	 *            die Id eines {@link ShippmentBo}
	 *
	 * @return das {@link ShippmentBo} zu der übergebenen <code>id</code> oder {@code null}, wenn keines gefunden wird.
	 */
	ShippmentBo getById(long id);



	/**
	 * Speichert das übergebene {@link ShippmentBo}.
	 *
	 * @param exampleBo
	 *            das zu speichernde {@link ShippmentBo}
	 * @return das gespeicherte {@link ShippmentBo}
	 * @throws ShippmentException
	 */
	ShippmentBo save(ShippmentBo exampleBo) throws ShippmentException;



	/**
	 * Löscht das {@link ShippmentBo} zu der gegebenen <code>id</code>.
	 *
	 * @param id
	 *            die Id des zu löschenden {@link ShippmentBo}
	 */
	void delete(final Long id);

}
