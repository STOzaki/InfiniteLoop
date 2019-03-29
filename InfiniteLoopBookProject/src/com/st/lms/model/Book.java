package com.st.lms.model;

public class Book extends Entry{
	private int authorID, publisherID;
	public Book() {
		super();
		authorID = -1;
		publisherID = -1;
	}
	
	public Book(String name, int id) {
		super(name, id);
		authorID = -1;
		publisherID = -1;
	}
	
	public Book(String name, int id, int authorID, int publisherID) {
		super(name, id);
		this.authorID = authorID;
		this.publisherID = publisherID;
	}

	public int getAuthorID() {
		return authorID;
	}
	
	public void setAuthorID(int authorID) {
		this.authorID = authorID;
	}
	
	public int getPublisherID() {
		return publisherID;
	}
	
	public void setPublisherID(int publisherID) {
		this.publisherID = publisherID;
	}
}
