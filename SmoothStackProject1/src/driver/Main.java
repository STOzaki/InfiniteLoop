package driver;

import java.util.Scanner;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import services.AuthorService;
import services.BookService;
import services.PublisherService;
import test.TestMenu;

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
		
		selection = getSelection(6);
		
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
			System.exit(0);
		}
		else {
			System.out.println("Not a valid option! Try Again.");
			System.out.println();
			menu();
		}
	}
	
	public static void addOperations() {
		System.out.println("What do you want to add?");
		System.out.println("1 - A book");
		System.out.println("2 - A author");
		System.out.println("3 - A publisher");
		System.out.println("4 - Return to Main Menu");
		
		selection = getSelection(4);
		
		if(selection == 1) {
			BookService.addBook();
		}
		else if(selection == 2) {
			AuthorService.addAuthor();
		}
		else if(selection == 3) {
			PublisherService.addPublisher();
		}
		else if(selection == 4) {
			menu();
		}
		else {
			System.out.println("Not a valid option! Try Again.");
			System.out.println();
			addOperations();
		}
	}
	
	public static void findOperations() {
		System.out.println("What do you want to find?");
		System.out.println("1 - A book");
		System.out.println("2 - A author");
		System.out.println("3 - A publisher");
		System.out.println("4 - Return to Main Menu");
		
		selection = getSelection(4);
		
		if(selection == 1) {
			BookService.getBook();
		}
		else if(selection == 2) {
			AuthorService.getAuthor();
		}
		else if(selection == 3) {
			PublisherService.getPublisher();
		}
		else if(selection == 4) {
			menu();
		}
		else {
			System.out.println("Not a valid option! Try Again.");
			System.out.println();
			findOperations();
		}
	}
	
	public static void findAllOperations() {
		System.out.println("What do you want to find all of?");
		System.out.println("1 - Books");
		System.out.println("2 - Authors");
		System.out.println("3 - Publishers");
		System.out.println("4 - Return to Main Menu");
		
		selection = getSelection(4);
		
		if(selection == 1) {
			BookService.getAllBooks();
		}
		else if(selection == 2) {
			AuthorService.getAllAuthors();
		}
		else if(selection == 3) {
			PublisherService.getAllPublishers();
		}
		else if(selection == 4) {
			menu();
		}
		else {
			System.out.println("Not a valid option! Try Again.");
			System.out.println();
			findAllOperations();
		}
	}
	
	public static void updateOperations() {
		System.out.println("What do you want to update?");
		System.out.println("1 - A book");
		System.out.println("2 - A author");
		System.out.println("3 - A publisher");
		System.out.println("4 - Return to Main Menu");
		
		selection = getSelection(4);
		
		if(selection == 1) {
			BookService.updateBook();
		}
		else if(selection == 2) {
			AuthorService.updateAuthor();
		}
		else if(selection == 3) {
			PublisherService.updatePublisher();
		}
		else if(selection == 4) {
			menu();
		}
		else {
			System.out.println("Not a valid option! Try Again.");
			System.out.println();
			updateOperations();
		}
	}
	
	public static void deleteOperations() {
		System.out.println("What do you want to delete?");
		System.out.println("1 - A book");
		System.out.println("2 - A author");
		System.out.println("3 - A publisher");
		System.out.println("4 - Return to Main Menu");
		
		selection = getSelection(4);
		
		if(selection == 1) {
			BookService.deleteBook();
		}
		else if(selection == 2) {
			AuthorService.deleteAuthor();
		}
		else if(selection == 3) {
			PublisherService.deletePublisher();
		}
		else if(selection == 4) {
			menu();
		}
		else {
			System.out.println("Not a valid option! Try Again.");
			System.out.println();
			deleteOperations();
		}
	}
	
	//takes in the number of options available and makes sure user input is between 1-options
	public static int getSelection(int options) {
		int input = 0;
		do {
			System.out.println("Please enter a number between 1 and " + options);
			try {
				input = Integer.parseInt(scan.nextLine());
			}catch(NumberFormatException e) {
				System.out.println("INVALID INPUT! Please choose an option from the menu.");
				input = 0;
			}
		}while(!(input > 0 && input <= options));
		return input;
	}
	
	public static void main(String[] args) {
//		System.out.println("Welcome to the library app!");
//		System.out.println();
//
//		menu();
		Result result = JUnitCore.runClasses(TestMenu.class);
		
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(result.wasSuccessful());
	}
}