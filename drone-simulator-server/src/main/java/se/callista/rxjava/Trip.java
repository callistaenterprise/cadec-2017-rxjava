package se.callista.rxjava;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Trip {
	private Coordinate from;
	private Coordinate to;
	private int speed;

	public Trip(Coordinate from, Coordinate to, int speed) {
		this.from = from;
		this.to = to;
		this.speed = speed;
	}

	public Coordinate getFrom() {
		return from;
	}

	public Coordinate getTo() {
		return to;
	}

	public int getSpeed() {
		return speed;
	}

	public double getDistance() {
		return GeoMath.distance(from, to);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
				.append("from", from)
				.append("to", to)
				.append("speed", speed)
				.toString();
	}

	public double getEta(Coordinate fromPosition) {
		return GeoMath.distance(fromPosition, to) / speed * 3600;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		Trip trip = (Trip) o;

		return new EqualsBuilder()
				.append(speed, trip.speed)
				.append(from, trip.from)
				.append(to, trip.to)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(from)
				.append(to)
				.append(speed)
				.toHashCode();
	}
}
