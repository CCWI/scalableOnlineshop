package de.fhm.akfo.shop.authentication.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.junit.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTTokenGeneratorTest {

	@Test
	public void createJWTTest() {
		Map<String, Object> credentials = new HashMap<String, Object>();
		
		String[] allRoles = {"admin", "superadmin"};
		credentials.put("role", allRoles);
		credentials.put("firstname", "John");
		credentials.put("lastname", "Doe");
		
		JWTTokenGenerator generator = new JWTTokenGenerator();
		String token = generator.createJWT("1", "Shopsystem", "Authentication", 1800000, credentials);

		System.out.println(token);

		parseJWT(token);
	}


	private void parseJWT(String jwt) {

		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("a3br-rni2-ie2b-lo4y"))
									 .parseClaimsJws(jwt).getBody();
		
		System.out.println("ID: " + claims.getId());
		System.out.println("Subject: " + claims.getSubject());
		System.out.println("Issuer: " + claims.getIssuer());
		System.out.println("Expiration: " + claims.getExpiration());
		System.out.println("Roles: " + claims.get("role", ArrayList.class));
		System.out.println("Firstname: " + claims.get("firstname", String.class));
		System.out.println("Lastname: " + claims.get("lastname", String.class));
	}
}
