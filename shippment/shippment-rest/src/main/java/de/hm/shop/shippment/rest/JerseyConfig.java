package de.hm.shop.shippment.rest;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import de.hm.shop.shippment.rest.exception.RuntimeExceptionHandler;
import de.hm.shop.shippment.rest.filter.ShippmentLoginFilter;
import de.hm.shop.shippment.rest.resource.ShippmentResource;

/**
 * StandardConfig für den Restservice
 * @author Maximilian.Auch
 */
@Path("/")
@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(ShippmentLoginFilter.class);
		registerResources();
		registerExceptionHandlers();
	}
	

	private void registerResources() {
		register(ShippmentResource.class);
	}

	
	private void registerExceptionHandlers() {
		register(RuntimeExceptionHandler.class);
	}
	
	@GET
    @PermitAll
    @Path("/ping")
    public Response ping() {
    	return Response.ok("pong").build();
    }

}
