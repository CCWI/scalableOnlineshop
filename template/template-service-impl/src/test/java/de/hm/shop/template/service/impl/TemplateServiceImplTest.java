package de.hm.shop.template.service.impl;

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

import de.hm.shop.template.dao.entity.TemplateEntity;
import de.hm.shop.template.dao.repo.TemplateRepository;
import de.hm.shop.template.service.api.bo.TemplateBo;
import de.hm.shop.template.service.api.exception.TemplateException;
import de.hm.shop.template.service.impl.TemplateServiceImpl;
import de.hm.shop.template.service.impl.mapper.TemplateBoEntityMapper;


/**
 * 
 * @author Maximilian.Auch
 */
@RunWith(MockitoJUnitRunner.class)
public class TemplateServiceImplTest {

	@InjectMocks
	private TemplateServiceImpl sut;

	@Mock
	private TemplateRepository templateRepositoryMock;
	@Mock
	private TemplateBoEntityMapper templateMapperMock;

	@Mock
	private TemplateEntity templateEntity1;
	@Mock
	private TemplateEntity templateEntity2;
	@Mock
	private TemplateBo templateDto1;
	@Mock
	private TemplateBo templateDto2;



	@Test
	public void testGetAll() {
		when(templateRepositoryMock.findAll()).thenReturn(Lists.newArrayList(templateEntity1, templateEntity2));
		when(templateMapperMock.mapEntityToBo(templateEntity1)).thenReturn(templateDto1);
		when(templateMapperMock.mapEntityToBo(templateEntity2)).thenReturn(templateDto2);

		final List<TemplateBo> result = sut.getAll();
		assertThat(result, is(notNullValue()));
		assertThat(result, containsInAnyOrder(templateDto1, templateDto2));

		final InOrder order = inOrder(templateRepositoryMock, templateMapperMock);
		order.verify(templateRepositoryMock).findAll();
		order.verify(templateMapperMock).mapEntityToBo(templateEntity1);
		order.verify(templateMapperMock).mapEntityToBo(templateEntity2);
		order.verifyNoMoreInteractions();
	}



	@Test
	public void testGetById() {
		when(templateRepositoryMock.findOne(Long.valueOf(100L))).thenReturn(templateEntity1);
		when(templateMapperMock.mapEntityToBo(templateEntity1)).thenReturn(templateDto1);

		final TemplateBo result = sut.getById(100L);
		assertThat(result, is(theInstance(templateDto1)));

		final InOrder order = inOrder(templateRepositoryMock, templateMapperMock);
		order.verify(templateRepositoryMock).findOne(Long.valueOf(100L));
		order.verify(templateMapperMock).mapEntityToBo(templateEntity1);
		order.verifyNoMoreInteractions();
	}



	@Test
	public void testSave() throws TemplateException {
		when(templateMapperMock.mapBoToEntity(templateDto1)).thenReturn(templateEntity1);
		when(templateRepositoryMock.save(templateEntity1)).thenReturn(templateEntity2);
		when(templateMapperMock.mapEntityToBo(templateEntity2)).thenReturn(templateDto2);

		final TemplateBo result = sut.save(templateDto1);
		assertThat(result, is(theInstance(templateDto2)));

		final InOrder order = inOrder(templateRepositoryMock, templateMapperMock);
		order.verify(templateMapperMock).mapBoToEntity(templateDto1);
		order.verify(templateRepositoryMock).save(templateEntity1);
		order.verify(templateMapperMock).mapEntityToBo(templateEntity2);
		order.verifyNoMoreInteractions();
	}



	@Test
	public void testDelete() {
		final Long id = Long.valueOf(100L);
		when(templateRepositoryMock.exists(id)).thenReturn(true);

		sut.delete(100L);

		final InOrder order = inOrder(templateRepositoryMock);
		order.verify(templateRepositoryMock).exists(id);
		order.verify(templateRepositoryMock).delete(id);
		order.verifyNoMoreInteractions();

		verifyZeroInteractions(templateMapperMock);
	}



	@Test
	public void testDelete_NotFound() {
		final Long id = Long.valueOf(100L);

		sut.delete(id.longValue());

		final InOrder order = inOrder(templateRepositoryMock);
		order.verify(templateRepositoryMock).exists(id);
		order.verifyNoMoreInteractions();

		verify(templateRepositoryMock, never()).delete(id);
		verifyZeroInteractions(templateMapperMock);
	}
}