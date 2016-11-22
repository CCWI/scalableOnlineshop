package de.hm.shop.user.rest.resource;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;


@Component
@CrossOrigin
@Path("ping")
public class PingResource {
	
	@GET
    @PermitAll
    public Response ping() {
    	return Response.ok("pong").build();
    }
}
