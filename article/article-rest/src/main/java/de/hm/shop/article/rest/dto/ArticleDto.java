package de.hm.shop.article.rest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * ArticleDTO.
 * @author Maximilian.Auch
 */
@XmlRootElement
public class ArticleDto extends AbstractDto {

	/**
	 * Titel des Article-Objekts
	 */
	@XmlElement
	private String articleTitle;
	
	/**
	 * Beschreibung des Article-Objekts
	 */
	@XmlElement
    private String articleDescription;
	
	/**
	 * EAN-Code des Article-Objekts
	 */
	@XmlElement
    private String articleEAN;
	
	/**
	 * Preis des Article-Objekts
	 */
	@XmlElement
    private Double articlePrice;
	
	
	/**
	 * Stückzahl des Article-Objekts
	 */
	@XmlElement
    private Integer articleStock;
	
	/**
	 * SupplierId des Article-Objekts
	 */
	@XmlElement
    private Long supplierId;

	
	

	/**
	 * Default-Konstruktor.
	 */
	public ArticleDto() {
	}


	/**
	 * Konstruktor zur Befuellung aller Attribute.
	 *
	 * @param id
	 *            die Id
	 * @param name
	 *            ServiceArticle-Name String
	 */
	public ArticleDto(final Long id, final String articleTitle, final String articleDescription, 
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
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
