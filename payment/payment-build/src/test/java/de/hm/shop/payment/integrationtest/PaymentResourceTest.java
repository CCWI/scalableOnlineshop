package de.hm.shop.payment.integrationtest;

import static de.hm.shop.payment.integrationtest.matcher.RegexMatcher.matchesRegex;
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

import de.hm.shop.payment.Application;
import de.hm.shop.payment.rest.dto.PaymentDtoList;
import de.hm.shop.payment.rest.dto.PaymentDto;
import de.hm.shop.payment.service.api.PaymentService;
import de.hm.shop.payment.service.api.bo.PaymentBo;
import de.hm.shop.payment.service.api.exception.PaymentException;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest("server.port=9000")
public class PaymentResourceTest {

	private static final String HOST = "http://localhost:9000";
	private static final Logger LOG = Logger.getLogger(PaymentResourceTest.class.getName());
	
	private final Client client = ClientBuilder.newClient().register(new LoggingFilter(LOG, true));

	@Inject
	private PaymentService paymentService;

	private PaymentBo paymentBo1;
	private String vaildAuthToken;


	@Before
	public void setUp() throws PaymentException {
		paymentBo1 = createAndSavePaymentBo("Max");
		
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
		paymentService.delete(paymentBo1.getId());
	}



	@Test
	@Ignore
	public void testGetAll() throws PaymentException {
		final PaymentBo paymentBo2 = createAndSavePaymentBo("Max");

		final Response result = client.target(HOST).path("payments").request(MediaType.APPLICATION_JSON)
											.header("AUTHORIZATION", vaildAuthToken).get(Response.class);
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.OK.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));

		final PaymentDtoList resultPayments = result.readEntity(PaymentDtoList.class);
		assertThat(resultPayments, is(notNullValue()));
		assertThat(resultPayments.getpaymentList(), is(notNullValue()));
		assertThat(resultPayments.getpaymentList(), hasSize(2));
		final Iterator<PaymentDto> paymentsIterator = resultPayments.getpaymentList().iterator();
		final PaymentDto paymentDto1 = paymentsIterator.next();
		final PaymentDto paymentDto2 = paymentsIterator.next();

		if (Objects.equals(paymentDto1.getId(), paymentBo1.getId())) {
			assertThat(paymentDto2.getId(), is(equalTo(paymentBo2.getId())));
			assertSelfLink(paymentDto2.getLinks(), paymentDto2.getId());
			assertSelfLink(paymentDto1.getLinks(), paymentDto1.getId());
		} else {
			assertThat(paymentDto1.getId(), is(equalTo(paymentBo2.getId())));
			assertThat(paymentDto2.getId(), is(equalTo(paymentBo1.getId())));
			assertSelfLink(paymentDto1.getLinks(), paymentDto2.getId());
			assertSelfLink(paymentDto2.getLinks(), paymentDto1.getId());
		}

		assertThat(resultPayments.getLinks(), is(notNullValue()));
		assertThat(resultPayments.getLinks(), hasSize(1));
		final Link resultPaymentsSelfLink = resultPayments.getLinks().iterator().next();
		assertThat(resultPaymentsSelfLink.getRel(), is(equalTo(Link.REL_SELF)));
		assertThat(resultPaymentsSelfLink.getHref(), is(equalTo(HOST + "/payments")));
	}



	@Test
	public void testGetById() {
		final Response result = client.target(HOST).path("payments/" + paymentBo1.getSupplierId())
				.request(MediaType.APPLICATION_XML).header("AUTHORIZATION", vaildAuthToken).get();
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.OK.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));

//		final PaymentDto resultPayment = result.readEntity(PaymentDto.class);
//		assertThat(resultPayment, is(notNullValue()));
//		assertThat(resultPayment.getSupplierId(), is(equalTo(paymentBo1.getSupplierId())));
//		assertSelfLink(resultPayment.getLinks(), paymentBo1.getId());
	}



	@Test
	public void testGetById_NotFound() {
		final Response result = client.target(HOST).path("payments/" + System.currentTimeMillis())
				.request(MediaType.APPLICATION_XML).header("AUTHORIZATION", vaildAuthToken).get();
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.NOT_FOUND.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));
		assertThat(result.readEntity(String.class), is(equalTo(Status.NOT_FOUND.getReasonPhrase())));
	}



	@Test
	public void testCreatePayment() {
		final PaymentDto newPayment = new PaymentDto();
		newPayment.setMethod("Überweisung");
		newPayment.setSupplierId(2L);

		final Response result = client.target(HOST).path("payments").request(MediaType.APPLICATION_XML)
				.header("AUTHORIZATION", vaildAuthToken).post(Entity.xml(newPayment));
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.CREATED.getStatusCode())));
		assertThat(result.getLocation().toString(), matchesRegex(HOST + "/payments/[0-9]*"));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));

		final PaymentDto resultPayment = result.readEntity(PaymentDto.class);
		assertThat(resultPayment, is(notNullValue()));
		assertThat(resultPayment.getId(), is(notNullValue()));
		assertSelfLink(resultPayment.getLinks(), result.getLocation().toString());

		paymentService.delete(resultPayment.getId());
	}



	@Test
	public void testUpdatePerson() {
		final PaymentDto paymentDto = new PaymentDto();
		paymentDto.setId(paymentBo1.getId());
		paymentDto.setMethod("Überweisung");
		paymentDto.setSupplierId(2L);

		final Response result = client.target(HOST).path("payments").path(paymentBo1.getId().toString()).request(MediaType.APPLICATION_XML)
				.header("AUTHORIZATION", vaildAuthToken).put(Entity.xml(paymentDto));
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.OK.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));

		final PaymentDto resultPayment = result.readEntity(PaymentDto.class);
		assertThat(resultPayment, is(notNullValue()));
		assertThat(resultPayment.getId(), is(equalTo(paymentBo1.getId())));
		assertSelfLink(resultPayment.getLinks(), paymentBo1.getId());
	}



	@Test
	public void testDeleteById() {
		final Response result = client.target(HOST).path("payments/" + paymentBo1.getId())
				.request(MediaType.APPLICATION_XML).header("AUTHORIZATION", vaildAuthToken).delete();
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.NO_CONTENT.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));
	}



	private void assertSelfLink(final Collection<Link> links, final Long expectedId) {
		assertSelfLink(links, HOST + "/payments/" + expectedId);
	}



	private void assertSelfLink(final Collection<Link> links, final String expectedUri) {
		assertThat(links, is(notNullValue()));
		assertThat(links, hasSize(1));
		final Link selfLink = links.iterator().next();
		assertThat(selfLink.getRel(), is(equalTo(Link.REL_SELF)));
		assertThat(selfLink.getHref(), is(equalTo(expectedUri)));
	}



	private PaymentBo createAndSavePaymentBo(final String name) throws PaymentException {
		PaymentBo paymentBo = new PaymentBo();
		paymentBo.setMethod("Überweisung");
		paymentBo.setSupplierId(100L);
		return (paymentBo = paymentService.save(paymentBo));
	}

}
