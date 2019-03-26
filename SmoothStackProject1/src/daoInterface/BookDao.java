package daoInterface;

import java.util.ArrayList;

import pojos.Book;

public interface BookDao {
	public void addBook(Book book);
	public Book getBook(int bookId);
	public ArrayList<Book> getAllBooks();
	public void updateBook(Book book);
	public void deleteBook(Book book);
}