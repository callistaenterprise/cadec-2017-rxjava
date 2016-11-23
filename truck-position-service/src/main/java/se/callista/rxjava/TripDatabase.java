package se.callista.rxjava;

import java.util.HashMap;
import java.util.Map;

public class TripDatabase {

	final Map<Integer, Trip> trips = new HashMap<>();

	public TripDatabase() {
		trips.put(1, new Trip(new Coordinate(57.490197, 12.076019), new Coordinate(57.720923, 11.911977), 50)); //Kungsbacka - ???
		trips.put(2, new Trip(new Coordinate(57.490197, 12.076019), new Coordinate(57.720923, 11.911977), 50)); //Partille - ???
		trips.put(3, new Trip(new Coordinate(57.720923, 11.911977), new Coordinate(57.654073, 12.015198), 50)); //Lundby - ???
		trips.put(4, new Trip(new Coordinate(57.720923, 11.911977), new Coordinate(57.654073, 12.015198), 50)); //Kungälv - ???
		trips.put(5, new Trip(new Coordinate(57.654073, 12.015198), new Coordinate(57.490197, 12.076019), 50)); //Mölndal - ???
	}

	public Trip getTripByDrone(int droneId) {
		return trips.get(droneId);
	}

}
