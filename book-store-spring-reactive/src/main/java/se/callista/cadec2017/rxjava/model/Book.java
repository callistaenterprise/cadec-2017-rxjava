package se.callista.cadec2017.rxjava.model;

public class Book {
	public String title;
	public String author;
	public String isbn10;
	public String isbn13;

	public Book() {
	}

	public Book(String title, String author, String isbn10, String isbn13) {
		this.title = title;
		this.author = author;
		this.isbn10 = isbn10;
		this.isbn13 = isbn13;
	}
}
