package de.hm.shop.shippment.service.api.bo;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;



/**
 * Shippment-Businessobjekt.
 * @author Maximilian.Auch
 */
public class ShippmentBo extends AbstractBo {

	private Boolean shipmentReady;
	
	private Integer shippingDays;
	
	private String shippingMethod;



	/**
	 * Default-Konstruktor.
	 */
	public ShippmentBo() {
	}



	/**
	 * Konstruktor zur Befuellung aller Attribute.
	 * @param shipmentReady
	 * @param shippingDays
	 * @param shippingMethod
	 */
	public ShippmentBo(Boolean shipmentReady, Integer shippingDays, String shippingMethod) {
		super();
		this.shipmentReady = shipmentReady;
		this.shippingDays = shippingDays;
		this.shippingMethod = shippingMethod;
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
