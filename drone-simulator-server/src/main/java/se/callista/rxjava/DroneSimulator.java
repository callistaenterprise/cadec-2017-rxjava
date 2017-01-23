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

	public DroneSimulator() {
		logger.debug("Creating new DroneSimulator");
	}

	public Observable<Coordinate> simulateDroneTrip(Trip trip, long samplingTime) {

		//Calculate distance between sampled wayoints
		final double samplingDistance = toMetersPerSecond(trip.getSpeed()) * ((double)samplingTime / 1000);


		//Create stream of waypoints
		final Observable<Coordinate> coordinateStream = Observable.from(waypoints(trip.getFrom(), trip.getTo(), samplingDistance));


		//Create stream of 'ticks'
		final Observable<Long> metronom = Observable.interval(samplingTime, TimeUnit.MILLISECONDS);


		//Zip stream waypoints with metronom
		return coordinateStream.zipWith(metronom, (coordinate, tick) -> coordinate);

	}
}
