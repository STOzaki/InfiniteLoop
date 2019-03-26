package driver;

import java.util.Scanner;

import services.AuthorService;
import services.BookService;
import services.PublisherService;

public class Main {

	static Scanner scan = new Scanner(System.in);
	private static int selection;
	
	public static void menu() {
		System.out.println();
		System.out.println("What action would you like to do?");
		System.out.println("1 - Add...");
		System.out.println("2 - Find...");
		System.out.println("3 - Find All...");
		System.out.println("4 - Update...");
		System.out.println("5 - Delete...");
		System.out.println("6 - Quit");
		
		selection = scan.nextInt();
		
		if(selection == 1) {
			addOperations();
		}
		else if(selection == 2) {
			findOperations();
		}
		else if(selection == 3) {
			findAllOperations();
		}
		else if(selection == 4) {
			updateOperations();
		}
		else if(selection == 5) {
			deleteOperations();
		}
		else if(selection == 6) {
			System.out.println("Goodbye!");
			return;
		}
	}
	
	public static void addOperations() {
		System.out.println("What do you want to add?");
		System.out.println("1 - A book");
		System.out.println("2 - A author");
		System.out.println("3 - A publisher");
		
		selection = scan.nextInt();
		
		if(selection == 1) {
			BookService.addBook();
		}
		else if(selection == 2) {
			AuthorService.addAuthor();
		}
		else if(selection == 3) {
			PublisherService.addPublisher();
		}
	}
	
	public static void findOperations() {
		System.out.println("What do you want to find?");
		System.out.println("1 - A book");
		System.out.println("2 - A author");
		System.out.println("3 - A publisher");
		
		selection = scan.nextInt();
		
		if(selection == 1) {
			BookService.getBook();
		}
		else if(selection == 2) {
			AuthorService.getAuthor();
		}
		else if(selection == 3) {
			PublisherService.getPublisher();
		}
	}
	
	public static void findAllOperations() {
		System.out.println("What do you want to find all of?");
		System.out.println("1 - Books");
		System.out.println("2 - Authors");
		System.out.println("3 - Publishers");
		
		selection = scan.nextInt();
		
		if(selection == 1) {
			BookService.getAllBooks();
		}
		else if(selection == 2) {
			AuthorService.getAllAuthors();
		}
		else if(selection == 3) {
			PublisherService.getAllPublishers();
		}
	}
	
	public static void updateOperations() {
		System.out.println("What do you want to update?");
		System.out.println("1 - A book");
		System.out.println("2 - A author");
		System.out.println("3 - A publisher");
		
		selection = scan.nextInt();
		
		if(selection == 1) {
			BookService.updateBook();
		}
		else if(selection == 2) {
			AuthorService.updateAuthor();
		}
		else if(selection == 3) {
			PublisherService.updatePublisher();
		}
	}
	
	public static void deleteOperations() {
		System.out.println("What do you want to delete?");
		System.out.println("1 - A book");
		System.out.println("2 - A author");
		System.out.println("3 - A publisher");
		
		selection = scan.nextInt();
		
		if(selection == 1) {
			BookService.deleteBook();
		}
		else if(selection == 2) {
			AuthorService.deleteAuthor();
		}
		else if(selection == 3) {
			PublisherService.deletePublisher();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome to the library app!");
		System.out.println();
		
		menu();
	}
}