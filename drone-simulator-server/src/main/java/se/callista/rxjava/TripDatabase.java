package se.callista.rxjava;

import java.util.HashMap;
import java.util.Map;

public class TripDatabase {

	final Map<Coordinate, Trip> trips = new HashMap<>();

	public boolean containsTripTo(Coordinate to) {
		return trips.containsKey(to);
	}

	public Trip getTripTo(Coordinate to) {
		return trips.get(to);
	}

	public void addTrip(Trip trip) {
		trips.put(trip.getTo(), trip);
	}

}
