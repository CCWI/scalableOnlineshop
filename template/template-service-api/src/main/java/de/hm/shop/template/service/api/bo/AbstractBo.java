package de.hm.shop.template.service.api.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * Abstraktes Businessobjekt
 * @author Maximilian.Auch
 */
public abstract class AbstractBo {

	private Long id;



	public AbstractBo() {
	}



	public AbstractBo(final Long id) {
		this.id = id;
	}



	public Long getId() {
		return id;
	}



	public void setId(final Long id) {
		this.id = id;
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
