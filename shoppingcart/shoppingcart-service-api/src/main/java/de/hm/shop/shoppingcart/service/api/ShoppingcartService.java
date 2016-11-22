package de.hm.shop.shoppingcart.service.api;

import java.util.List;

import de.hm.shop.shoppingcart.service.api.bo.ShoppingcartBo;
import de.hm.shop.shoppingcart.service.api.exception.ShoppingcartException;


/**
 * Shoppingcart-Service.
 * @author Maximilian.Auch
 */
public interface ShoppingcartService {

	/**
	 * Liefert alle {@link ShoppingcartBo}s zurück.
	 * @param userId 
	 *
	 * @return alle {@link ShoppingcartBo}s
	 */
	List<ShoppingcartBo> getAllForUser(Long userId);



	/**
	 * Liefert das {@link ShoppingcartBo} zu der übergebenen <code>id</code> zurück.
	 *
	 * @param id
	 *            die Id eines {@link ShoppingcartBo}
	 *
	 * @return das {@link ShoppingcartBo} zu der übergebenen <code>id</code> oder {@code null}, wenn keines gefunden wird.
	 */
	ShoppingcartBo getByUserId(long id);



	/**
	 * Speichert das übergebene {@link ShoppingcartBo}.
	 *
	 * @param exampleBo
	 *            das zu speichernde {@link ShoppingcartBo}
	 * @return das gespeicherte {@link ShoppingcartBo}
	 * @throws ShoppingcartException
	 */
	ShoppingcartBo save(ShoppingcartBo exampleBo) throws ShoppingcartException;


	/**
	 * Update auf einen bestehenden {@link ShoppingcartBo}-Eintrag.
	 * @param shoppingcartBo
	 * @return
	 * @throws ShoppingcartException
	 */
	ShoppingcartBo update(final ShoppingcartBo shoppingcartBo) throws ShoppingcartException;

	

	/**
	 * Löscht das {@link ShoppingcartBo} zu der gegebenen <code>id</code>.
	 *
	 * @param id
	 *            die Id des zu löschenden {@link ShoppingcartBo}
	 */
	void delete(final Long id, final Long userId);
	
}
