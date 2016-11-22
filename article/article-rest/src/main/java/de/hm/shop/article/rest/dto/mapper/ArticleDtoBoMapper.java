package de.hm.shop.article.rest.dto.mapper;

import org.mapstruct.Mapper;

import de.hm.shop.article.rest.dto.ArticleDto;
import de.hm.shop.article.service.api.bo.ArticleBo;


/**
 * Mapper zwischen Dto und Bo (MapStruct)
 * @author Maximilian.Auch
 */
@Mapper
public interface ArticleDtoBoMapper {

	public ArticleDto mapBoToDto(ArticleBo articleBo);



	public ArticleBo mapDtoToBo(ArticleDto articleDto);
}
