package se.callista.cadec2017.rxjava.model;

public class Coordinates {
	public String lng;
	public String lat;
	public int eta;

	public Coordinates() {
	}

	public Coordinates(String lng, String lat, int eta) {
		this.lng = lng;
		this.lat = lat;
		this.eta = eta;
	}
}
