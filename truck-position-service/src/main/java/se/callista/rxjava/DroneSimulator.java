package se.callista.rxjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static se.callista.rxjava.GeoMath.toMetersPerSecond;
import static se.callista.rxjava.GeoMath.waypoints;

public class DroneSimulator {
	private TripDatabase tripDatabase;
	private static Logger logger = LoggerFactory.getLogger(TruckPositionServer.class);

	private Map<Integer, Observable<Coordinate>> simulations = new ConcurrentHashMap<>();

	public DroneSimulator(TripDatabase tripDatabase) {
		logger.debug("Creating new DroneSimulator");
		this.tripDatabase = tripDatabase;
	}

	public synchronized Observable<Coordinate> simulateDroneTrip(int droneId) {
		logger.debug("Simulating trip for drone {}", droneId);

		if (simulations.containsKey(droneId)) {
			logger.debug("Reuse simulated trip {}", droneId);
			return simulations.get(droneId);
		}

		final Trip trip = tripDatabase.getTripByDrone(droneId);

		final List<Coordinate> waypoints = waypoints(trip.getFrom(), trip.getTo(), toMetersPerSecond(trip.getSpeed()));

		final Observable<Coordinate> simulatedTrip = Observable.interval(1, TimeUnit.SECONDS)
				.zipWith(Observable.from(waypoints), (tick, coordinate) -> coordinate).publish().autoConnect();


		logger.debug("Put simulated trip in cache {}", droneId);
		simulations.put(droneId, simulatedTrip);

		return simulatedTrip;

	}
}
