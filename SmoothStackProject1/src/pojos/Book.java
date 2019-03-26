package pojos;

public class Book {
	private int bookId;
	private String title;
	private int authorId;
	private int publisherId;
	
	public Book() {}
	
	public Book(int bookId, String title, int authorId, int publisherId) {
		this.bookId = bookId;
		this.title = title;
		this.authorId = authorId;
		this.publisherId = publisherId;
	}
	
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public int getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}	
}