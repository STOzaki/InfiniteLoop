package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import daoImplementations.AuthorDaoImpl;
import daoImplementations.BookDaoImpl;
import daoInterface.AuthorDao;
import daoInterface.BookDao;
import driver.Main;
import pojos.Author;
import pojos.Book;

public class AuthorService {

	private static AuthorDao authorDao = new AuthorDaoImpl();
	private static BookDao bookDao = new BookDaoImpl();
	static File csvBookFile = new File("./database/Books.csv");
	static Scanner scan = new Scanner(System.in);
	
	//Purpose of Service class:
	//read stuff and see if ok to save
	//can do some "business logic" like validation. 
	//if passes, send data to the Dao for it to be saved in db
	
	public static boolean checkAuthorExists(int id) {
		Author author = authorDao.getAuthor(id);
		
		if(author.getAuthorName() == null)
			return false;
		else
			return true;
	}
	
	public static void addAuthor() {
		int authorId;
		String authorName;
		boolean authorExists;
		
		System.out.print("Enter author id: ");
		authorId = scan.nextInt();
		authorExists = checkAuthorExists(authorId);
		
		if(authorExists) {
			System.out.println("Author id already exists!");
			Main.menu();
		}
		else {
			//fixes issue of nextInt not consuming /n
			scan.nextLine();
			
			System.out.print("Enter author name: ");
			authorName = scan.nextLine();
			
			Author author = new Author(authorId, authorName);
			authorDao.addAuthor(author);
			System.out.println("Author Saved!");
			Main.menu();
		}
	}
	
	public static void getAuthor() {
		int authorId;
		
		System.out.print("What is the author id: ");
		authorId = scan.nextInt();
		Author author = authorDao.getAuthor(authorId);
		
		if(author.getAuthorName() == null) {
			System.out.println("Author id not found!");
			Main.menu();
		}
		else {
			System.out.println("Name: " + author.getAuthorName());
			Main.menu();
		}
	}
	
	public static void getAllAuthors() {
		ArrayList<Author> authors = authorDao.getAllAuthors();
		
		System.out.println(String.format("%-12s %-5s %-20s", "AuthorId", "|", "Name"));
		System.out.println(String.format("%s", "----------------------------------------"));
		 
		for(int i=0; i<authors.size(); i++) {
			System.out.println(String.format("%-12s %-5s %-20s", 
				authors.get(i).getAuthorId(), 
				"|", 
				authors.get(i).getAuthorName())
			);
		}
		Main.menu();
	}
	
	public static void updateAuthor() {
		int authorId;
		String authorName;
		boolean authorExists;

		System.out.print("What is the author id: ");
		authorId = scan.nextInt();
		authorExists = checkAuthorExists(authorId);
		
		if(authorExists) {
			//fixes issue of nextInt not consuming /n
			scan.nextLine();
			
			System.out.print("Update author name to: ");
			authorName = scan.nextLine();
			
			Author author = new Author(authorId, authorName);
			//authorDao.updateAuthor(authorId + "|" + authorName);
			authorDao.updateAuthor(author);
			System.out.println("Author updated!");
			Main.menu();
		}
		else {
			//user starts over again if authorId not found
			System.out.println("Author id not found!");
			Main.menu();
		}
	}
	
	//when deleting an author, need to delete all their books as well
	public static void deleteAuthor() {
		int authorId;
		boolean authorExists;
		ArrayList<Integer> matchedBookIds = new ArrayList<>();
		
		System.out.print("What is the author id: ");
		authorId = scan.nextInt();
		authorExists = checkAuthorExists(authorId);
		
		if(authorExists) {
			//delete author
			Author author = new Author();
			author.setAuthorId(authorId);
			authorDao.deleteAuthor(author);
			
			//find the bookIds linked to authorId and add to an ArrayList
			try {
				Scanner scan = new Scanner(csvBookFile);
				while(scan.hasNextLine()) {
					String bookLine = scan.nextLine();
					String[] strArray = bookLine.split(",");
					
					if(strArray[2].equals(Integer.toString(authorId))) {
						matchedBookIds.add(Integer.parseInt(strArray[0]));
					}
				}
				scan.close();
			}
			catch(FileNotFoundException e) {
				e.printStackTrace();
			}

			//delete books
			for(int i=0; i<matchedBookIds.size(); i++) {
				Book book = new Book();
				book.setBookId(matchedBookIds.get(i));
				bookDao.deleteBook(book);
			}
			
			System.out.println("Author deleted (along with their books)!");
			Main.menu();
		}
		else {
			System.out.println("Author id not found!");
			Main.menu();
		}
	}
}