package de.hm.shop.shippment.dao.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * Basisklasse für alle Entities.
 * @author Maximilian.Auch
 */
@MappedSuperclass
public abstract class AbstractEntity {

	protected static final String ID_GENERATOR_NAME = "sequenceGenerator";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ID_GENERATOR_NAME)
	private Long id;



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
