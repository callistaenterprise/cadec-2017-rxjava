package se.callista.rxjava;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Coordinate {
	private double longitude;
	private double latitude;

	public Coordinate(double latitude, double longitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}


	@Override
	public String toString() {
		return "Coordinate{" +
				"longitude=" + longitude +
				", latitude=" + latitude +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		Coordinate that = (Coordinate) o;

		return new EqualsBuilder()
				.append(longitude, that.longitude)
				.append(latitude, that.latitude)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(longitude)
				.append(latitude)
				.toHashCode();
	}
}
