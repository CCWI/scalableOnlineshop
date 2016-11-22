package de.hm.shop.shippment.service.impl;

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

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import de.hm.shop.shippment.dao.entity.ShippmentEntity;
import de.hm.shop.shippment.dao.repo.ShippmentRepository;
import de.hm.shop.shippment.service.api.bo.ShippmentBo;
import de.hm.shop.shippment.service.api.exception.ShippmentException;
import de.hm.shop.shippment.service.impl.ShippmentServiceImpl;
import de.hm.shop.shippment.service.impl.mapper.ShippmentBoEntityMapper;


/**
 * 
 * @author Maximilian.Auch
 */
@RunWith(MockitoJUnitRunner.class)
public class ShippmentServiceImplTest {

	@InjectMocks
	private ShippmentServiceImpl sut;

	@Mock
	private ShippmentRepository shippmentRepositoryMock;
	@Mock
	private ShippmentBoEntityMapper shippmentMapperMock;

	@Mock
	private ShippmentEntity shippmentEntity1;
	@Mock
	private ShippmentEntity shippmentEntity2;
	@Mock
	private ShippmentBo shippmentDto1;
	@Mock
	private ShippmentBo shippmentDto2;



	@Test
	public void testGetAll() {
		when(shippmentRepositoryMock.findAll()).thenReturn(Lists.newArrayList(shippmentEntity1, shippmentEntity2));
		when(shippmentMapperMock.mapEntityToBo(shippmentEntity1)).thenReturn(shippmentDto1);
		when(shippmentMapperMock.mapEntityToBo(shippmentEntity2)).thenReturn(shippmentDto2);

		final List<ShippmentBo> result = sut.getAll();
		assertThat(result, is(notNullValue()));
		assertThat(result, containsInAnyOrder(shippmentDto1, shippmentDto2));

		final InOrder order = inOrder(shippmentRepositoryMock, shippmentMapperMock);
		order.verify(shippmentRepositoryMock).findAll();
		order.verify(shippmentMapperMock).mapEntityToBo(shippmentEntity1);
		order.verify(shippmentMapperMock).mapEntityToBo(shippmentEntity2);
		order.verifyNoMoreInteractions();
	}



	@Test
	public void testGetById() {
		when(shippmentRepositoryMock.findOne(Long.valueOf(100L))).thenReturn(shippmentEntity1);
		when(shippmentMapperMock.mapEntityToBo(shippmentEntity1)).thenReturn(shippmentDto1);

		final ShippmentBo result = sut.getById(100L);
		assertThat(result, is(theInstance(shippmentDto1)));

		final InOrder order = inOrder(shippmentRepositoryMock, shippmentMapperMock);
		order.verify(shippmentRepositoryMock).findOne(Long.valueOf(100L));
		order.verify(shippmentMapperMock).mapEntityToBo(shippmentEntity1);
		order.verifyNoMoreInteractions();
	}



	@Test
	public void testSave() throws ShippmentException {
		when(shippmentMapperMock.mapBoToEntity(shippmentDto1)).thenReturn(shippmentEntity1);
		when(shippmentRepositoryMock.save(shippmentEntity1)).thenReturn(shippmentEntity2);
		when(shippmentMapperMock.mapEntityToBo(shippmentEntity2)).thenReturn(shippmentDto2);

		final ShippmentBo result = sut.save(shippmentDto1);
		assertThat(result, is(theInstance(shippmentDto2)));

		final InOrder order = inOrder(shippmentRepositoryMock, shippmentMapperMock);
		order.verify(shippmentMapperMock).mapBoToEntity(shippmentDto1);
		order.verify(shippmentRepositoryMock).save(shippmentEntity1);
		order.verify(shippmentMapperMock).mapEntityToBo(shippmentEntity2);
		order.verifyNoMoreInteractions();
	}



	@Test
	public void testDelete() {
		final Long id = Long.valueOf(100L);
		when(shippmentRepositoryMock.exists(id)).thenReturn(true);

		sut.delete(100L);

		final InOrder order = inOrder(shippmentRepositoryMock);
		order.verify(shippmentRepositoryMock).exists(id);
		order.verify(shippmentRepositoryMock).delete(id);
		order.verifyNoMoreInteractions();

		verifyZeroInteractions(shippmentMapperMock);
	}



	@Test
	public void testDelete_NotFound() {
		final Long id = Long.valueOf(100L);

		sut.delete(id.longValue());

		final InOrder order = inOrder(shippmentRepositoryMock);
		order.verify(shippmentRepositoryMock).exists(id);
		order.verifyNoMoreInteractions();

		verify(shippmentRepositoryMock, never()).delete(id);
		verifyZeroInteractions(shippmentMapperMock);
	}
}