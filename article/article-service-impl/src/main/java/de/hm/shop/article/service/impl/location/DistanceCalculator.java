package de.hm.shop.article.service.impl.location;

public class DistanceCalculator {

	/**
	 * Berechnung der Distanz in Metern anhand zweier Positionspunkte
	 * @param lat1, Breitengrad der Position1
	 * @param lng1, Längengrad der Position1
	 * @param lat2, Breitengrad der Position2
	 * @param lng2, Längengrad der Position2
	 * @return
	 */
	public float calcDist(float lat1, float lng1, float lat2, float lng2) {
		double earthRadius = 6371000; // meters
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		float dist = (float) (earthRadius * c);

		return dist;
	}
	
	

}
