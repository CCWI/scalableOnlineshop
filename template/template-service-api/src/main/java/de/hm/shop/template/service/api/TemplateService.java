package de.hm.shop.template.service.api;

import java.util.List;

import de.hm.shop.template.service.api.bo.TemplateBo;
import de.hm.shop.template.service.api.exception.TemplateException;


/**
 * Template-Service.
 * @author Maximilian.Auch
 */
public interface TemplateService {

	/**
	 * Liefert alle {@link TemplateBo}s zurück.
	 *
	 * @return alle {@link TemplateBo}s
	 */
	List<TemplateBo> getAll();



	/**
	 * Liefert das {@link TemplateBo} zu der übergebenen <code>id</code> zurück.
	 *
	 * @param id
	 *            die Id eines {@link TemplateBo}
	 *
	 * @return das {@link TemplateBo} zu der übergebenen <code>id</code> oder {@code null}, wenn keines gefunden wird.
	 */
	TemplateBo getById(long id);



	/**
	 * Speichert das übergebene {@link TemplateBo}.
	 *
	 * @param exampleBo
	 *            das zu speichernde {@link TemplateBo}
	 * @return das gespeicherte {@link TemplateBo}
	 * @throws TemplateException
	 */
	TemplateBo save(TemplateBo exampleBo) throws TemplateException;



	/**
	 * Löscht das {@link TemplateBo} zu der gegebenen <code>id</code>.
	 *
	 * @param id
	 *            die Id des zu löschenden {@link TemplateBo}
	 */
	void delete(final Long id);

}
