package de.hm.shop.article.rest.dto;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.Link;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * DTO in Listenform
 * @author Maximilian.Auch
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ArticleDtoList extends AbstractDtoList {

	@XmlElementWrapper(name = "articleList")
	@XmlElement(name = "article")
	@JsonProperty("articleList")
	private final Collection<ArticleDto> articleList;



	// Wegen Jaxb erforderlich
	public ArticleDtoList() {
		this(null, (Link) null);
	}



	public ArticleDtoList(final Collection<ArticleDto> articleList, final Link... links) {
		super(links);
		this.articleList = articleList;
	}



	public Collection<ArticleDto> getarticleList() {
		return articleList;
	}

}
