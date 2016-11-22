package de.hm.shop.template.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * Template-Entität.
 * @author Maximilian.Auch
 */
@Entity
@Table(name = "Template")
@SequenceGenerator(name = AbstractEntity.ID_GENERATOR_NAME, sequenceName = "SEQ_Template", initialValue = 1,
		allocationSize = 1)
public class TemplateEntity extends AbstractEntity {

	@Column
	private String name;


	
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