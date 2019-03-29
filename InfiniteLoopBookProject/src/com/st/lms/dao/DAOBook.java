package com.st.lms.dao;


import java.io.IOException;
import java.util.List;
import com.st.lms.model.*;

public interface DAOBook extends DAOBasic {
	
	public List<Book> loadBooks() throws IOException; //returns a list containing all books in the database
	public Book getBook(int id) throws IOException; //returns a book with corresponding id#. if not found, returns null
	public void addBook(Book book) throws IOException; //appends given book to book database
	public void replace(int id, Book book) throws IOException; //replaces item with given id# with given Book data
	public boolean hasBook(int id) throws IOException; //returns true if a book with given id already exists in database
	public void removePublisher(int publisherID) throws IOException; //removes all books under this publisher
	public void removeAuthor(int authorID) throws IOException; //removes all books under this author
}
