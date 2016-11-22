package de.hm.shop.template.integrationtest;

import static de.hm.shop.template.integrationtest.matcher.RegexMatcher.matchesRegex;
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

import de.hm.shop.template.Application;
import de.hm.shop.template.rest.dto.TemplateDtoList;
import de.hm.shop.template.rest.dto.TemplateDto;
import de.hm.shop.template.service.api.TemplateService;
import de.hm.shop.template.service.api.bo.TemplateBo;
import de.hm.shop.template.service.api.exception.TemplateException;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest("server.port=9000")
public class TemplateResourceTest {

	private static final String HOST = "http://localhost:9000";
	private static final Logger LOG = Logger.getLogger(TemplateResourceTest.class.getName());
	
//	private final HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("techuser", "techuser");
	private final Client client = ClientBuilder.newClient().register(new LoggingFilter(LOG, true));

	@Inject
	private TemplateService templateService;

	private TemplateBo templateBo1;
	private String vaildAuthToken;


	@Before
	public void setUp() throws TemplateException {
		templateBo1 = createAndSaveTemplateBo("Max");
		
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
		templateService.delete(templateBo1.getId());
	}



	@Test
	public void testGetAll() throws TemplateException {
		final TemplateBo templateBo2 = createAndSaveTemplateBo("Max");

		final Response result = client.target(HOST).path("templates").request(MediaType.APPLICATION_JSON)
											.header("AUTHORIZATION", vaildAuthToken).get(Response.class);
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.OK.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));

		final TemplateDtoList resultTemplates = result.readEntity(TemplateDtoList.class);
		assertThat(resultTemplates, is(notNullValue()));
		assertThat(resultTemplates.gettemplateList(), is(notNullValue()));
		assertThat(resultTemplates.gettemplateList(), hasSize(2));
		final Iterator<TemplateDto> templatesIterator = resultTemplates.gettemplateList().iterator();
		final TemplateDto templateDto1 = templatesIterator.next();
		final TemplateDto templateDto2 = templatesIterator.next();

		if (Objects.equals(templateDto1.getId(), templateBo1.getId())) {
			assertThat(templateDto2.getId(), is(equalTo(templateBo2.getId())));
			assertSelfLink(templateDto2.getLinks(), templateDto2.getId());
			assertSelfLink(templateDto1.getLinks(), templateDto1.getId());
		} else {
			assertThat(templateDto1.getId(), is(equalTo(templateBo2.getId())));
			assertThat(templateDto2.getId(), is(equalTo(templateBo1.getId())));
			assertSelfLink(templateDto1.getLinks(), templateDto2.getId());
			assertSelfLink(templateDto2.getLinks(), templateDto1.getId());
		}

		assertThat(resultTemplates.getLinks(), is(notNullValue()));
		assertThat(resultTemplates.getLinks(), hasSize(1));
		final Link resultTemplatesSelfLink = resultTemplates.getLinks().iterator().next();
		assertThat(resultTemplatesSelfLink.getRel(), is(equalTo(Link.REL_SELF)));
		assertThat(resultTemplatesSelfLink.getHref(), is(equalTo(HOST + "/templates")));
	}



	@Test
	public void testGetById() {
		final Response result = client.target(HOST).path("templates/" + templateBo1.getId())
				.request(MediaType.APPLICATION_XML).header("AUTHORIZATION", vaildAuthToken).get();
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.OK.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));

		final TemplateDto resultTemplate = result.readEntity(TemplateDto.class);
		assertThat(resultTemplate, is(notNullValue()));
		assertThat(resultTemplate.getId(), is(equalTo(templateBo1.getId())));
		assertSelfLink(resultTemplate.getLinks(), templateBo1.getId());
	}



	@Test
	public void testGetById_NotFound() {
		final Response result = client.target(HOST).path("templates/" + System.currentTimeMillis())
				.request(MediaType.APPLICATION_XML).header("AUTHORIZATION", vaildAuthToken).get();
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.NOT_FOUND.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));
		assertThat(result.readEntity(String.class), is(equalTo(Status.NOT_FOUND.getReasonPhrase())));
	}



	@Test
	public void testCreateTemplate() {
		final TemplateDto newTemplate = new TemplateDto();
		newTemplate.setName("bla");

		final Response result = client.target(HOST).path("templates").request(MediaType.APPLICATION_XML)
				.header("AUTHORIZATION", vaildAuthToken).post(Entity.xml(newTemplate));
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.CREATED.getStatusCode())));
		assertThat(result.getLocation().toString(), matchesRegex(HOST + "/templates/[0-9]*"));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));

		final TemplateDto resultTemplate = result.readEntity(TemplateDto.class);
		assertThat(resultTemplate, is(notNullValue()));
		assertThat(resultTemplate.getId(), is(notNullValue()));
		assertSelfLink(resultTemplate.getLinks(), result.getLocation().toString());

		templateService.delete(resultTemplate.getId());
	}



	@Test
	public void testUpdatePerson() {
		final TemplateDto templateDto = new TemplateDto();
		templateDto.setId(templateBo1.getId());
		templateDto.setName("x");

		final Response result = client.target(HOST).path("templates").path(templateBo1.getId().toString()).request(MediaType.APPLICATION_XML)
				.header("AUTHORIZATION", vaildAuthToken).put(Entity.xml(templateDto));
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.OK.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));

		final TemplateDto resultTemplate = result.readEntity(TemplateDto.class);
		assertThat(resultTemplate, is(notNullValue()));
		assertThat(resultTemplate.getId(), is(equalTo(templateBo1.getId())));
		assertSelfLink(resultTemplate.getLinks(), templateBo1.getId());
	}



	@Test
	public void testDeleteById() {
		final Response result = client.target(HOST).path("templates/" + templateBo1.getId())
				.request(MediaType.APPLICATION_XML).header("AUTHORIZATION", vaildAuthToken).delete();
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.NO_CONTENT.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));
	}



	private void assertSelfLink(final Collection<Link> links, final Long expectedId) {
		assertSelfLink(links, HOST + "/templates/" + expectedId);
	}



	private void assertSelfLink(final Collection<Link> links, final String expectedUri) {
		assertThat(links, is(notNullValue()));
		assertThat(links, hasSize(1));
		final Link selfLink = links.iterator().next();
		assertThat(selfLink.getRel(), is(equalTo(Link.REL_SELF)));
		assertThat(selfLink.getHref(), is(equalTo(expectedUri)));
	}



	private TemplateBo createAndSaveTemplateBo(final String name) throws TemplateException {
		TemplateBo templateBo = new TemplateBo();
		templateBo.setName(name);
		return (templateBo = templateService.save(templateBo));
	}

}
