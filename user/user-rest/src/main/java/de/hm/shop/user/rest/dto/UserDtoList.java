package de.hm.shop.user.rest.dto;

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
public class UserDtoList extends AbstractDtoList {

	@XmlElementWrapper(name = "userList")
	@XmlElement(name = "user")
	@JsonProperty("userList")
	private final Collection<UserDto> userList;



	// Wegen Jaxb erforderlich
	public UserDtoList() {
		this(null, (Link) null);
	}



	public UserDtoList(final Collection<UserDto> userList, final Link... links) {
		super(links);
		this.userList = userList;
	}



	public Collection<UserDto> getuserList() {
		return userList;
	}

}
