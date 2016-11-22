package de.hm.shop.article.rest;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.hm.shop.article.rest.exception.RuntimeExceptionHandler;
import de.hm.shop.article.rest.filter.ArticleLoginFilter;
import de.hm.shop.article.rest.resource.ArticleResource;
import de.hm.shop.article.rest.resource.PingResource;

/**
 * StandardConfig für den Restservice
 * @author Maximilian.Auch
 */
@Path("/")
@Component
public class JerseyConfig extends ResourceConfig {
	
	/** Logger dieser Klasse. */
	private static final Logger LOG = LoggerFactory.getLogger(JerseyConfig.class);

	public JerseyConfig() {
		register(ArticleLoginFilter.class);
		registerResources();
		registerExceptionHandlers();
	}
	

	private void registerResources() {
		register(PingResource.class);
		register(ArticleResource.class);
	}

	
	private void registerExceptionHandlers() {
		register(RuntimeExceptionHandler.class);
	}

}
