package de.hm.shop.shippment.rest.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.Link;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Abstraktes DTO-Element in Listenform für das Arbeiten auf Collections
 * @author Maximilian.Auch
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractDtoList {

	@XmlElementWrapper(name = "links")
	@XmlElement(name = "link", namespace = Link.ATOM_NAMESPACE)
	@JsonProperty("links")
	private final Collection<Link> links = new ArrayList<>();


	// Wegen Jaxb erforderlich
	public AbstractDtoList() {
		this((Link) null);
	}


	public AbstractDtoList(final Link... links) {
		if (links != null) {
			this.links.addAll(Arrays.asList(links));
		}
	}


	public Collection<Link> getLinks() {
		return links;
	}

}
