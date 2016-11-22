package de.hm.shop.article.service.impl.mapper;

import org.mapstruct.Mapper;

import de.hm.shop.article.dao.entity.ArticleEntity;
import de.hm.shop.article.service.api.bo.ArticleBo;


/**
 * 
 * @author Maximilian.Auch
 */
@Mapper
public interface ArticleBoEntityMapper {

	public ArticleBo mapEntityToBo(ArticleEntity articleEntity);



	public ArticleEntity mapBoToEntity(ArticleBo articleBo);
}
