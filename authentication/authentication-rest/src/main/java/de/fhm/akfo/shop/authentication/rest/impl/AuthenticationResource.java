package de.fhm.akfo.shop.authentication.rest.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.internal.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

import de.fhm.akfo.shop.authentication.rest.to.AuthenticateTo;
import de.fhm.akfo.shop.authentication.rest.to.UserTo;
import de.fhm.akfo.shop.authentication.service.api.AuthenticationService;

@Component
@Path("/authentication")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationResource {
	
	/** Logger dieser Klasse. */
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationResource.class);

	private static final String AUTHENTICATION_SCHEME = "Basic";
	
	@Inject
	private AuthenticationService authenticationService;

	
	@Context 
	HttpHeaders headers;

    @GET
    @PermitAll
    public Response getAuthentificationToken() {
    	String authHeader = headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0);
   		LOG.info("Für die Nutzerdaten {} wird ein Token ausgegeben.", authHeader);
       	
   		UserTo dto = decodeCredentials(authHeader);
   		
   		LOG.info("AuthenticationDto: " + dto.toString());
   		
    	String token = authenticationService.getAuthenticationToken(dto.getUsername(), dto.getPassword());
    	AuthenticateTo to;
    	
    	if(token != null){
        	to = new AuthenticateTo(token);    		
    	}else{
    		return null;
    	}
    	    	
    	List<AuthenticateTo> servicetemplateTOList = new ArrayList<AuthenticateTo>();
    	servicetemplateTOList.add(to);
    	
	    Resources<AuthenticateTo> wrapped = new Resources<AuthenticateTo>(servicetemplateTOList);
	    wrapped.add(
	            JaxRsLinkBuilder.linkTo(AuthenticationResource.class).withSelfRel()
	    );
	    
    	LOG.info("Token: " + token);
    	
        return Response.ok(to).build();
    }


	public UserTo decodeCredentials(String credentials) {
		final String encodedUserPassword = credentials.replaceFirst(AUTHENTICATION_SCHEME + " ", "");
        final String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));;
        
        StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        String username = tokenizer.nextToken();
        String password = tokenizer.nextToken();
        
        return new UserTo(username, password);
	}

}
