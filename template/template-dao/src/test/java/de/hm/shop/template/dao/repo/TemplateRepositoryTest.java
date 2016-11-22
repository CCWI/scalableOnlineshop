package de.hm.shop.template.dao.repo;

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

import de.hm.shop.template.dao.TestDaoConfig;
import de.hm.shop.template.dao.entity.TemplateEntity;
import de.hm.shop.template.dao.repo.TemplateRepository;



@ContextConfiguration(classes = { TestDaoConfig.class })
public class TemplateRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Inject
	private TemplateRepository sut;

	private TemplateEntity templateEntity;



	@Before
	public void setUp() {
		templateEntity = new TemplateEntity();
		templateEntity.setName("Markus Müller");
		templateEntity = sut.save(templateEntity);
	}



	@Test
	public void testSave_CreateNew() {
		final TemplateEntity serviceTemplateEntity1 = new TemplateEntity();
		serviceTemplateEntity1.setName("Hans Meier");

		final TemplateEntity result = sut.save(serviceTemplateEntity1);
		assertThat(result, is(notNullValue()));
		assertThat(result.getId(), is(notNullValue()));
		assertThat(result.getName(), is(equalTo(serviceTemplateEntity1.getName())));
	}



	@Test
	public void testSave_UpdateExisting() {
		templateEntity.setName("neu");

		final TemplateEntity result = sut.save(templateEntity);
		assertThat(result, is(notNullValue()));
		assertThat(result.getId(), is(equalTo(templateEntity.getId())));
		assertThat(result.getName(), is(equalTo(templateEntity.getName())));
	}



	@Test
	public void testDelete() {
		sut.delete(templateEntity.getId());
	}



	@Test
	public void testExists() {
		final boolean result = sut.exists(templateEntity.getId());
		assertThat(result, is(equalTo(true)));
	}



	@Test
	public void testFindOne() {
		final TemplateEntity result = sut.findOne(templateEntity.getId());
		assertThat(result, is(equalTo(templateEntity)));
	}



	@Test
	public void testFindAll() {
		final Iterable<TemplateEntity> result = sut.findAll();
		assertThat(result, is(notNullValue()));

		final Iterator<TemplateEntity> iterator = result.iterator();
		assertThat(iterator, is(notNullValue()));
		assertThat(iterator.hasNext(), is(equalTo(true)));
		assertThat(iterator.next(), is(equalTo(templateEntity)));
		assertThat(iterator.hasNext(), is(equalTo(false)));
	}
}
