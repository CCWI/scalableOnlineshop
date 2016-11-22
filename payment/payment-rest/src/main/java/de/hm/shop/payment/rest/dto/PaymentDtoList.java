package de.hm.shop.payment.rest.dto;

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
public class PaymentDtoList extends AbstractDtoList {

	@XmlElementWrapper(name = "paymentList")
	@XmlElement(name = "payment")
	@JsonProperty("paymentList")
	private final Collection<PaymentDto> paymentList;



	// Wegen Jaxb erforderlich
	public PaymentDtoList() {
		this(null, (Link) null);
	}



	public PaymentDtoList(final Collection<PaymentDto> paymentList, final Link... links) {
		super(links);
		this.paymentList = paymentList;
	}



	public Collection<PaymentDto> getpaymentList() {
		return paymentList;
	}

}
