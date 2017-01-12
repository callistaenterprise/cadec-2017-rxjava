package model;

public class Order {
	private Book book;
	private Address address;
	private Coordinates coordinates;
	private Integer orderId;

	public Order(Book book, Address address, Coordinates coordinates) {
		this.book = book;
		this.address = address;
		this.coordinates = coordinates;
	}

	public Order withOrderId(Integer orderId) {
		final Order order = new Order(book, address, coordinates);
		order.orderId = orderId;
		return order;
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

	public Integer getOrderId() {
		return orderId;
	}
}
