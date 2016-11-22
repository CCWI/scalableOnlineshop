package de.hm.shop.shoppingcart.dao.repo;

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

import de.hm.shop.shoppingcart.dao.TestDaoConfig;
import de.hm.shop.shoppingcart.dao.entity.ShoppingcartEntity;
import de.hm.shop.shoppingcart.dao.repo.ShoppingcartRepository;


/**
 * Tests auf der DAO-Ebene
 * @author Maximilian.Auch
 */
@ContextConfiguration(classes = { TestDaoConfig.class })
public class ShoppingcartRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Inject
	private ShoppingcartRepository sut;

	private ShoppingcartEntity shoppingcartEntity;



	@Before
	public void setUp() {
		shoppingcartEntity = new ShoppingcartEntity();
		shoppingcartEntity.setArticleId(new Long(123));
		shoppingcartEntity.setUserId(new Long(321));
		shoppingcartEntity.setQuantity(5);
		shoppingcartEntity = sut.save(shoppingcartEntity);
	}



	@Test
	public void testSave_CreateNew() {
		final ShoppingcartEntity shoppingcartEntity1 = new ShoppingcartEntity();
		shoppingcartEntity1.setArticleId(new Long(456));
		shoppingcartEntity1.setUserId(new Long(654));
		shoppingcartEntity1.setQuantity(2);

		final ShoppingcartEntity result = sut.save(shoppingcartEntity1);
		assertThat(result, is(notNullValue()));
		assertThat(result.getId(), is(notNullValue()));
		assertThat(result.getArticleId(), is(equalTo(shoppingcartEntity1.getArticleId())));
		assertThat(result.getUserId(), is(equalTo(shoppingcartEntity1.getUserId())));
		assertThat(result.getQuantity(), is(equalTo(shoppingcartEntity1.getQuantity())));
	}



	@Test
	public void testSave_UpdateExisting() {
		shoppingcartEntity.setArticleId(new Long(987));

		final ShoppingcartEntity result = sut.save(shoppingcartEntity);
		assertThat(result, is(notNullValue()));
		assertThat(result.getId(), is(equalTo(shoppingcartEntity.getId())));
		assertThat(result.getArticleId(), is(equalTo(shoppingcartEntity.getArticleId())));
	}



	@Test
	public void testDelete() {
		sut.delete(shoppingcartEntity.getId());
	}



	@Test
	public void testExists() {
		final boolean result = sut.exists(shoppingcartEntity.getId());
		assertThat(result, is(equalTo(true)));
	}



	@Test
	public void testFindOne() {
		final ShoppingcartEntity result = sut.findOne(shoppingcartEntity.getId());
		assertThat(result, is(equalTo(shoppingcartEntity)));
	}



	@Test
	public void testFindAll() {
		final Iterable<ShoppingcartEntity> result = sut.findAll();
		assertThat(result, is(notNullValue()));

		final Iterator<ShoppingcartEntity> iterator = result.iterator();
		assertThat(iterator, is(notNullValue()));
		assertThat(iterator.hasNext(), is(equalTo(true)));
		assertThat(iterator.next(), is(equalTo(shoppingcartEntity)));
		assertThat(iterator.hasNext(), is(equalTo(false)));
	}
}
