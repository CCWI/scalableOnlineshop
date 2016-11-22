package de.hm.shop.article.service.api.bo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;



/**
 * Article-Businessobjekt.
 * @author Maximilian.Auch
 */
public class ArticleBo extends AbstractBo {

	/**
	 * Titel des Article-Objekts
	 */
    private String articleTitle;
	
	/**
	 * Beschreibung des Article-Objekts
	 */
    private String articleDescription;
	
	/**
	 * EAN-Code des Article-Objekts
	 */
    private String articleEAN;
	
	/**
	 * Preis des Article-Objekts
	 */
    private Double articlePrice;
	
	
	/**
	 * Stückzahl des Article-Objekts
	 */
    private Integer articleStock;
	
	/**
	 * SupplierId des Article-Objekts
	 */
    private Long supplierId;




	/**
	 * Default-Konstruktor.
	 */
	public ArticleBo() {
	}



	/**
	 * Konstruktor zur Befuellung aller Attribute.
	 *
	 * @param name
	 *            Name
	 */
	public ArticleBo(final Long id, final String articleTitle, final String articleDescription, 
			final String articleEAN, final Double articlePrice, final Integer articleStock, final Long supplierId) {
		super(id);
		this.articleTitle = articleTitle;
		this.articleDescription = articleDescription;
		this.articleEAN = articleEAN;
		this.articlePrice = articlePrice;
		this.articleStock = articleStock;
		this.supplierId = supplierId;
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
