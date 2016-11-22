package de.hm.shop.template.rest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * TemplateDTO.
 * @author Maximilian.Auch
 */
@XmlRootElement
public class TemplateDto extends AbstractDto {

	@XmlElement
	private String name;


	/**
	 * Default-Konstruktor.
	 */
	public TemplateDto() {
	}


	/**
	 * Konstruktor zur Befuellung aller Attribute.
	 *
	 * @param id
	 *            die Id
	 * @param name
	 *            ServiceTemplate-Name String
	 */
	public TemplateDto(final Long id, final String name) {
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
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
