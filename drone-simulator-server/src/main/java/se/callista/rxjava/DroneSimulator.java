package se.callista.rxjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static se.callista.rxjava.GeoMath.toMetersPerSecond;
import static se.callista.rxjava.GeoMath.waypoints;

public class DroneSimulator {
	private static Logger logger = LoggerFactory.getLogger(DroneSimulator.class);

	private Map<Trip, Observable<Coordinate>> simulations = new ConcurrentHashMap<>();

	public DroneSimulator() {
		logger.debug("Creating new DroneSimulator");
	}

	public synchronized Observable<Coordinate> simulateDroneTrip(Trip trip, long sampleInterval) {

		if (simulations.containsKey(trip)) {
			logger.debug("Reuse simulated trip {}", trip);
			return simulations.get(trip);
		}

		final double sampledSpeed = toMetersPerSecond(trip.getSpeed()) * ((double)sampleInterval / 1000);
		final List<Coordinate> waypoints = waypoints(trip.getFrom(), trip.getTo(), sampledSpeed);

		final Observable<Coordinate> simulatedTrip = Observable.interval(sampleInterval, TimeUnit.MILLISECONDS)
				.zipWith(Observable.from(waypoints), (tick, coordinate) -> coordinate)
				.doOnCompleted(() -> simulations.remove(trip))
				.publish().autoConnect();


		logger.debug("Put simulated trip in cache {}", trip);
		simulations.put(trip, simulatedTrip);

		return simulatedTrip;

	}
}
