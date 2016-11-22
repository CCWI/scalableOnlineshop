package de.hm.shop.payment.service.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.theInstance;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import de.hm.shop.payment.dao.entity.PaymentEntity;
import de.hm.shop.payment.dao.repo.PaymentRepository;
import de.hm.shop.payment.service.api.bo.PaymentBo;
import de.hm.shop.payment.service.api.exception.PaymentException;
import de.hm.shop.payment.service.impl.PaymentServiceImpl;
import de.hm.shop.payment.service.impl.mapper.PaymentBoEntityMapper;


/**
 * 
 * @author Maximilian.Auch
 */
@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceImplTest {

	@InjectMocks
	private PaymentServiceImpl sut;

	@Mock
	private PaymentRepository paymentRepositoryMock;
	@Mock
	private PaymentBoEntityMapper paymentMapperMock;

	@Mock
	private PaymentEntity paymentEntity1;
	@Mock
	private PaymentEntity paymentEntity2;
	@Mock
	private PaymentBo paymentDto1;
	@Mock
	private PaymentBo paymentDto2;



	@Test
	public void testGetAll() {
		when(paymentRepositoryMock.findEntriesByArticleId(Mockito.anyLong())).thenReturn(Lists.newArrayList(paymentEntity1, paymentEntity2));
		when(paymentMapperMock.mapEntityToBo(paymentEntity1)).thenReturn(paymentDto1);
		when(paymentMapperMock.mapEntityToBo(paymentEntity2)).thenReturn(paymentDto2);

		final Collection<PaymentBo> result = sut.getBySupplierId(100L);
		assertThat(result, is(notNullValue()));
		assertThat(result, containsInAnyOrder(paymentDto1, paymentDto2));

		final InOrder order = inOrder(paymentRepositoryMock, paymentMapperMock);
		order.verify(paymentRepositoryMock).findEntriesByArticleId(100L);
		order.verify(paymentMapperMock).mapEntityToBo(paymentEntity1);
		order.verify(paymentMapperMock).mapEntityToBo(paymentEntity2);
		order.verifyNoMoreInteractions();
	}



	@Test
	public void testSave() throws PaymentException {
		when(paymentMapperMock.mapBoToEntity(paymentDto1)).thenReturn(paymentEntity1);
		when(paymentRepositoryMock.save(paymentEntity1)).thenReturn(paymentEntity2);
		when(paymentMapperMock.mapEntityToBo(paymentEntity2)).thenReturn(paymentDto2);

		final PaymentBo result = sut.save(paymentDto1);
		assertThat(result, is(theInstance(paymentDto2)));

		final InOrder order = inOrder(paymentRepositoryMock, paymentMapperMock);
		order.verify(paymentMapperMock).mapBoToEntity(paymentDto1);
		order.verify(paymentRepositoryMock).save(paymentEntity1);
		order.verify(paymentMapperMock).mapEntityToBo(paymentEntity2);
		order.verifyNoMoreInteractions();
	}



	@Test
	public void testDelete() {
		final Long id = Long.valueOf(100L);
		when(paymentRepositoryMock.exists(id)).thenReturn(true);

		sut.delete(100L);

		final InOrder order = inOrder(paymentRepositoryMock);
		order.verify(paymentRepositoryMock).exists(id);
		order.verify(paymentRepositoryMock).delete(id);
		order.verifyNoMoreInteractions();

		verifyZeroInteractions(paymentMapperMock);
	}



	@Test
	public void testDelete_NotFound() {
		final Long id = Long.valueOf(100L);

		sut.delete(id.longValue());

		final InOrder order = inOrder(paymentRepositoryMock);
		order.verify(paymentRepositoryMock).exists(id);
		order.verifyNoMoreInteractions();

		verify(paymentRepositoryMock, never()).delete(id);
		verifyZeroInteractions(paymentMapperMock);
	}
}