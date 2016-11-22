package de.hm.shop.article.rest.resource;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;


@Component
@Path("ping")
@CrossOrigin
public class PingResource {

	/** Logger dieser Klasse. */
	private static final Logger LOG = LoggerFactory.getLogger(PingResource.class);
	

	@GET
	@PermitAll
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getAllArticle(@QueryParam("host") final String ipHost) {
		LOG.info("Aufruf der getAllArticle()-Methode mit der IP: {}", ipHost);
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(ipHost).path("ping");
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();
		
		if(response.getStatus() == 200){
			return Response.ok().build();
		}
		return Response.status(Status.FOUND).entity(response.getStatus() + ", " + response.getEntity()).build();
	}

}
