package model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class OrderForm {
	private String bookTitle;
	private String bookAuthor;
	private String pnr;

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("bookTitle", bookTitle)
				.append("bookAuthor", bookAuthor)
				.append("pnr", pnr)
				.toString();
	}
}
