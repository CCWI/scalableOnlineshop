package de.hm.shop.article.dao.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.hm.shop.article.dao.entity.ArticleEntity;


/**
 * Repository
 * @author Maximilian.Auch
 */
@Repository
public interface ArticleRepository extends PagingAndSortingRepository<ArticleEntity, Long> {

	
	/**
	 * Find Article by title
	 * @param articleTitle
	 * @return A List of Articles.
	 */
	@Query("SELECT p FROM ArticleEntity p WHERE p.articleTitle = :articleTitle")
	public List<ArticleEntity> findArticleByTitle(@Param("articleTitle") String articleTitle);

}
