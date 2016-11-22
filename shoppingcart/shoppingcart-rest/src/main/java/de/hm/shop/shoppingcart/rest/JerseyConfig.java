package de.hm.shop.shoppingcart.rest;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import de.hm.shop.shoppingcart.rest.exception.RuntimeExceptionHandler;
import de.hm.shop.shoppingcart.rest.filter.ShoppingcartLoginFilter;
import de.hm.shop.shoppingcart.rest.resource.ShoppingcartResource;

@Path("/")
@Component
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(ShoppingcartLoginFilter.class);
		registerResources();
		registerExceptionHandlers();
	}
	

	private void registerResources() {
		register(ShoppingcartResource.class);
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
