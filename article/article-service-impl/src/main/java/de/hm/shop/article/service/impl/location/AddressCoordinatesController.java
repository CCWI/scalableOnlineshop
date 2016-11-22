package de.hm.shop.article.service.impl.location;

import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.filter.LoggingFilter;
import org.json.JSONArray;
import org.json.JSONObject;

public class AddressCoordinatesController {
	// http://nominatim.openstreetmap.org/search.php?q=Moosburg+4%2C+85368+4%2C+M%C3%BCnchenerstr&addressdetails=1&format=json

	private static final String HOST = "http://nominatim.openstreetmap.org";
	private static final Logger LOG = Logger.getLogger(AddressCoordinatesController.class.getName());
	private final Client client = ClientBuilder.newClient().register(new LoggingFilter(LOG, true));

	
	public Float[] getCoordinates(String city, String postcode) {
		final Response result = client.target(HOST).path("search.php").queryParam("q", city + " " + postcode)
				.queryParam("addressdetails", 1).queryParam("format", "json").request(MediaType.APPLICATION_JSON)
				.get(Response.class);

		String entityMap = result.readEntity(String.class);
		Float[] coordinates = parseJson(entityMap);

		System.out.println("lat: " + coordinates[0] + ", lon:" + coordinates[1]);
		
		return coordinates;
	}

	
	public Float[] parseJson(String resultString) {
		Float[] coordinates = new Float[2];

		JSONArray jsonArray = new JSONArray(resultString);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject objectInArray = jsonArray.getJSONObject(i);
			String[] elementNames = JSONObject.getNames(objectInArray);
			for (String elementName : elementNames) {
				try {
					String value = objectInArray.getString(elementName);
					if (elementName.equals("lat")) {
						coordinates[0] = Float.parseFloat(value);
					}
					if (elementName.equals("lon")) {
						coordinates[1] = Float.parseFloat(value);
					}
				} catch (Exception e) {
				}
			}
		}
		return coordinates;
	}

}
