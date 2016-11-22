package de.hm.shop.shippment.dao.repo;

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

import de.hm.shop.shippment.dao.TestDaoConfig;
import de.hm.shop.shippment.dao.entity.ShippmentEntity;
import de.hm.shop.shippment.dao.repo.ShippmentRepository;



@ContextConfiguration(classes = { TestDaoConfig.class })
public class ShippmentRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Inject
	private ShippmentRepository sut;

	private ShippmentEntity shippmentEntity;



	@Before
	public void setUp() {
		shippmentEntity = new ShippmentEntity();
		shippmentEntity.setId(new Long(1));
		shippmentEntity.setShipmentReady(true);
		shippmentEntity.setShippingDays(3);
		shippmentEntity.setShippingMethod("DHL");

		shippmentEntity = sut.save(shippmentEntity);
	}



	@Test
	public void testSave_CreateNew() {
		final ShippmentEntity serviceShippmentEntity1 = new ShippmentEntity();
		serviceShippmentEntity1.setShipmentReady(false);
		serviceShippmentEntity1.setShippingDays(2);
		serviceShippmentEntity1.setShippingMethod("DHL");
		
		final ShippmentEntity result = sut.save(serviceShippmentEntity1);
		assertThat(result, is(notNullValue()));
		assertThat(result.getId(), is(notNullValue()));
		assertThat(result.getShipmentReady(), is(equalTo(serviceShippmentEntity1.getShipmentReady())));
		assertThat(result.getShippingDays(), is(equalTo(serviceShippmentEntity1.getShippingDays())));
		assertThat(result.getShippingMethod(), is(equalTo(serviceShippmentEntity1.getShippingMethod())));
	}



	@Test
	public void testSave_UpdateExisting() {
		shippmentEntity.setShipmentReady(false);
		shippmentEntity.setShippingDays(2);
		shippmentEntity.setShippingMethod("DHL");
		
		final ShippmentEntity result = sut.save(shippmentEntity);
		assertThat(result, is(notNullValue()));
		assertThat(result.getId(), is(equalTo(shippmentEntity.getId())));
		assertThat(result.getShipmentReady(), is(equalTo(shippmentEntity.getShipmentReady())));
		assertThat(result.getShippingDays(), is(equalTo(shippmentEntity.getShippingDays())));
		assertThat(result.getShippingMethod(), is(equalTo(shippmentEntity.getShippingMethod())));
	}



	@Test
	public void testDelete() {
		sut.delete(shippmentEntity.getId());
	}



	@Test
	public void testExists() {
		final boolean result = sut.exists(shippmentEntity.getId());
		assertThat(result, is(equalTo(true)));
	}



	@Test
	public void testFindOne() {
		final ShippmentEntity result = sut.findOne(shippmentEntity.getId());
		assertThat(result, is(equalTo(shippmentEntity)));
	}



	@Test
	public void testFindAll() {
		final Iterable<ShippmentEntity> result = sut.findAll();
		assertThat(result, is(notNullValue()));

		final Iterator<ShippmentEntity> iterator = result.iterator();
		assertThat(iterator, is(notNullValue()));
		assertThat(iterator.hasNext(), is(equalTo(true)));
		assertThat(iterator.next(), is(equalTo(shippmentEntity)));
		assertThat(iterator.hasNext(), is(equalTo(false)));
	}
}
