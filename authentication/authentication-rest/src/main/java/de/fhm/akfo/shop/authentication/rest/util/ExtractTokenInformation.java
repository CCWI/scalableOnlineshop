package de.fhm.akfo.shop.authentication.rest.util;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fhm.akfo.shop.authentication.service.api.exception.AuthenticationValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

public class ExtractTokenInformation {

	/** Logger dieser Klasse. */
	private static final Logger LOG = LoggerFactory.getLogger(ExtractTokenInformation.class);
	
	public Claims parseJWT(String jwt) throws ExpiredJwtException {
		LOG.info("Start JWT mit dem Token {}", jwt);
		return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("a3br-rni2-ie2b-lo4y"))
				.parseClaimsJws(jwt).getBody();
	}

	public String stringifyJWTToken(String jwt){
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
	
	public Map<String, Object> getCredentialsOfToken(Claims claims) throws AuthenticationValidationException {
		Map<String, Object> credentials = new HashMap<String, Object>();

		if(claims == null){
			throw new AuthenticationValidationException("No Usercredentials in Token.");
		}
		if(claims.containsKey("username") && claims.containsKey("role") &&
		   claims.containsKey("firstname") && claims.containsKey("lastname")){
			credentials.put("username", claims.get("username"));
			credentials.put("role", claims.get("role"));
			credentials.put("firstname", claims.get("firstname"));
			credentials.put("lastname", claims.get("lastname"));
		}else{
			throw new AuthenticationValidationException("Credentials in Token not fully provided.");
		}
		
		return credentials;
	}

}
