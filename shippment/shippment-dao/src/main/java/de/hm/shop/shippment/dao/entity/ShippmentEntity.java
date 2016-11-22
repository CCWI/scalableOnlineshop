package de.hm.shop.shippment.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * Shippment-Entität.
 * @author Maximilian.Auch
 */
@Entity
@Table(name = "Shippment")
@SequenceGenerator(name = AbstractEntity.ID_GENERATOR_NAME, sequenceName = "SEQ_Shippment", initialValue = 1,
		allocationSize = 1)
public class ShippmentEntity extends AbstractEntity {

	@Column
	private Boolean shipmentReady;
	
	@Column
	private Integer shippingDays;
	
	@Column
	private String shippingMethod;

	
	/**
	 * Default-Konstruktor
	 */
	public ShippmentEntity() {
	}


	public Boolean getShipmentReady() {
		return shipmentReady;
	}



	public void setShipmentReady(Boolean shipmentReady) {
		this.shipmentReady = shipmentReady;
	}



	public Integer getShippingDays() {
		return shippingDays;
	}



	public void setShippingDays(Integer shippingDays) {
		this.shippingDays = shippingDays;
	}



	public String getShippingMethod() {
		return shippingMethod;
	}



	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
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