package de.hm.shop.payment.service.api.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;



/**
 * Payment-Businessobjekt.
 * @author Maximilian.Auch
 */
public class PaymentBo extends AbstractBo {

	private Long supplierId;
	
	private String method;


	/**
	 * Default-Konstruktor.
	 */
	public PaymentBo() {
	}



	/**
	 * Konstruktor zur Befuellung aller Attribute.
	 *
	 * @param name
	 *            Name
	 */
	public PaymentBo(final Long id, final Long supplierId, final String method) {
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
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}



	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
