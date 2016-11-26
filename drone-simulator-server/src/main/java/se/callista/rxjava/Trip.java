package se.callista.rxjava;

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
}
