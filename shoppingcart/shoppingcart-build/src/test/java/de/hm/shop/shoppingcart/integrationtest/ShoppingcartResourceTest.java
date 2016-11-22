package de.hm.shop.shoppingcart.integrationtest;

import static de.hm.shop.shoppingcart.integrationtest.matcher.RegexMatcher.matchesRegex;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.hateoas.Link;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.hm.shop.shoppingcart.Application;
import de.hm.shop.shoppingcart.rest.dto.ShoppingcartDtoList;
import de.hm.shop.shoppingcart.rest.dto.ShoppingcartDto;
import de.hm.shop.shoppingcart.service.api.ShoppingcartService;
import de.hm.shop.shoppingcart.service.api.bo.ShoppingcartBo;
import de.hm.shop.shoppingcart.service.api.exception.ShoppingcartException;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest("server.port=9000")
public class ShoppingcartResourceTest {

	private static final String HOST = "http://localhost:9000";
	private static final Logger LOG = Logger.getLogger(ShoppingcartResourceTest.class.getName());
	
	private final Long userId = Long.valueOf(100L);
	
	private final Client client = ClientBuilder.newClient().register(new LoggingFilter(LOG, true));

	@Inject
	private ShoppingcartService shoppingcartService;

	private ShoppingcartBo shoppingcartBo1;
	private String vaildAuthToken;


	@Before
	public void setUp() throws ShoppingcartException {
		shoppingcartBo1 = createAndSaveShoppingcartBo(new Long(123), this.userId, 50);
		
		String issuer = "shopsystem";
        String subject = "authentication";
        long ttlMiS = 1800000;
   		Map<String, Object> credentials = new HashMap<String, Object>();
   		
   		LinkedHashMap<String, String> userRolesMap = new LinkedHashMap<String, String>();
   		ArrayList<LinkedHashMap<String, String>> roles = new ArrayList<LinkedHashMap<String, String>>();
   		
   		userRolesMap.put("role", "admin");
   		userRolesMap.put("role", "user");
   		roles.add(userRolesMap);
   		credentials.put("role", roles);
		credentials.put("userId", this.userId);

    	vaildAuthToken = new JWTTokenGenerator().createJWT("1", issuer, subject, ttlMiS, credentials);
	}



	@After
	public void cleanUp() {
		shoppingcartService.delete(shoppingcartBo1.getArticleId(), shoppingcartBo1.getUserId());
	}



	@Test
	@Ignore
	public void testGetAllById() throws ShoppingcartException {
		final ShoppingcartBo shoppingcartBo2 = createAndSaveShoppingcartBo(new Long(123), new Long(321), 50);

		final Response result = client.target(HOST).path("shoppingcart/"+shoppingcartBo2.getUserId()).request(MediaType.APPLICATION_JSON)
											.header("AUTHORIZATION", vaildAuthToken).get(Response.class);
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.OK.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));

		final ShoppingcartDtoList resultShoppingcarts = result.readEntity(ShoppingcartDtoList.class);
		assertThat(resultShoppingcarts, is(notNullValue()));
		assertThat(resultShoppingcarts.getshoppingcartList(), is(notNullValue()));
		assertThat(resultShoppingcarts.getshoppingcartList(), hasSize(2));
		final Iterator<ShoppingcartDto> shoppingcartsIterator = resultShoppingcarts.getshoppingcartList().iterator();
		final ShoppingcartDto shoppingcartDto1 = shoppingcartsIterator.next();
		final ShoppingcartDto shoppingcartDto2 = shoppingcartsIterator.next();

		if (Objects.equals(shoppingcartDto1.getId(), shoppingcartBo1.getId())) {
			assertThat(shoppingcartDto2.getId(), is(equalTo(shoppingcartBo2.getId())));
			assertSelfLink(shoppingcartDto2.getLinks(), shoppingcartDto2.getId());
			assertSelfLink(shoppingcartDto1.getLinks(), shoppingcartDto1.getId());
		} else {
			assertThat(shoppingcartDto1.getId(), is(equalTo(shoppingcartBo2.getId())));
			assertThat(shoppingcartDto2.getId(), is(equalTo(shoppingcartBo1.getId())));
			assertSelfLink(shoppingcartDto1.getLinks(), shoppingcartDto2.getId());
			assertSelfLink(shoppingcartDto2.getLinks(), shoppingcartDto1.getId());
		}

		assertThat(resultShoppingcarts.getLinks(), is(notNullValue()));
		assertThat(resultShoppingcarts.getLinks(), hasSize(1));
		final Link resultShoppingcartsSelfLink = resultShoppingcarts.getLinks().iterator().next();
		assertThat(resultShoppingcartsSelfLink.getRel(), is(equalTo(Link.REL_SELF)));
		assertThat(resultShoppingcartsSelfLink.getHref(), is(equalTo(HOST + "/shoppingcart")));
	}



	@Test
	public void testCreateShoppingcart() {
		final ShoppingcartDto newShoppingcart = new ShoppingcartDto();
		newShoppingcart.setArticleId(new Long(123));
		newShoppingcart.setUserId(this.userId);
		newShoppingcart.setQuantity(2);

		final Response result = client.target(HOST).path("shoppingcart").request(MediaType.APPLICATION_XML)
				.header("AUTHORIZATION", vaildAuthToken).post(Entity.xml(newShoppingcart));
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.CREATED.getStatusCode())));
		assertThat(result.getLocation().toString(), matchesRegex(HOST + "/shoppingcart/[0-9]*"));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));

		final ShoppingcartDto resultShoppingcart = result.readEntity(ShoppingcartDto.class);
		assertThat(resultShoppingcart, is(notNullValue()));
		assertThat(resultShoppingcart.getId(), is(notNullValue()));
		assertSelfLink(resultShoppingcart.getLinks(), result.getLocation().toString());

		shoppingcartService.delete(resultShoppingcart.getArticleId(), resultShoppingcart.getUserId());
	}



	@Test
	public void testUpdateShoppingcart() {
		final ShoppingcartDto shoppingcartDto = new ShoppingcartDto();
		shoppingcartDto.setId(shoppingcartBo1.getId());
		shoppingcartDto.setArticleId(new Long(123));
		shoppingcartDto.setUserId(this.userId);
		shoppingcartDto.setQuantity(2);

		final Response result = client.target(HOST).path("shoppingcart").path(shoppingcartDto.getArticleId().toString()).request(MediaType.APPLICATION_XML)
				.header("AUTHORIZATION", vaildAuthToken).put(Entity.xml(shoppingcartDto));
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.OK.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));

		final ShoppingcartDto resultShoppingcart = result.readEntity(ShoppingcartDto.class);
		assertThat(resultShoppingcart, is(notNullValue()));
		assertThat(resultShoppingcart.getUserId(), is(equalTo(shoppingcartBo1.getUserId())));
	}



	@Test
	public void testDeleteById() {
		final Response result = client.target(HOST).path("shoppingcart/" + shoppingcartBo1.getArticleId())
				.request(MediaType.APPLICATION_XML).header("AUTHORIZATION", vaildAuthToken).delete();
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.NO_CONTENT.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));
	}



	private void assertSelfLink(final Collection<Link> links, final Long expectedId) {
		assertSelfLink(links, HOST + "/shoppingcart/" + expectedId);
	}



	private void assertSelfLink(final Collection<Link> links, final String expectedUri) {
		assertThat(links, is(notNullValue()));
		assertThat(links, hasSize(1));
		final Link selfLink = links.iterator().next();
		assertThat(selfLink.getRel(), is(equalTo(Link.REL_SELF)));
		assertThat(selfLink.getHref(), is(equalTo(expectedUri)));
	}



	private ShoppingcartBo createAndSaveShoppingcartBo(final Long articleId, final Long userId, final Integer quantity) throws ShoppingcartException {
		ShoppingcartBo shoppingcartBo = new ShoppingcartBo();
		shoppingcartBo.setArticleId(articleId);
		shoppingcartBo.setUserId(userId);
		shoppingcartBo.setQuantity(quantity);
		return (shoppingcartBo = shoppingcartService.save(shoppingcartBo));
	}

}
