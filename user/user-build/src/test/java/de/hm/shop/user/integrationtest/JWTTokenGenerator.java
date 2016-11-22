package de.hm.shop.user.integrationtest;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import io.jsonwebtoken.*;
import java.util.Date;
import java.util.Map;

/**
 * Tokengenerator für Testfälle
 * @author Maximilian.Auch
 */
public class JWTTokenGenerator {

	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

	/**
	 * Erzeugen eines JWT-Token
	 * @param id
	 * @param issuer
	 * @param subject
	 * @param ttlMiS
	 * @param credentials
	 * @return gültiger JWT-Token
	 */
	public String createJWT(String id, String issuer, String subject, long ttlMiS, Map<String, Object> credentials) {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("a3br-rni2-ie2b-lo4y");
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

		JwtBuilder builder = Jwts.builder()
								.setClaims(credentials)
								.setId(id)
								.setIssuedAt(now)
								.setSubject(subject)
								.setIssuer(issuer)
								.signWith(signatureAlgorithm, signingKey)
								.setExpiration(calcExpiriationDate(ttlMiS, nowMillis));

		return builder.compact();
	}

	private Date calcExpiriationDate(long ttlMiS, long nowMillis) {
		long expMillis = nowMillis + ttlMiS;
		return new Date(expMillis);
	}
}