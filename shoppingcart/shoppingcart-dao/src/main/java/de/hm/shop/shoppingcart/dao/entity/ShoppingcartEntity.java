package de.hm.shop.shoppingcart.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;



/**
 * Shoppingcart-Entität.
 * @author Maximilian.Auch
 */
@Entity
@Table(name = "Shoppingcart")
@SequenceGenerator(name = AbstractEntity.ID_GENERATOR_NAME, sequenceName = "SEQ_Shoppingcart", initialValue = 1,
		allocationSize = 1)
public class ShoppingcartEntity extends AbstractEntity {

	
	@Column
	private Long articleId;

	
	@Column
	private Long userId;
	
	
	@Column
	private Integer quantity;

	/**
	 * Default-Konstruktor
	 */
	public ShoppingcartEntity(){
	}

	/**
	 * Konstruktor
	 * @param articleId
	 * @param userId
	 * @param quantity
	 */
	public ShoppingcartEntity(Long articleId, Long userId, Integer quantity) {
		super();
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