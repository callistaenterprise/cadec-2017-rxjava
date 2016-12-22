package se.callista.rxjava;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Coordinate {
	private double lng;
	private double lat;

	public Coordinate(double lat, double lng) {
		this.lng = lng;
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public double getLat() {
		return lat;
	}

	@Override
	public String toString() {
		return "Coordinate: {lat = " + lat + ", lng = " + lng + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		Coordinate that = (Coordinate) o;

		return new EqualsBuilder()
				.append(lng, that.lng)
				.append(lat, that.lat)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(lat)
				.append(lng)
				.toHashCode();
	}
}
