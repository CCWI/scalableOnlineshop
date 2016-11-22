package de.hm.shop.article.service.api;

import java.util.Collection;
import java.util.List;

import de.hm.shop.article.service.api.bo.ArticleBo;
import de.hm.shop.article.service.api.exception.ArticleException;


/**
 * Article-Service.
 * @author Maximilian.Auch
 */
public interface ArticleService {

	/**
	 * Liefert alle {@link ArticleBo}s zurück.
	 *
	 * @return alle {@link ArticleBo}s
	 */
	List<ArticleBo> getAll();



	/**
	 * Liefert das {@link ArticleBo} zu der übergebenen <code>id</code> zurück.
	 *
	 * @param id
	 *            die Id eines {@link ArticleBo}
	 *
	 * @return das {@link ArticleBo} zu der übergebenen <code>id</code> oder {@code null}, wenn keines gefunden wird.
	 */
	ArticleBo getById(long id);

	

	/**
	 * Liefert das {@link ArticleBo} zu dem übergebenen <code>searchString</code> und <code>distance</code> zurück.
	 * @param searchString
	 * @param distance
	 * @param userToken 
	 * @return
	 */
	Collection<ArticleBo> getByTitleDistanceSearch(Long userId, String searchString, Double distance, String userToken);



	/**
	 * Speichert das übergebene {@link ArticleBo}.
	 *
	 * @param exampleBo
	 *            das zu speichernde {@link ArticleBo}
	 * @return das gespeicherte {@link ArticleBo}
	 * @throws ArticleException
	 */
	ArticleBo save(ArticleBo exampleBo) throws ArticleException;



	/**
	 * Löscht das {@link ArticleBo} zu der gegebenen <code>id</code>.
	 *
	 * @param id
	 *            die Id des zu löschenden {@link ArticleBo}
	 */
	void delete(final Long id);


}
