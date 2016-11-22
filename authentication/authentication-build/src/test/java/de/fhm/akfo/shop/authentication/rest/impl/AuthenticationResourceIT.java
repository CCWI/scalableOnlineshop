package de.fhm.akfo.shop.authentication.rest.impl;

import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.fhm.akfo.shop.authentication.rest.to.UserTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ApplicationTest
public class AuthenticationResourceIT {

	// Username: John, Password: Doe = Basic Sm9objpEb2U= 
	private String credentials = "Basic Sm9objpEb2U=";

	
    @Test
    public void getToken() {
    	Client client = ClientBuilder.newClient();
    	WebTarget target = client.target("http://localhost:8094").path("authentication");
    	String response = target.request(MediaType.APPLICATION_JSON).header("AUTHORIZATION", credentials).get(String.class);
    	System.out.println(response);
    }

    @Test
    public void decodeCredentials() {
    	AuthenticationResource authResource = new AuthenticationResource();
    	UserTo encodedCredentials = authResource.decodeCredentials(credentials);
    	
    	System.out.println("|"+encodedCredentials.getUsername()+"|");
    	System.out.println("|"+encodedCredentials.getPassword()+"|");
    	assertTrue(encodedCredentials.getUsername().equals("John") && encodedCredentials.getPassword().equals("Doe"));
    }
}