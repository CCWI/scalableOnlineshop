package de.hm.shop.shoppingcart.service.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.theInstance;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import de.hm.shop.shoppingcart.dao.entity.ShoppingcartEntity;
import de.hm.shop.shoppingcart.dao.repo.ShoppingcartRepository;
import de.hm.shop.shoppingcart.service.api.bo.ShoppingcartBo;
import de.hm.shop.shoppingcart.service.api.exception.ShoppingcartException;
import de.hm.shop.shoppingcart.service.impl.ShoppingcartServiceImpl;
import de.hm.shop.shoppingcart.service.impl.mapper.ShoppingcartBoEntityMapper;



@RunWith(MockitoJUnitRunner.class)
public class ShoppingcartServiceImplTest {

	@InjectMocks
	private ShoppingcartServiceImpl sut;

	@Mock
	private ShoppingcartRepository shoppingcartRepositoryMock;
	@Mock
	private ShoppingcartBoEntityMapper shoppingcartMapperMock;

	@Mock
	private ShoppingcartEntity shoppingcartEntity1;
	@Mock
	private ShoppingcartEntity shoppingcartEntity2;
	@Mock
	private ShoppingcartBo shoppingcartDto1;
	@Mock
	private ShoppingcartBo shoppingcartDto2;



	@Test
	public void testGetAllForUser() {
		when(shoppingcartRepositoryMock.findEntriesByUserId(Mockito.any())).thenReturn(Lists.newArrayList(shoppingcartEntity1, shoppingcartEntity2));
		when(shoppingcartMapperMock.mapEntityToBo(shoppingcartEntity1)).thenReturn(shoppingcartDto1);
		when(shoppingcartMapperMock.mapEntityToBo(shoppingcartEntity2)).thenReturn(shoppingcartDto2);

		final List<ShoppingcartBo> result = sut.getAllForUser(new Long(123));
		assertThat(result, is(notNullValue()));
		assertThat(result, containsInAnyOrder(shoppingcartDto1, shoppingcartDto2));

		final InOrder order = inOrder(shoppingcartRepositoryMock, shoppingcartMapperMock);
		order.verify(shoppingcartRepositoryMock).findEntriesByUserId(Mockito.any());
		order.verify(shoppingcartMapperMock).mapEntityToBo(shoppingcartEntity1);
		order.verify(shoppingcartMapperMock).mapEntityToBo(shoppingcartEntity2);
		order.verifyNoMoreInteractions();
	}



	@Test
	public void testSave() throws ShoppingcartException {
		when(shoppingcartMapperMock.mapBoToEntity(shoppingcartDto1)).thenReturn(shoppingcartEntity1);
		when(shoppingcartRepositoryMock.save(shoppingcartEntity1)).thenReturn(shoppingcartEntity2);
		when(shoppingcartMapperMock.mapEntityToBo(shoppingcartEntity2)).thenReturn(shoppingcartDto2);

		final ShoppingcartBo result = sut.save(shoppingcartDto1);
		assertThat(result, is(theInstance(shoppingcartDto2)));

		final InOrder order = inOrder(shoppingcartRepositoryMock, shoppingcartMapperMock);
		order.verify(shoppingcartMapperMock).mapBoToEntity(shoppingcartDto1);
		order.verify(shoppingcartRepositoryMock).save(shoppingcartEntity1);
		order.verify(shoppingcartMapperMock).mapEntityToBo(shoppingcartEntity2);
		order.verifyNoMoreInteractions();
	}
}