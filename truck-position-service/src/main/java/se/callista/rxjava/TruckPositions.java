package se.callista.rxjava;

import java.util.HashMap;
import java.util.Map;

public class TruckPositions {

	final Map<Integer, Coordinate> trucks = new HashMap<>();

	public TruckPositions() {
		trucks.put(1, new Coordinate(57.490197, 12.076019)); //Kungsbacka
		trucks.put(2, new Coordinate(57.490197, 12.076019)); //Partille
		trucks.put(3, new Coordinate(57.720923, 11.911977)); //Lundby
		trucks.put(4, new Coordinate(57.720923, 11.911977)); //Kungälv
		trucks.put(5, new Coordinate(57.654073, 12.015198)); //Mölndal
	}

	public Coordinate getTruckPosition(int truckId) {
		return trucks.get(truckId);
	}

}
