package de.hm.shop.payment.rest.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;


/**
 * Loginfilter für die Authentifizierung und Authorisierung
 * @author Maximilian.Auch
 */
@Component
@Provider
public class PaymentLoginFilter implements ContainerRequestFilter {

	/** Logger dieser Klasse. */
	private static final Logger LOG = LoggerFactory.getLogger(PaymentLoginFilter.class);
	
	@Context
	private ResourceInfo resourceInfo;

	private static final String AUTHORIZATION_PROPERTY = "Authorization";

	
	public void filter(ContainerRequestContext requestContext) throws IOException {
		LOG.info("Authentication/Authorization-filter prüft...");
		
		Method method = resourceInfo.getResourceMethod();
		if (!method.isAnnotationPresent(PermitAll.class)) {
			if (method.isAnnotationPresent(DenyAll.class)) {
				throw new ForbiddenException("Method is not accessable");
			}

			final MultivaluedMap<String, String> headers = requestContext.getHeaders();
			final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

			if (authorization == null || authorization.isEmpty()) {
				throw new NotAuthorizedException("Authorization header must be provided");
			}
				
			String encodedUserPassword = stringifyJWTToken(authorization.get(0));
			LOG.info("Authorization-Header to parse: "+ encodedUserPassword);
			
			Claims credentials;
			try{
				credentials = parseJWT(encodedUserPassword);
			}catch(Exception e){
				LOG.info("Parsing of JWT-Token gone wrong!");
				throw new NotAuthorizedException("Authorization header must be provided");
			}
			
			if(!validateToken(credentials)){
				LOG.info("Credentials of Token are not as expected!");
				throw new NotAuthorizedException("Authorization header must be provided");
			}
			
			if (method.isAnnotationPresent(RolesAllowed.class)) {
				RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
				Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
				
				if(!ceckAllowedRoles(rolesSet, credentials.get("role", ArrayList.class))){
					throw new ForbiddenException("Method is not accessable");
				}
			}
		}
	}

	
	private boolean ceckAllowedRoles(Set<String> rolesSet, ArrayList<LinkedHashMap<String, String>> userRoles) {
		boolean userHasRole = false;
		for(LinkedHashMap<String, String> userRolesMap : userRoles){
			for(String userRole : userRolesMap.values()){
				if(rolesSet.contains(userRole)){
					userHasRole = true;
				}
			}
		}
		return userHasRole;
	}


	private boolean validateToken(Claims credential) {
		if(credential.getIssuer().equals("shopsystem") && 
		   credential.getSubject().equals("authentication") &&
		   credential.getExpiration().after(new Date()) &&
		   credential.get("role", ArrayList.class) != null){
			return true;
		}
		return false;
	}


	private Claims parseJWT(String jwt) throws ExpiredJwtException {
		LOG.info("Start JWT mit dem Token {}", jwt);
		return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("a3br-rni2-ie2b-lo4y"))
				.parseClaimsJws(jwt).getBody();
	}

	private String stringifyJWTToken(String jwt){
		LOG.info("StringifyJWTToken: {}", jwt);
		String token = null;
		if(jwt != null && jwt.length() > 0){
			if(jwt.charAt(0) == '\"' && jwt.charAt(jwt.length()-1) == '\"'){
				token = jwt.substring(1, jwt.length()-1);
			}else{
				token = jwt;
			}
		}
		return token;
	}
}