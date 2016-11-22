package de.hm.shop.shoppingcart.rest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;



/**
 * Dto eines Shoppingcart-Eintrags.
 * @author Maximilian.Auch
 */
@XmlRootElement
public class ShoppingcartDto extends AbstractDto {

	@XmlElement
	private Long articleId;

	@XmlElement
	private Long userId;
	
	@XmlElement
	private Integer quantity;


	
	/**
	 * Default-Konstruktor.
	 */
	public ShoppingcartDto() {
	}



	/**
	 * Konstruktor zur Befuellung aller Attribute.
	 * @param id
	 * @param articleId
	 * @param userId
	 * @param quantity
	 */
	public ShoppingcartDto(final Long id, final Long articleId, 
			final Long userId, final Integer quantity) {
		super(id);
		this.articleId = articleId;
		this.userId = userId;
		this.quantity = quantity;
	}



	public Long getArticleId() {
		return articleId;
	}



	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}



	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public Integer getQuantity() {
		return quantity;
	}



	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
