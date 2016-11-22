package de.hm.shop.article.integrationtest;

import static de.hm.shop.article.integrationtest.matcher.RegexMatcher.matchesRegex;
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

import de.hm.shop.article.Application;
import de.hm.shop.article.rest.dto.ArticleDtoList;
import de.hm.shop.article.rest.dto.ArticleDto;
import de.hm.shop.article.service.api.ArticleService;
import de.hm.shop.article.service.api.bo.ArticleBo;
import de.hm.shop.article.service.api.exception.ArticleException;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest("server.port=9000")
public class ArticleResourceTest {

	private static final String HOST = "http://localhost:9000";
	private static final Logger LOG = Logger.getLogger(ArticleResourceTest.class.getName());
	
//	private final HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("techuser", "techuser");
	private final Client client = ClientBuilder.newClient().register(new LoggingFilter(LOG, true));

	@Inject
	private ArticleService articleService;

	private ArticleBo articleBo1;
	private String vaildAuthToken;


	@Before
	public void setUp() throws ArticleException {
		articleBo1 = createAndSaveArticleBo();
		
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
		articleService.delete(articleBo1.getId());
	}



	@Test
	public void testGetAll() throws ArticleException {
		final ArticleBo articleBo2 = createAndSaveArticleBo();

		final Response result = client.target(HOST).path("articles").request(MediaType.APPLICATION_JSON)
											.header("AUTHORIZATION", vaildAuthToken).get(Response.class);
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.OK.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));

		final ArticleDtoList resultArticles = result.readEntity(ArticleDtoList.class);
		assertThat(resultArticles, is(notNullValue()));
		assertThat(resultArticles.getarticleList(), is(notNullValue()));
		assertThat(resultArticles.getarticleList(), hasSize(2));
		final Iterator<ArticleDto> articlesIterator = resultArticles.getarticleList().iterator();
		final ArticleDto articleDto1 = articlesIterator.next();
		final ArticleDto articleDto2 = articlesIterator.next();

		if (Objects.equals(articleDto1.getId(), articleBo1.getId())) {
			assertThat(articleDto2.getId(), is(equalTo(articleBo2.getId())));
			assertSelfLink(articleDto2.getLinks(), articleDto2.getId());
			assertSelfLink(articleDto1.getLinks(), articleDto1.getId());
		} else {
			assertThat(articleDto1.getId(), is(equalTo(articleBo2.getId())));
			assertThat(articleDto2.getId(), is(equalTo(articleBo1.getId())));
			assertSelfLink(articleDto1.getLinks(), articleDto2.getId());
			assertSelfLink(articleDto2.getLinks(), articleDto1.getId());
		}

		assertThat(resultArticles.getLinks(), is(notNullValue()));
		assertThat(resultArticles.getLinks(), hasSize(1));
		final Link resultArticlesSelfLink = resultArticles.getLinks().iterator().next();
		assertThat(resultArticlesSelfLink.getRel(), is(equalTo(Link.REL_SELF)));
		assertThat(resultArticlesSelfLink.getHref(), is(equalTo(HOST + "/articles")));
	}



	@Test
	public void testGetById() {
		final Response result = client.target(HOST).path("articles/" + articleBo1.getId())
				.request(MediaType.APPLICATION_XML).header("AUTHORIZATION", vaildAuthToken).get();
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.OK.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));

		final ArticleDto resultArticle = result.readEntity(ArticleDto.class);
		assertThat(resultArticle, is(notNullValue()));
		assertThat(resultArticle.getId(), is(equalTo(articleBo1.getId())));
		assertSelfLink(resultArticle.getLinks(), articleBo1.getId());
	}



	@Test
	public void testGetById_NotFound() {
		final Response result = client.target(HOST).path("articles/" + System.currentTimeMillis())
				.request(MediaType.APPLICATION_XML).header("AUTHORIZATION", vaildAuthToken).get();
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.NOT_FOUND.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));
		assertThat(result.readEntity(String.class), is(equalTo(Status.NOT_FOUND.getReasonPhrase())));
	}



	@Test
	public void testCreateArticle() {
		final ArticleDto newArticle = new ArticleDto();
		newArticle.setArticleTitle("title");
		newArticle.setArticleStock(15);
		newArticle.setArticlePrice(432.3);
		newArticle.setArticleEAN("123412341234");
		newArticle.setArticleDescription("Beschriebung");
		
		final Response result = client.target(HOST).path("articles").request(MediaType.APPLICATION_XML)
				.header("AUTHORIZATION", vaildAuthToken).post(Entity.xml(newArticle));
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.CREATED.getStatusCode())));
		assertThat(result.getLocation().toString(), matchesRegex(HOST + "/articles/[0-9]*"));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));

		final ArticleDto resultArticle = result.readEntity(ArticleDto.class);
		assertThat(resultArticle, is(notNullValue()));
		assertThat(resultArticle.getId(), is(notNullValue()));
		assertThat(resultArticle.getArticleTitle(), is(notNullValue()));
		assertSelfLink(resultArticle.getLinks(), result.getLocation().toString());

		articleService.delete(resultArticle.getId());
	}



	@Test
	public void testUpdatePerson() {
		final ArticleDto articleDto = new ArticleDto();
		articleDto.setId(articleBo1.getId());
		articleDto.setArticleTitle("title");
		articleDto.setArticleStock(15);
		articleDto.setArticlePrice(432.3);
		articleDto.setArticleEAN("123412341234");
		articleDto.setArticleDescription("Beschriebung");

		final Response result = client.target(HOST).path("articles").path(articleBo1.getId().toString()).request(MediaType.APPLICATION_XML)
				.header("AUTHORIZATION", vaildAuthToken).put(Entity.xml(articleDto));
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.OK.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));

		final ArticleDto resultArticle = result.readEntity(ArticleDto.class);
		assertThat(resultArticle, is(notNullValue()));
		assertThat(resultArticle.getId(), is(equalTo(articleBo1.getId())));
		assertSelfLink(resultArticle.getLinks(), articleBo1.getId());
	}



	@Test
	public void testDeleteById() {
		final Response result = client.target(HOST).path("articles/" + articleBo1.getId())
				.request(MediaType.APPLICATION_XML).header("AUTHORIZATION", vaildAuthToken).delete();
		assertThat(result, is(notNullValue()));
		assertThat(result.getStatus(), is(equalTo(Status.NO_CONTENT.getStatusCode())));
		assertThat(result.getLocation(), is(nullValue()));
		assertThat(result.getLinks(), is(notNullValue()));
		assertThat(result.getLinks(), is(empty()));
	}



	private void assertSelfLink(final Collection<Link> links, final Long expectedId) {
		assertSelfLink(links, HOST + "/articles/" + expectedId);
	}



	private void assertSelfLink(final Collection<Link> links, final String expectedUri) {
		assertThat(links, is(notNullValue()));
		assertThat(links, hasSize(1));
		final Link selfLink = links.iterator().next();
		assertThat(selfLink.getRel(), is(equalTo(Link.REL_SELF)));
		assertThat(selfLink.getHref(), is(equalTo(expectedUri)));
	}



	private ArticleBo createAndSaveArticleBo() throws ArticleException {
		ArticleBo articleBo = new ArticleBo();
		articleBo.setArticleTitle("title");
		articleBo.setArticleStock(15);
		articleBo.setArticlePrice(432.3);
		articleBo.setArticleEAN("123412341234");
		articleBo.setArticleDescription("Beschriebung");
		return (articleBo = articleService.save(articleBo));
	}

}
