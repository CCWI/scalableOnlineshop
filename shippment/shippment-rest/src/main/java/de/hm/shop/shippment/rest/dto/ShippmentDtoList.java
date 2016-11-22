package de.hm.shop.shippment.rest.dto;

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
public class ShippmentDtoList extends AbstractDtoList {

	@XmlElementWrapper(name = "shippmentList")
	@XmlElement(name = "shippment")
	@JsonProperty("shippmentList")
	private final Collection<ShippmentDto> shippmentList;



	// Wegen Jaxb erforderlich
	public ShippmentDtoList() {
		this(null, (Link) null);
	}



	public ShippmentDtoList(final Collection<ShippmentDto> shippmentList, final Link... links) {
		super(links);
		this.shippmentList = shippmentList;
	}



	public Collection<ShippmentDto> getshippmentList() {
		return shippmentList;
	}

}
