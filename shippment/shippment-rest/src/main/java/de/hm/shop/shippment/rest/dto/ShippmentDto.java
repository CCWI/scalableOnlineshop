package de.hm.shop.shippment.rest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * ShippmentDTO.
 * @author Maximilian.Auch
 */
@XmlRootElement
public class ShippmentDto extends AbstractDto {

	@XmlElement
	private Boolean shipmentReady;

	@XmlElement
	private Integer shippingDays;
	
	@XmlElement
	private String shippingMethod;


	/**
	 * Default-Konstruktor.
	 */
	public ShippmentDto() {
	}


	/**
	 * Konstruktor zur Befuellung aller Attribute.
	 * @param shipmentReady
	 * @param shippingDays
	 * @param shippingMethod
	 */
	public ShippmentDto(Boolean shipmentReady, Integer shippingDays, String shippingMethod) {
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
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
