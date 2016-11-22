package de.hm.shop.template.rest.dto;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.Link;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * DTO in Listenform
 * @author Maximilian.Auch
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TemplateDtoList extends AbstractDtoList {

	@XmlElementWrapper(name = "templateList")
	@XmlElement(name = "template")
	@JsonProperty("templateList")
	private final Collection<TemplateDto> templateList;



	// Wegen Jaxb erforderlich
	public TemplateDtoList() {
		this(null, (Link) null);
	}



	public TemplateDtoList(final Collection<TemplateDto> templateList, final Link... links) {
		super(links);
		this.templateList = templateList;
	}



	public Collection<TemplateDto> gettemplateList() {
		return templateList;
	}

}
