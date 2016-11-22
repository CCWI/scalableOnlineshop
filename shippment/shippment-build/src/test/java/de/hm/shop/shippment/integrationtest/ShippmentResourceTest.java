package de.hm.shop.shippment.integrationtest;

import static de.hm.shop.shippment.integrationtest.matcher.RegexMatcher.matchesRegex;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.hateoas.Link;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.hm.shop.shippment.Application;
import de.hm.shop.shippment.rest.dto.ShippmentDtoList;
import de.hm.shop.shippment.rest.dto.ShippmentDto;
import de.hm.shop.shippment.service.api.ShippmentService;
import de.hm.shop.shippment.service.api.bo.ShippmentBo;
import de.hm.shop.shippment.service.api.exception.ShippmentException;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest("server.port=9000")
public class ShippmentResourceTest {

	private static final String HOST = "http://localhost:9000";
	private static final Logger LOG = Logger.getLogger(ShippmentResourceTest.class.getName());
	
//	private final HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("techuser", "techuser");
	private final Client client = ClientBuilder.newClient().register(new LoggingFilter(LOG, true));

	@Inject
	private ShippmentService shippmentService;

	private ShippmentBo shippmentBo1;
	private String vaildAuthToken;


	@Before
	public void setUp() throws ShippmentException {
		shippmentBo1 = createAndSaveShippmentBo("Max");
		
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
    	vaildAuthToken = new JWTTokenGenerator().createJWT("1", issuer, subject, ttlMiS, credentials);
	}



	@After
	public void cleanUp() {
		shippmentService.delete(shippmentBo1.getId());
	}



	@Test
	public void testGetAll() throws ShippmentException {
		final ShippmentBo shippmentBo2 = createAndSaveShippmentBo("Max");

		final Response result = client.target(HOST).path("shippments").request(MediaType.APPLICATION_JSON)
											.header("AUTHORIZATION", vaildAuthToken).get(Response.class);
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.OK.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));

		final ShippmentDtoList resultShippments = result.readEntity(ShippmentDtoList.class);
		assertThat(resultShippments, is(notNullValue()));
		assertThat(resultShippments.getshippmentList(), is(notNullValue()));
		assertThat(resultShippments.getshippmentList(), hasSize(2));
		final Iterator<ShippmentDto> shippmentsIterator = resultShippments.getshippmentList().iterator();
		final ShippmentDto shippmentDto1 = shippmentsIterator.next();
		final ShippmentDto shippmentDto2 = shippmentsIterator.next();

		if (Objects.equals(shippmentDto1.getId(), shippmentBo1.getId())) {
			assertThat(shippmentDto2.getId(), is(equalTo(shippmentBo2.getId())));
			assertSelfLink(shippmentDto2.getLinks(), shippmentDto2.getId());
			assertSelfLink(shippmentDto1.getLinks(), shippmentDto1.getId());
		} else {
			assertThat(shippmentDto1.getId(), is(equalTo(shippmentBo2.getId())));
			assertThat(shippmentDto2.getId(), is(equalTo(shippmentBo1.getId())));
			assertSelfLink(shippmentDto1.getLinks(), shippmentDto2.getId());
			assertSelfLink(shippmentDto2.getLinks(), shippmentDto1.getId());
		}

		assertThat(resultShippments.getLinks(), is(notNullValue()));
		assertThat(resultShippments.getLinks(), hasSize(1));
		final Link resultShippmentsSelfLink = resultShippments.getLinks().iterator().next();
		assertThat(resultShippmentsSelfLink.getRel(), is(equalTo(Link.REL_SELF)));
		assertThat(resultShippmentsSelfLink.getHref(), is(equalTo(HOST + "/shippments")));
	}



	@Test
	public void testGetById() {
		final Response result = client.target(HOST).path("shippments/" + shippmentBo1.getId())
				.request(MediaType.APPLICATION_XML).header("AUTHORIZATION", vaildAuthToken).get();
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.OK.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));

		final ShippmentDto resultShippment = result.readEntity(ShippmentDto.class);
		assertThat(resultShippment, is(notNullValue()));
		assertThat(resultShippment.getId(), is(equalTo(shippmentBo1.getId())));
		assertSelfLink(resultShippment.getLinks(), shippmentBo1.getId());
	}



	@Test
	public void testGetById_NotFound() {
		final Response result = client.target(HOST).path("shippments/" + System.currentTimeMillis())
				.request(MediaType.APPLICATION_XML).header("AUTHORIZATION", vaildAuthToken).get();
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.NOT_FOUND.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));
		assertThat(result.readEntity(String.class), is(equalTo(Status.NOT_FOUND.getReasonPhrase())));
	}



	@Test
	public void testCreateShippment() {
		final ShippmentDto newShippment = new ShippmentDto();
		newShippment.setShipmentReady(false);
		newShippment.setShippingDays(2);
		newShippment.setShippingMethod("DHL");

		final Response result = client.target(HOST).path("shippments").request(MediaType.APPLICATION_XML)
				.header("AUTHORIZATION", vaildAuthToken).post(Entity.xml(newShippment));
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.CREATED.getStatusCode())));
		assertThat(result.getLocation().toString(), matchesRegex(HOST + "/shippments/[0-9]*"));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));

		final ShippmentDto resultShippment = result.readEntity(ShippmentDto.class);
		assertThat(resultShippment, is(notNullValue()));
		assertThat(resultShippment.getId(), is(notNullValue()));
		assertSelfLink(resultShippment.getLinks(), result.getLocation().toString());

		shippmentService.delete(resultShippment.getId());
	}



	@Test
	public void testUpdatePerson() {
		final ShippmentDto shippmentDto = new ShippmentDto();
		shippmentDto.setId(shippmentBo1.getId());
		shippmentDto.setShipmentReady(false);
		shippmentDto.setShippingDays(2);
		shippmentDto.setShippingMethod("DHL");

		final Response result = client.target(HOST).path("shippments").path(shippmentBo1.getId().toString()).request(MediaType.APPLICATION_XML)
				.header("AUTHORIZATION", vaildAuthToken).put(Entity.xml(shippmentDto));
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.OK.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));

		final ShippmentDto resultShippment = result.readEntity(ShippmentDto.class);
		assertThat(resultShippment, is(notNullValue()));
		assertThat(resultShippment.getId(), is(equalTo(shippmentBo1.getId())));
		assertSelfLink(resultShippment.getLinks(), shippmentBo1.getId());
	}



	@Test
	public void testDeleteById() {
		final Response result = client.target(HOST).path("shippments/" + shippmentBo1.getId())
				.request(MediaType.APPLICATION_XML).header("AUTHORIZATION", vaildAuthToken).delete();
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.NO_CONTENT.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));
	}



	private void assertSelfLink(final Collection<Link> links, final Long expectedId) {
		assertSelfLink(links, HOST + "/shippments/" + expectedId);
	}



	private void assertSelfLink(final Collection<Link> links, final String expectedUri) {
		assertThat(links, is(notNullValue()));
		assertThat(links, hasSize(1));
		final Link selfLink = links.iterator().next();
		assertThat(selfLink.getRel(), is(equalTo(Link.REL_SELF)));
		assertThat(selfLink.getHref(), is(equalTo(expectedUri)));
	}



	private ShippmentBo createAndSaveShippmentBo(final String name) throws ShippmentException {
		ShippmentBo shippmentBo = new ShippmentBo();
		shippmentBo.setShipmentReady(false);
		shippmentBo.setShippingDays(2);
		shippmentBo.setShippingMethod("DHL");
		return (shippmentBo = shippmentService.save(shippmentBo));
	}

}
