package de.hm.shop.template.service.api.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;



/**
 * Template-Businessobjekt.
 * @author Maximilian.Auch
 */
public class TemplateBo extends AbstractBo {

	private String name;



	/**
	 * Default-Konstruktor.
	 */
	public TemplateBo() {
	}



	/**
	 * Konstruktor zur Befuellung aller Attribute.
	 *
	 * @param name
	 *            Name
	 */
	public TemplateBo(final Long id, final String name) {
		super(id);
		this.name = name;
	}



	public String getName() {
		return name;
	}



	public void setName(final String name) {
		this.name = name;
	}



	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}



	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
