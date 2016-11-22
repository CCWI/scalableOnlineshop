package de.fhm.akfo.shop.authentication.rest.filter;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.fhm.akfo.shop.authentication.rest.impl.AuthenticationResource;
import de.fhm.akfo.shop.authentication.rest.impl.UserResource;
import de.fhm.akfo.shop.authentication.rest.to.RoleTo;
import de.fhm.akfo.shop.authentication.rest.to.UserTo;
import de.fhm.akfo.shop.authentication.rest.util.ExtractTokenInformation;
import io.jsonwebtoken.Claims;


@Component
@Provider
public class AuthenticationLoginFilter implements ContainerRequestFilter {

	/** Logger dieser Klasse. */
	private static final Logger LOG = LoggerFactory.getLogger(AuthenticationLoginFilter.class);
	
	@Context
	private ResourceInfo resourceInfo;

	private static final String AUTHORIZATION_PROPERTY = "Authorization";

	
	public void filter(ContainerRequestContext requestContext) throws IOException {
		LOG.info("Authentication/Authorization-filter prüft...");
		
//		if(isFreeClassCall()){
//			return;
//		}
		
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
			
			ExtractTokenInformation extractToken = new ExtractTokenInformation();
			String encodedUserPassword = extractToken.stringifyJWTToken(authorization.get(0));
			LOG.info("Authorization-Header to parse: "+ encodedUserPassword);
			
			Claims credentials;
			try{
				credentials = extractToken.parseJWT(encodedUserPassword);
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

	
//	private boolean isFreeClassCall() {
//		System.out.println("ResourceInfo.getResourceMethod: " +resourceInfo.getResourceMethod());
//		if(resourceInfo.getResourceClass() == null){
//			return false;
//		}
//		if(resourceInfo.getResourceClass().equals(AuthenticationResource.class)){
//			return true;
//		}
//		try {
//			if(resourceInfo.getResourceClass().equals(UserResource.class) &&
//			   resourceInfo.getResourceMethod().equals(UserResource.class.getMethod("registrate", UserTo.class))){
//				return true;
//			}
//		} catch (NoSuchMethodException e) {
//			LOG.error("TargetMethod not found. " + e.getMessage());
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			LOG.error(e.getMessage());
//			e.printStackTrace();
//		}
//		
//		return false;
//	}


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
}