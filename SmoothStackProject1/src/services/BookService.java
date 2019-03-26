package services;

import java.util.ArrayList;
import java.util.Scanner;

import daoImplementations.AuthorDaoImpl;
import daoImplementations.BookDaoImpl;
import daoImplementations.PublisherDaoImpl;
import daoInterface.AuthorDao;
import daoInterface.BookDao;
import daoInterface.PublisherDao;
import driver.Main;
import pojos.Author;
import pojos.Book;
import pojos.Publisher;

public class BookService {
	
	private static BookDao bookDao = new BookDaoImpl();
	private static AuthorDao authorDao = new AuthorDaoImpl();
	private static PublisherDao publisherDao = new PublisherDaoImpl();
	static Scanner scan = new Scanner(System.in);
	
	public static boolean checkBookExists(int id) {
		Book book = bookDao.getBook(id);
		
		if(book.getTitle() == null)
			return false;
		else
			return true;
	}
	
	public static void addBook() {
		int bookId, authorId, publisherId;
		String title, authorName, publisherName, publisherAddress, publisherPhone;
		boolean bookExists;
		boolean authorExists;
		boolean publisherExists;
		
		System.out.print("Enter book id: ");
		bookId = scan.nextInt();
		bookExists = checkBookExists(bookId);
		
		//user starts over again if a book already exists
		if(bookExists) {
			System.out.println("Book id already exists!");
			Main.menu();
		}
		
		System.out.print("Enter author id: ");
		authorId = scan.nextInt();
		authorExists = AuthorService.checkAuthorExists(authorId);
		
		System.out.print("Enter publisher id: ");
		publisherId = scan.nextInt();
		publisherExists = PublisherService.checkPublisherExists(publisherId);
		
		//book, author, and publisher are all new
		if(!bookExists && !authorExists && !publisherExists) {
			scan.nextLine();
			System.out.print("Enter book title: ");
			title = scan.nextLine();
			
			System.out.print("Enter author name: ");
			authorName = scan.nextLine();
			
			System.out.print("Enter publisher name: ");
			publisherName = scan.nextLine();
			
			System.out.print("Enter publisher address: ");
			publisherAddress = scan.nextLine();
			
			System.out.print("Enter publisher phone xxx-xxx-xxxx: ");
			publisherPhone = scan.nextLine();
			
			//create objects to be saved
			Book book = new Book(bookId, title, authorId, publisherId);
			Author author = new Author(authorId, authorName);
			Publisher publisher = new Publisher(publisherId, publisherName, publisherAddress, publisherPhone);
			
			bookDao.addBook(book);
			authorDao.addAuthor(author);
			publisherDao.addPublisher(publisher);
			
			System.out.println("Book Saved!");
			Main.menu();	
		}
		
		//we don't need to ask user for things already in DB.
		//book is new, but author and publisher aren't
		if(!bookExists && authorExists && publisherExists) {
			//fixes issue of nextInt not consuming /n
			scan.nextLine();
			
			System.out.print("Enter book title: ");
			title = scan.nextLine();
			
			Book book = new Book(bookId, title, authorId, publisherId);
			bookDao.addBook(book);
			System.out.println("Book Saved!");
			Main.menu();
		}
		
		//book and author are new, but publisher isn't 
		if(!bookExists && !authorExists) {
			scan.nextLine();
			System.out.print("Enter book title: ");
			title = scan.nextLine();

			System.out.print("Enter author name: ");
			authorName = scan.nextLine();
			
			Book book = new Book(bookId, title, authorId, publisherId);
			Author author = new Author(authorId, authorName);
			
			bookDao.addBook(book);
			authorDao.addAuthor(author);
			
			System.out.println("Book Saved!");
			Main.menu();
		}
		
		//book and publisher are new, but author isn't
		if(!bookExists && !publisherExists) {
			scan.nextLine();
			System.out.print("Enter book title: ");
			title = scan.nextLine();
			
			System.out.print("Enter publisher name: ");
			publisherName = scan.nextLine();
			
			System.out.print("Enter publisher address: ");
			publisherAddress = scan.nextLine();
			
			System.out.print("Enter publisher phone xxx-xxx-xxxx: ");
			publisherPhone = scan.nextLine();
			
			Book book = new Book(bookId, title, authorId, publisherId);
			Publisher publisher = new Publisher(publisherId, publisherName, publisherAddress, publisherPhone);
		
			bookDao.addBook(book);
			publisherDao.addPublisher(publisher);
			
			System.out.println("Book Saved!");
			Main.menu();	
		}
	}
	
	public static void getBook() {
		int bookId;
		
		System.out.print("What is the book id: ");
		bookId = scan.nextInt();
		Book book = bookDao.getBook(bookId);
		
		if(book.getTitle() == null) {
			System.out.println("Book id not found!");
			Main.menu();
		}
		else {
			System.out.println("Title: " + book.getTitle());
			System.out.println("AuthorId: " + book.getAuthorId());
			System.out.println("PublisherId: " + book.getPublisherId());
			Main.menu();
		}
	}
	
	public static void getAllBooks() {
		ArrayList<Book> books = bookDao.getAllBooks();
		
		System.out.println(String.format("%-13s %-5s %-30s %-5s %-13s %-5s %-15s", "BookId", "|", "Title", "|", "AuthorId", "|", "PublisherId"));
		System.out.println(String.format("%s", "--------------------------------------------------------------------------------------------"));
		 
		for(int i=0; i<books.size(); i++) {
			System.out.println(String.format("%-13s %-5s %-30s %-5s %-13s %-5s %-15s", 
				books.get(i).getBookId(), 
				"|", 
				books.get(i).getTitle(), 
				"|", 
				books.get(i).getAuthorId(),
				"|",
				books.get(i).getPublisherId())
			);
		}
		Main.menu();
	}
	
	//choosing not to let user update authorId or publisherId
	public static void updateBook() {	
		int bookId;
		String title;
		boolean bookExists;

		System.out.print("What is the book id: ");
		bookId = scan.nextInt();
		bookExists = checkBookExists(bookId);
		
		if(bookExists) {
			scan.nextLine();
			System.out.print("Update title to: ");
			title = scan.nextLine();
			
			Book book = new Book();
			book.setBookId(bookId);
			book.setTitle(title);
			
			bookDao.updateBook(book);
			System.out.println("Book updated!");
			Main.menu();
		}
		else {
			//user starts over again if bookId not found
			System.out.println("Book id not found!");
			Main.menu();
		}
	}
	
	//don't need to ever delete linked author and publisher 
	//since a future book can be added to them
	public static void deleteBook() {
		int bookId;
		boolean bookExists;
		
		System.out.print("What is the book id: ");
		bookId = scan.nextInt();
		bookExists = checkBookExists(bookId);
		
		if(bookExists) {
			Book book = new Book();
			book.setBookId(bookId);
			
			bookDao.deleteBook(book);
			System.out.println("Book deleted!");
			Main.menu();
		}
		else {
			System.out.println("Book id not found!");
			Main.menu();
		}
	}	
}