package se.callista.rxjava;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class GeoMath {

	private static double r = 6371.0;


	public static List<Coordinate> waypoints(Coordinate from, Coordinate to, double distanceBetween) {
		final double lat1 = toRadians(from.getLat());
		final double lat2 = toRadians(to.getLat());
		final double long1 = toRadians(from.getLng());
		final double long2 = toRadians(to.getLng());

		double d = r * acos(sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(long2 - long1)) * 1000;
		double θ = atan2(sin(long2 - long1) * cos(lat2), cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(long2 - long1));

		final List<Coordinate> waypoints = new ArrayList<>();
		for (int n = 0; n <= d; n += distanceBetween) {
			waypoints.add(waypoint(from, θ, n));
		}
		return waypoints;
	}


	public static double distance(Coordinate from, Coordinate to) {
		double theta = from.getLng() - to.getLng();
		double dist = sin(toRadians(from.getLat())) * sin(toRadians(to.getLat())) + cos(toRadians(from.getLat())) * cos(toRadians(to.getLat())) * cos(toRadians(theta));

		dist = acos(dist);
		dist = toDegrees(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;

		return dist;
	}

	private static Coordinate waypoint(Coordinate c, double θ, double d) {
		final double lat1 = toRadians(c.getLat());
		final double long1 = toRadians(c.getLng());

		d = d / 1000;
		double lat2 = asin(sin(lat1) * cos(d / r) + cos(lat1) * sin(d / r) * cos(θ));
		double long2 = long1 + atan2(sin(θ) * sin(d / r) * cos(lat1), cos(d / r) - sin(lat1) * sin(lat2));
		long2 = (long2 + 3 * PI) % (2 * PI) - PI;

		return new Coordinate(toDegrees(lat2), toDegrees(long2));
	}

	public static double toMetersPerSecond(double kmPerHour) {
		return kmPerHour / 3.6;
	}

}
