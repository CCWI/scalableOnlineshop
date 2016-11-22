package de.hm.shop.shoppingcart.service.api.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;



/**
 * Beispiel-Businessobjekt.
 */
public class ShoppingcartBo extends AbstractBo {

	
	private Long articleId;

	
	private Long userId;
	
	
	private Integer quantity;



	/**
	 * Default-Konstruktor.
	 */
	public ShoppingcartBo() {
	}



	/**
	 * Konstruktor zur Befuellung aller Attribute.
	 *
	 * @param name
	 *            Name
	 */
	public ShoppingcartBo(final Long id, final Long articleId, 
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
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}



	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
