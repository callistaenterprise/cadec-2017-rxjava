package se.callista.cadec2017.rxjava.model;

public class Order {
	private Book book;
	private Address address;
	private Coordinates coordinates;
	private int orderId;

	public Order(Book book, Address address, Coordinates coordinates) {
		this.book = book;
		this.address = address;
		this.coordinates = coordinates;
	}

	public Order withOrderId(int orderId) {
		return new Order(book, address, coordinates).withOrderId(orderId);
	}

	public Book getBook() {
		return book;
	}

	public Address getAddress() {
		return address;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public int getOrderId() {
		return orderId;
	}
}
