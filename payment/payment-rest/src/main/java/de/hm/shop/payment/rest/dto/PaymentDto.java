package de.hm.shop.payment.rest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * PaymentDTO.
 * @author Maximilian.Auch
 */
@XmlRootElement
public class PaymentDto extends AbstractDto {

	@XmlElement
	private Long supplierId;
	
	@XmlElement
	private String method;


	/**
	 * Default-Konstruktor.
	 */
	public PaymentDto() {
	}


	/**
	 * Konstruktor zur Befuellung aller Attribute.
	 *
	 * @param id
	 *            die Id
	 * @param supplierId
	 * 			  die ID des Suppliers
	 * @param name
	 *            ServicePayment-Name String
	 */
	public PaymentDto(final Long id, final Long supplierId, final String method) {
		super(id);
		this.supplierId = supplierId;
		this.method = method;
	}



	public Long getSupplierId() {
		return supplierId;
	}



	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}



	public String getMethod() {
		return method;
	}



	public void setMethod(String method) {
		this.method = method;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
