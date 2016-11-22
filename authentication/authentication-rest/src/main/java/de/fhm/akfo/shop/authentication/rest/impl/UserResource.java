package de.fhm.akfo.shop.authentication.rest.impl;

import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.internal.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.fhm.akfo.shop.authentication.rest.to.AuthenticateTo;
import de.fhm.akfo.shop.authentication.rest.to.UserTo;
import de.fhm.akfo.shop.authentication.rest.to.mapper.UserToDtoMapper;
import de.fhm.akfo.shop.authentication.rest.util.ExtractTokenInformation;
import de.fhm.akfo.shop.authentication.service.api.AuthenticationService;
import de.fhm.akfo.shop.authentication.service.api.dto.UserDto;
import de.fhm.akfo.shop.authentication.service.api.exception.AuthenticationValidationException;
import io.jsonwebtoken.Claims;

@Component
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
	
	/** Logger dieser Klasse. */
	private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

	private static final String AUTHENTICATION_SCHEME = "Basic";
	
	@Inject
	private AuthenticationService authenticationService;

	
	@Context 
	HttpHeaders headers;

	
    @GET
    @DenyAll
    @RolesAllowed("user")
    public Response getUserdata() {
   		ExtractTokenInformation extractToken = new ExtractTokenInformation();
    	String authHeader = headers.getRequestHeader(HttpHeaders.AUTHORIZATION).get(0);
    	LOG.info("Für die Nutzerdaten {} wird ein Token ausgegeben.", authHeader);
    	
    	AuthenticateTo jwtTo = new AuthenticateTo(extractToken.stringifyJWTToken(authHeader));
    	LOG.info("AuthenticateTo für Token angelegt:" + jwtTo.getJwtToken());
    	
    	Map<String, Object> credentialmap = null;
		try{
			Claims credentials = extractToken.parseJWT(jwtTo.getJwtToken());
			credentialmap = extractToken.getCredentialsOfToken(credentials);
		}catch (AuthenticationValidationException e) {
			LOG.error("Couldn't extract credentials from Token.");
		} catch(Exception e){
			LOG.error("Parsing of JWT-Token gone wrong!");
		}
   		
    	UserDto user = null;
		try {
			user = authenticationService.getUserData((String) credentialmap.get("username"));
		} catch (AuthenticationValidationException e) {
			LOG.error("Some Userdata was not available from Token of User: {}", (String) credentialmap.get("username"));
		}
    	UserTo to = UserToDtoMapper.INSTANCE.dtoToTo(user);
    	
        return Response.ok(to).build();
    }
    
    
    
    @POST
    @PermitAll
    public Response registrate(UserTo userTo){
    	LOG.info("Methode registrate() mit den Nutzerdaten {} wird ausgeführt.", userTo);
    	if(userTo == null){
    		return Response.status(Status.BAD_REQUEST).build();
    	}
    	
    	try {
    		return Response.ok(authenticationService.saveUserData(UserToDtoMapper.INSTANCE.toToDto(userTo))).build();
		} catch (AuthenticationValidationException e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
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
