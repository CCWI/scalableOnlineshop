package de.hm.shop.article.dao.repo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Iterator;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import de.hm.shop.article.dao.TestDaoConfig;
import de.hm.shop.article.dao.entity.ArticleEntity;
import de.hm.shop.article.dao.repo.ArticleRepository;



@ContextConfiguration(classes = { TestDaoConfig.class })
public class ArticleRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Inject
	private ArticleRepository sut;

	private ArticleEntity articleEntity;



	@Before
	public void setUp() {
		articleEntity = new ArticleEntity();
		articleEntity.setId(new Long(123));
		articleEntity.setArticleTitle("title");
		articleEntity.setArticleStock(1);
		articleEntity.setArticlePrice(12.40);
		articleEntity.setArticleEAN("123456789123");
		articleEntity.setArticleDescription("testbeschreibung");
		articleEntity.setSupplierId(new Long(321));
		articleEntity = sut.save(articleEntity);
	}



	@Test
	public void testSave_CreateNew() {
		final ArticleEntity serviceArticleEntity1 = new ArticleEntity();
		serviceArticleEntity1.setId(new Long(123));
		serviceArticleEntity1.setArticleTitle("title");
		serviceArticleEntity1.setArticleStock(1);
		serviceArticleEntity1.setArticlePrice(12.40);
		serviceArticleEntity1.setArticleEAN("123456789123");
		serviceArticleEntity1.setArticleDescription("testbeschreibung");
		serviceArticleEntity1.setSupplierId(new Long(123));
		
		articleEntity = sut.save(articleEntity);

		final ArticleEntity result = sut.save(serviceArticleEntity1);
		assertThat(result, is(notNullValue()));
		assertThat(result.getId(), is(notNullValue()));
		assertThat(result.getArticleTitle(), is(equalTo(serviceArticleEntity1.getArticleTitle())));
		assertThat(result.getArticleStock(), is(equalTo(serviceArticleEntity1.getArticleStock())));
		assertThat(result.getArticlePrice(), is(equalTo(serviceArticleEntity1.getArticlePrice())));
		assertThat(result.getArticleEAN(), is(equalTo(serviceArticleEntity1.getArticleEAN())));
		assertThat(result.getArticleDescription(), is(equalTo(serviceArticleEntity1.getArticleDescription())));
		assertThat(result.getSupplierId(), is(equalTo(serviceArticleEntity1.getSupplierId())));
	}



	@Test
	public void testSave_UpdateExisting() {
		articleEntity.setArticleTitle("title2");
		articleEntity.setArticleStock(12);
		articleEntity.setArticlePrice(1.20);
		articleEntity.setArticleEAN("987654321");
		articleEntity.setArticleDescription("testbeschreibung");
		articleEntity.setSupplierId(new Long(124));
		articleEntity = sut.save(articleEntity);

		final ArticleEntity result = sut.save(articleEntity);
		assertThat(result, is(notNullValue()));
		assertThat(result.getId(), is(equalTo(articleEntity.getId())));
		assertThat(result.getArticleTitle(), is(equalTo(articleEntity.getArticleTitle())));
		assertThat(result.getArticleStock(), is(equalTo(articleEntity.getArticleStock())));
		assertThat(result.getArticlePrice(), is(equalTo(articleEntity.getArticlePrice())));
		assertThat(result.getArticleEAN(), is(equalTo(articleEntity.getArticleEAN())));
		assertThat(result.getArticleDescription(), is(equalTo(articleEntity.getArticleDescription())));
		assertThat(result.getSupplierId(), is(equalTo(articleEntity.getSupplierId())));
	}



	@Test
	public void testDelete() {
		sut.delete(articleEntity.getId());
	}



	@Test
	public void testExists() {
		final boolean result = sut.exists(articleEntity.getId());
		assertThat(result, is(equalTo(true)));
	}



	@Test
	public void testFindOne() {
		final ArticleEntity result = sut.findOne(articleEntity.getId());
		assertThat(result, is(equalTo(articleEntity)));
	}



	@Test
	public void testFindAll() {
		final Iterable<ArticleEntity> result = sut.findAll();
		assertThat(result, is(notNullValue()));

		final Iterator<ArticleEntity> iterator = result.iterator();
		assertThat(iterator, is(notNullValue()));
		assertThat(iterator.hasNext(), is(equalTo(true)));
		assertThat(iterator.next(), is(equalTo(articleEntity)));
		assertThat(iterator.hasNext(), is(equalTo(false)));
	}
}
