package de.hm.shop.shoppingcart.rest.dto;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.Link;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Listeneinträge der DTOs
 * @author Maximilian.Auch
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShoppingcartDtoList extends AbstractDtoList {

	@XmlElementWrapper(name = "shoppingcartList")
	@XmlElement(name = "shoppingcart")
	@JsonProperty("shoppingcartList")
	private final Collection<ShoppingcartDto> shoppingcartList;



	// Wegen Jaxb erforderlich
	public ShoppingcartDtoList() {
		this(null, (Link) null);
	}



	public ShoppingcartDtoList(final Collection<ShoppingcartDto> shoppingcartList, final Link... links) {
		super(links);
		this.shoppingcartList = shoppingcartList;
	}



	public Collection<ShoppingcartDto> getshoppingcartList() {
		return shoppingcartList;
	}

}
