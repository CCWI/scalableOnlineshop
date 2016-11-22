package de.hm.shop.user.service.impl;

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

import de.hm.shop.user.dao.entity.UserEntity;
import de.hm.shop.user.dao.repo.UserRepository;
import de.hm.shop.user.service.api.bo.UserBo;
import de.hm.shop.user.service.api.exception.UserException;
import de.hm.shop.user.service.impl.UserServiceImpl;
import de.hm.shop.user.service.impl.mapper.UserBoEntityMapper;


/**
 * 
 * @author Maximilian.Auch
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl sut;

	@Mock
	private UserRepository userRepositoryMock;
	@Mock
	private UserBoEntityMapper userMapperMock;

	@Mock
	private UserEntity userEntity1;
	@Mock
	private UserEntity userEntity2;
	@Mock
	private UserBo userDto1;
	@Mock
	private UserBo userDto2;



	@Test
	public void testGetAll() {
		when(userRepositoryMock.findAll()).thenReturn(Lists.newArrayList(userEntity1, userEntity2));
		when(userMapperMock.mapEntityToBo(userEntity1)).thenReturn(userDto1);
		when(userMapperMock.mapEntityToBo(userEntity2)).thenReturn(userDto2);

		final List<UserBo> result = sut.getAll();
		assertThat(result, is(notNullValue()));
		assertThat(result, containsInAnyOrder(userDto1, userDto2));

		final InOrder order = inOrder(userRepositoryMock, userMapperMock);
		order.verify(userRepositoryMock).findAll();
		order.verify(userMapperMock).mapEntityToBo(userEntity1);
		order.verify(userMapperMock).mapEntityToBo(userEntity2);
		order.verifyNoMoreInteractions();
	}



	@Test
	public void testGetById() {
		when(userRepositoryMock.findOne(Long.valueOf(100L))).thenReturn(userEntity1);
		when(userMapperMock.mapEntityToBo(userEntity1)).thenReturn(userDto1);

		final UserBo result = sut.getById(100L);
		assertThat(result, is(theInstance(userDto1)));

		final InOrder order = inOrder(userRepositoryMock, userMapperMock);
		order.verify(userRepositoryMock).findOne(Long.valueOf(100L));
		order.verify(userMapperMock).mapEntityToBo(userEntity1);
		order.verifyNoMoreInteractions();
	}



	@Test
	public void testSave() throws UserException {
		when(userMapperMock.mapBoToEntity(userDto1)).thenReturn(userEntity1);
		when(userRepositoryMock.save(userEntity1)).thenReturn(userEntity2);
		when(userMapperMock.mapEntityToBo(userEntity2)).thenReturn(userDto2);

		final UserBo result = sut.save(userDto1);
		assertThat(result, is(theInstance(userDto2)));

		final InOrder order = inOrder(userRepositoryMock, userMapperMock);
		order.verify(userMapperMock).mapBoToEntity(userDto1);
		order.verify(userRepositoryMock).save(userEntity1);
		order.verify(userMapperMock).mapEntityToBo(userEntity2);
		order.verifyNoMoreInteractions();
	}
	
}