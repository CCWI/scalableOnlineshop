package de.hm.shop.article.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * Article-Entität.
 * @author Maximilian.Auch
 */
@Entity
@Table(name = "Article")
@SequenceGenerator(name = AbstractEntity.ID_GENERATOR_NAME, sequenceName = "SEQ_Article", initialValue = 1,
		allocationSize = 1)
public class ArticleEntity extends AbstractEntity {
	
	/**
	 * Titel des Article-Objekts
	 */
	@Column
    private String articleTitle;
	
	/**
	 * Beschreibung des Article-Objekts
	 */
	@Column
    private String articleDescription;
	
	/**
	 * EAN-Code des Article-Objekts
	 */
	@Column
    private String articleEAN;
	
	/**
	 * Preis des Article-Objekts
	 */
	@Column
    private Double articlePrice;
	
	
	/**
	 * Stückzahl des Article-Objekts
	 */
	@Column
    private Integer articleStock;
	
	/**
	 * SupplierId des Article-Objekts
	 */
	@Column
	private Long supplierId;

	
	/**
	 * Default-Konstruktur
	 */
	public ArticleEntity(){
	}
	


	public String getArticleTitle() {
		return articleTitle;
	}



	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}



	public String getArticleDescription() {
		return articleDescription;
	}



	public void setArticleDescription(String articleDescription) {
		this.articleDescription = articleDescription;
	}



	public String getArticleEAN() {
		return articleEAN;
	}



	public void setArticleEAN(String articleEAN) {
		this.articleEAN = articleEAN;
	}



	public Double getArticlePrice() {
		return articlePrice;
	}



	public void setArticlePrice(Double articlePrice) {
		this.articlePrice = articlePrice;
	}



	public Integer getArticleStock() {
		return articleStock;
	}



	public void setArticleStock(Integer articleStock) {
		this.articleStock = articleStock;
	}



	public Long getSupplierId() {
		return supplierId;
	}



	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
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