package de.hm.shop.user.rest;

import javax.ws.rs.Path;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import de.hm.shop.user.rest.exception.RuntimeExceptionHandler;
import de.hm.shop.user.rest.filter.UserLoginFilter;
import de.hm.shop.user.rest.resource.PingResource;
import de.hm.shop.user.rest.resource.UserResource;

/**
 * StandardConfig für den Restservice
 * @author Maximilian.Auch
 */
@Path("/")
@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(UserLoginFilter.class);
		registerResources();
		registerExceptionHandlers();
	}
	

	private void registerResources() {
		register(PingResource.class);
		register(UserResource.class);
	}

	
	private void registerExceptionHandlers() {
		register(RuntimeExceptionHandler.class);
	}

}
