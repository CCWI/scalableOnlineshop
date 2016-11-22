package de.hm.shop.user.dao.repo;

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

import de.hm.shop.user.dao.TestDaoConfig;
import de.hm.shop.user.dao.entity.UserEntity;
import de.hm.shop.user.dao.repo.UserRepository;



@ContextConfiguration(classes = { TestDaoConfig.class })
public class UserRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Inject
	private UserRepository sut;

	private UserEntity userEntity;



	@Before
	public void setUp() {
		userEntity = new UserEntity();
		userEntity.setId(new Long(423));
		userEntity.setFirstname("Max");
		userEntity.setLastname("Auch");
		userEntity.setAddress("Dachauerstraße");
		userEntity.setPostcode("80335");
		userEntity.setCity("München");
		userEntity.setCountry("Deutschland");
		userEntity = sut.save(userEntity);
	}



	@Test
	public void testSave_CreateNew() {
		final UserEntity serviceUserEntity1 = new UserEntity();
		serviceUserEntity1.setId(new Long(321));
		serviceUserEntity1.setFirstname("Max");
		serviceUserEntity1.setLastname("Auch");
		serviceUserEntity1.setAddress("Dachauerstraße");
		serviceUserEntity1.setPostcode("80335");
		serviceUserEntity1.setCity("München");
		serviceUserEntity1.setCountry("Deutschland");

		final UserEntity result = sut.save(serviceUserEntity1);
		assertThat(result, is(notNullValue()));
		assertThat(result.getId(), is(notNullValue()));
		assertThat(result.getFirstname(), is(equalTo(serviceUserEntity1.getFirstname())));
		assertThat(result.getLastname(), is(equalTo(serviceUserEntity1.getLastname())));
		assertThat(result.getCountry(), is(equalTo(serviceUserEntity1.getCountry())));
		assertThat(result.getPostcode(), is(equalTo(serviceUserEntity1.getPostcode())));
		assertThat(result.getSupplierId(), is(equalTo(serviceUserEntity1.getSupplierId())));
	}



	@Test
	public void testSave_UpdateExisting() {
		userEntity.setId(new Long(213));
		userEntity.setFirstname("Max");
		userEntity.setLastname("Auch");
		userEntity.setAddress("Dachauerstraße");
		userEntity.setPostcode("80335");
		userEntity.setCity("München");
		userEntity.setCountry("Deutschland");

		final UserEntity result = sut.save(userEntity);
		assertThat(result, is(notNullValue()));
		assertThat(result.getId(), is(equalTo(userEntity.getId())));
		assertThat(result.getAddress(), is(equalTo(userEntity.getAddress())));
		assertThat(result.getCity(), is(equalTo(userEntity.getCity())));
		assertThat(result.getFirstname(), is(equalTo(userEntity.getFirstname())));
		assertThat(result.getId(), is(equalTo(userEntity.getId())));
		assertThat(result.getSupplierId(), is(equalTo(userEntity.getSupplierId())));

	}



	@Test
	public void testDelete() {
		sut.delete(userEntity.getId());
	}



	@Test
	public void testExists() {
		final boolean result = sut.exists(userEntity.getSupplierId());
		assertThat(result, is(equalTo(true)));
	}



	@Test
	public void testFindOne() {
		final UserEntity result = sut.findOne(userEntity.getId());
		assertThat(result, is(equalTo(userEntity)));
	}



	@Test
	public void testFindAll() {
		final Iterable<UserEntity> result = sut.findAll();
		assertThat(result, is(notNullValue()));

		final Iterator<UserEntity> iterator = result.iterator();
		assertThat(iterator, is(notNullValue()));
		assertThat(iterator.hasNext(), is(equalTo(true)));
		assertThat(iterator.next(), is(equalTo(userEntity)));
		assertThat(iterator.hasNext(), is(equalTo(false)));
	}
}
