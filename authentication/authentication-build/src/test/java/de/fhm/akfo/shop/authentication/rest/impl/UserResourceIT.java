package de.fhm.akfo.shop.authentication.rest.impl;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.fhm.akfo.shop.authentication.rest.to.UserTo;


@RunWith(SpringJUnit4ClassRunner.class)
@ApplicationTest
public class UserResourceIT {

	private UserTo to;
	
	@Before
	public void init(){
		to = new UserTo("testuser", "testpassword");
	}
	
    @Test
    public void saveAuthenticatendataOfUser() {
    	Client client = ClientBuilder.newClient();
    	WebTarget target = client.target("http://localhost:8094").path("user");
    	Response result = target.request(MediaType.APPLICATION_JSON).post(Entity.json(to));
    	
    	Assert.assertTrue(result != null);
    	Assert.assertTrue(result.getStatus() == 200);
    }
    
}
