package de.hm.shop.payment.dao.repo;

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

import de.hm.shop.payment.dao.TestDaoConfig;
import de.hm.shop.payment.dao.entity.PaymentEntity;
import de.hm.shop.payment.dao.repo.PaymentRepository;



@ContextConfiguration(classes = { TestDaoConfig.class })
public class PaymentRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Inject
	private PaymentRepository sut;

	private PaymentEntity paymentEntity;



	@Before
	public void setUp() {
		paymentEntity = new PaymentEntity();
		paymentEntity.setSupplierId(100L);
		paymentEntity.setMethod("Paypal");
		paymentEntity = sut.save(paymentEntity);
	}



	@Test
	public void testSave_CreateNew() {
		final PaymentEntity servicePaymentEntity1 = new PaymentEntity();
		servicePaymentEntity1.setSupplierId(101L);
		servicePaymentEntity1.setMethod("Bar");

		final PaymentEntity result = sut.save(servicePaymentEntity1);
		assertThat(result, is(notNullValue()));
		assertThat(result.getId(), is(notNullValue()));
		assertThat(result.getSupplierId(), is(equalTo(servicePaymentEntity1.getSupplierId())));
		assertThat(result.getMethod(), is(equalTo(servicePaymentEntity1.getMethod())));
	}



	@Test
	public void testSave_UpdateExisting() {
		paymentEntity.setMethod("Überweisung");

		final PaymentEntity result = sut.save(paymentEntity);
		assertThat(result, is(notNullValue()));
		assertThat(result.getId(), is(equalTo(paymentEntity.getId())));
		assertThat(result.getSupplierId(), is(equalTo(paymentEntity.getSupplierId())));
		assertThat(result.getMethod(), is(equalTo(paymentEntity.getMethod())));
	}



	@Test
	public void testDelete() {
		sut.delete(paymentEntity.getId());
	}



	@Test
	public void testExists() {
		final boolean result = sut.exists(paymentEntity.getId());
		assertThat(result, is(equalTo(true)));
	}



	@Test
	public void testFindOne() {
		final PaymentEntity result = sut.findOne(paymentEntity.getId());
		assertThat(result, is(equalTo(paymentEntity)));
	}



	@Test
	public void testFindAll() {
		final Iterable<PaymentEntity> result = sut.findAll();
		assertThat(result, is(notNullValue()));

		final Iterator<PaymentEntity> iterator = result.iterator();
		assertThat(iterator, is(notNullValue()));
		assertThat(iterator.hasNext(), is(equalTo(true)));
		assertThat(iterator.next(), is(equalTo(paymentEntity)));
		assertThat(iterator.hasNext(), is(equalTo(false)));
	}
}
