package de.hm.shop.payment.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * Payment-Entität.
 * @author Maximilian.Auch
 */
@Entity
@Table(name = "Payment")
@SequenceGenerator(name = AbstractEntity.ID_GENERATOR_NAME, sequenceName = "SEQ_Payment", initialValue = 1,
		allocationSize = 1)
public class PaymentEntity extends AbstractEntity {

	@Column
	private Long supplierId;
	
	@Column
	private String method;


	
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