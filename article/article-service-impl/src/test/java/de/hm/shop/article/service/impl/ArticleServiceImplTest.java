package de.hm.shop.article.service.impl;

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

import de.hm.shop.article.dao.entity.ArticleEntity;
import de.hm.shop.article.dao.repo.ArticleRepository;
import de.hm.shop.article.service.api.bo.ArticleBo;
import de.hm.shop.article.service.api.exception.ArticleException;
import de.hm.shop.article.service.impl.ArticleServiceImpl;
import de.hm.shop.article.service.impl.mapper.ArticleBoEntityMapper;


/**
 * 
 * @author Maximilian.Auch
 */
@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceImplTest {

	@InjectMocks
	private ArticleServiceImpl sut;

	@Mock
	private ArticleRepository articleRepositoryMock;
	@Mock
	private ArticleBoEntityMapper articleMapperMock;

	@Mock
	private ArticleEntity articleEntity1;
	@Mock
	private ArticleEntity articleEntity2;
	@Mock
	private ArticleBo articleDto1;
	@Mock
	private ArticleBo articleDto2;



	@Test
	public void testGetAll() {
		when(articleRepositoryMock.findAll()).thenReturn(Lists.newArrayList(articleEntity1, articleEntity2));
		when(articleMapperMock.mapEntityToBo(articleEntity1)).thenReturn(articleDto1);
		when(articleMapperMock.mapEntityToBo(articleEntity2)).thenReturn(articleDto2);

		final List<ArticleBo> result = sut.getAll();
		assertThat(result, is(notNullValue()));
		assertThat(result, containsInAnyOrder(articleDto1, articleDto2));

		final InOrder order = inOrder(articleRepositoryMock, articleMapperMock);
		order.verify(articleRepositoryMock).findAll();
		order.verify(articleMapperMock).mapEntityToBo(articleEntity1);
		order.verify(articleMapperMock).mapEntityToBo(articleEntity2);
		order.verifyNoMoreInteractions();
	}



	@Test
	public void testGetById() {
		when(articleRepositoryMock.findOne(Long.valueOf(100L))).thenReturn(articleEntity1);
		when(articleMapperMock.mapEntityToBo(articleEntity1)).thenReturn(articleDto1);

		final ArticleBo result = sut.getById(100L);
		assertThat(result, is(theInstance(articleDto1)));

		final InOrder order = inOrder(articleRepositoryMock, articleMapperMock);
		order.verify(articleRepositoryMock).findOne(Long.valueOf(100L));
		order.verify(articleMapperMock).mapEntityToBo(articleEntity1);
		order.verifyNoMoreInteractions();
	}



	@Test
	public void testSave() throws ArticleException {
		when(articleMapperMock.mapBoToEntity(articleDto1)).thenReturn(articleEntity1);
		when(articleRepositoryMock.save(articleEntity1)).thenReturn(articleEntity2);
		when(articleMapperMock.mapEntityToBo(articleEntity2)).thenReturn(articleDto2);

		final ArticleBo result = sut.save(articleDto1);
		assertThat(result, is(theInstance(articleDto2)));

		final InOrder order = inOrder(articleRepositoryMock, articleMapperMock);
		order.verify(articleMapperMock).mapBoToEntity(articleDto1);
		order.verify(articleRepositoryMock).save(articleEntity1);
		order.verify(articleMapperMock).mapEntityToBo(articleEntity2);
		order.verifyNoMoreInteractions();
	}



	@Test
	public void testDelete() {
		final Long id = Long.valueOf(100L);
		when(articleRepositoryMock.exists(id)).thenReturn(true);

		sut.delete(100L);

		final InOrder order = inOrder(articleRepositoryMock);
		order.verify(articleRepositoryMock).exists(id);
		order.verify(articleRepositoryMock).delete(id);
		order.verifyNoMoreInteractions();

		verifyZeroInteractions(articleMapperMock);
	}



	@Test
	public void testDelete_NotFound() {
		final Long id = Long.valueOf(100L);

		sut.delete(id.longValue());

		final InOrder order = inOrder(articleRepositoryMock);
		order.verify(articleRepositoryMock).exists(id);
		order.verifyNoMoreInteractions();

		verify(articleRepositoryMock, never()).delete(id);
		verifyZeroInteractions(articleMapperMock);
	}
}