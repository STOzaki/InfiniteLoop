package com.st.lms.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.st.lms.model.Author;
import com.st.lms.model.Book;
import com.st.lms.model.Publisher;
import com.st.lms.service.Service;

public class Menu {
	Scanner scan;
	private final int EXIT = 9;
	public Menu() {
		scan = new Scanner(System.in);
		run();
	}
	
	public void close() {//last method called in the application.
		System.out.println("Closing the app... Have a nice day!");
		scan.close();
	}
	
	private void run() { //called upon instantialization
		int option = MainMenu();
		switch(option) {
		case 1:
			BookMenu();
			break;
		case 2:
			AuthorMenu();
			break;
		case 3:
			PublisherMenu();
			break;
		case EXIT:
			break;
		default:
			break;
		}
	}
	
	private int MainMenu() {
		List<String> options = new ArrayList<String>();
		options.add("1) Manage Books");
		options.add("2) Manage Authors");
		options.add("3) Manage Publishers");
		options.add("" + EXIT + ") Exit");
		
		System.out.println("Main Menu. Please select one of the options below.");
		for(String option : options) {
			System.out.println(option);
		}
		return getValidOption(options.size());
	}
	
	private void BookMenu() {
		List<String> options = new ArrayList<String>();
		int bookID, authorID, publisherID, input;
		String bookTitle;
		options.add("1) Display current list of books");
		options.add("2) Add a new book");
		options.add("3) Remove a book");
		options.add("4) Search for a book");
		options.add("5) Update a book");
		options.add("" + EXIT + ") Return to main menu");
		
		do {
			System.out.println();
			System.out.println();
			System.out.println("Book Menu. Please select one of the options below.");
			for(String option : options) {
				System.out.println(option);
			}
			input = getValidOption(options.size());
			switch(input) {
			case 1:
				Service.printBooks();
				break;
			case 2:
				System.out.println("Please enter the book's id number.");
				bookID = getValidID();
				System.out.println("Please enter the book's title.");
				bookTitle = scan.nextLine();
				System.out.println("Please enter the book's author's id number.");
				authorID = getValidID();
				System.out.println("Please enter the book's publisher's id number.");
				publisherID = getValidID();
				String result = Service.addBook(bookID, bookTitle, authorID, publisherID);
				if(result.contains("a")) {
					System.out.println("The author does not exist in the database.");
					System.out.println("Please enter the author's name.");
					String authorName = scan.nextLine();
					Service.addAuthor(authorID, authorName);
				}
				if(result.contains("p")) {
					System.out.println("The publisher does not exist in the database.");
					System.out.println("Please enter the publisher's name.");
					String publisherName = scan.nextLine();
					System.out.println("Please enter the publisher's address.");
					String publisherAddr = scan.nextLine();
					System.out.println("Please enter the publisher's phone number.");
					String phone = getValidPhone();
					Service.addPublisher(publisherID, publisherName, publisherAddr, Long.parseLong(phone));
				}
				System.out.println("The database was successfully updated!");
				break;
			case 3:
				System.out.println("Please enter the id of the book you want to remove!");
				bookID = getValidID();
				Service.removeBook(bookID);
				System.out.println("The book was removed from the database!");
				break;
			case 4:
				System.out.println("Please enter the id number.");
				bookID = getValidID();
				Book book = Service.getBook(bookID);
				if(book == null)
					System.out.println("That book does not exist in the database.");
				else {
					System.out.println("Book ID: " + book.getID()
							+ "\nBook Title: " + book.getName()
							+ "\nBook's Author ID: " + book.getAuthorID()
							+ "\nBook's Publisher ID: " + book.getPublisherID());
				}
				break;
			case 5:
				List<String> updates = new ArrayList<String>();
				updates.add("1) Change book's title.");
				updates.add("2) Change book's author.");
				updates.add("3) Change book's publisher.");
				updates.add("9) Go back to Book Menu.");
				System.out.println("Please enter the id number of the book you would like to update.");
				bookID = getValidID();
				book = Service.getBook(bookID);
				if(book==null)
					System.out.println("That book does not exist in our records!");
				else {
					System.out.println("What would you like to change?");
					for(String update : updates)
						System.out.println(update);
					int temp = getValidOption(updates.size());
					switch(temp) {
						case 1:
							System.out.println("Please enter the book's new title.");
							bookTitle = scan.nextLine();
							book = new Book(bookTitle, bookID, book.getAuthorID(), book.getPublisherID());
							Service.updateBook(bookID, book);
							break;
						case 2:
							System.out.println("Please enter the book's new author's id number.");
							authorID = getValidID();
							book = new Book(book.getName(), bookID, authorID, book.getPublisherID());
							if(!Service.updateBook(bookID, book)) {
								System.out.println("That author does not exist in the database!");
								System.out.println("Please enter the author's name.");
								String authorName = scan.nextLine();
								Service.addAuthor(authorID, authorName);
							}
							break;
						case 3:
							System.out.println("Please enter the book's new publisher's id number.");
							publisherID = getValidID();
							book = new Book(book.getName(), bookID, book.getAuthorID(), publisherID);
							if(!Service.updateBook(bookID, book)) {
								System.out.println("That publisher does not exist in the database!");
								System.out.println("Please enter the publisher's name.");
								String publisherName = scan.nextLine();
								System.out.println("Please enter the publisher's address.");
								String publisherAddr = scan.nextLine();
								System.out.println("Please enter the publisher's phone number.");
								long phone = Long.parseLong(getValidPhone());
								Service.addPublisher(publisherID, publisherName, publisherAddr, phone);
							}
							break;
						default:
							break;
					}
				}
				break;
			case EXIT:
				System.out.println("Returning to main menu...");
				input = EXIT; //failsafe in case input was modified
				break;
			default:
				break;
			}
		}while(input != EXIT);
		run();
	}
	
	private void AuthorMenu() {
		List<String> options = new ArrayList<String>();
		int authorID, input;
		String authorName;
		options.add("1) Display current list of authors");
		options.add("2) Add a new author");
		options.add("3) Remove a author");
		options.add("4) Search for an author");
		options.add("5) Update an author");
		options.add("" + EXIT + ") Return to main menu");
		
		do {
			System.out.println();
			System.out.println();
			System.out.println("Author Menu. Please select one of the options below.");
			for(String option : options) {
				System.out.println(option);
			}
			input = getValidOption(options.size());
			
			switch(input) {
			case 1:
				Service.printAuthors();
				break;
			case 2:
				System.out.println("Please enter the author's id number.");
				authorID = getValidID();
				System.out.println("Please enter the author's name.");
				authorName = scan.nextLine();
				Service.addAuthor(authorID, authorName);
				System.out.println("The database was successfully updated!");
				break;
			case 3:
				System.out.println("Please enter the id of the book you want to remove!");
				authorID = getValidID();
				Service.removeAuthor(authorID);
				System.out.println("The book was removed from the database!");
				break;
			case 4:
				System.out.println("Please enter the id number.");
				authorID = getValidID();
				Author author = Service.getAuthor(authorID);
				if(author == null)
					System.out.println("The book's author does not exist in the database.");
				else {
					System.out.println("Author ID: " + author.getID()
							+ "\nAuthor's Name: " + author.getName());
				}
				break;
			case 5:
				System.out.println("Please enter the id number of the author you would like to update.");
				authorID = getValidID();
				author = Service.getAuthor(authorID);
				if(author==null)
					System.out.println("That author does not exist in our records!");
				else {
						System.out.println("Please enter the author's new name.");
						authorName = scan.nextLine();
						author = new Author(authorName, authorID);
						Service.updateAuthor(authorID, author);
					}
			case EXIT:
				System.out.println("Returning to main menu...");
				break;
			default:
				break;
			}
		}while(input != EXIT);
		run();
	}
	
	private void PublisherMenu() {
		List<String> options = new ArrayList<String>();
		int publisherID, input;
		String publisherName, publisherAddr;
		long publisherPhone;
		options.add("1) Display current list of publishers");
		options.add("2) Add a new publisher");
		options.add("3) Remove a publisher");
		options.add("4) Search for a publisher");
		options.add("5) Update a publisher");
		options.add("" + EXIT + ") Return to main menu");
		
		do {
			System.out.println();
			System.out.println();
			System.out.println("Publisher Menu. Please select one of the options below.");
			for(String option : options) {
				System.out.println(option);
			}
			input = getValidOption(options.size());
			
			switch(input) {
			case 1:
				Service.printPublishers();
				break;
			case 2:
				System.out.println("Please enter the publisher's id number.");
				publisherID = getValidID();
				System.out.println("Please enter the publisher's name.");
				publisherName = scan.nextLine();
				System.out.println("Please enter the publisher's address.");
				publisherAddr = scan.nextLine();
				System.out.println("Please enter the publisher's phone number.");
				publisherPhone = Long.parseLong(getValidPhone());
				Service.addPublisher(publisherID, publisherName, publisherAddr, publisherPhone);
				System.out.println("The database was successfully updated!");
				break;
			case 3:
				System.out.println("Please enter the id of the book you want to remove!");
				publisherID = getValidID();
				Service.removePublisher(publisherID);
				System.out.println("The book was removed from the database!");
				break;
			case 4:
				System.out.println("Please enter the id number.");
				publisherID = getValidID();
				Publisher publisher = Service.getPublisher(publisherID);
				if(publisher == null)
					System.out.println("That publisher does not exist in the database.");
				else {
					System.out.println("Publisher ID: " + publisher.getID()
							+ "\nPublisher's Name: " + publisher.getName()
							+ "\nPublisher's Address: " + publisher.getAddr()
							+ "\nPublisher's Phone: " + publisher.getPhone());
				}
				break;
			case 5:
				List<String> updates = new ArrayList<String>();
				updates.add("1) Change publisher's name.");
				updates.add("2) Change publisher's address.");
				updates.add("3) Change publisher's phone number.");
				updates.add("9) Go back to Publisher Menu.");
				System.out.println("Please enter the id number of the publisher you would like to update.");
				publisherID = getValidID();
				publisher = Service.getPublisher(publisherID);
				if(publisher==null)
					System.out.println("That publisher does not exist in our records!");
				else {
					System.out.println("What would you like to change?");
					for(String update : updates)
						System.out.println(update);
					int temp = getValidOption(updates.size());
					switch(temp) {
						case 1:
							System.out.println("Please enter the publisher's new name.");
							publisherName = scan.nextLine();
							publisher = new Publisher(publisherName, publisherID, publisher.getAddr(), publisher.getPhone());
							Service.updatePublisher(publisherID, publisher);
							break;
						case 2:
							System.out.println("Please enter the publisher's new address.");
							publisherAddr = scan.nextLine();
							publisher = new Publisher(publisher.getName(), publisherID, publisherAddr, publisher.getPhone());
							Service.updatePublisher(publisherID, publisher);
							break;
						case 3:
							System.out.println("Please enter the publisher's new phone number.");
							publisherPhone = Long.parseLong(getValidPhone());
							publisher = new Publisher(publisher.getName(), publisherID, publisher.getAddr(), publisherPhone);
							Service.updatePublisher(publisherID, publisher);
							break;
						default:
							break;
					}
				}
			case EXIT:
				System.out.println("Returning to main menu...");
				break;
			default:
				break;
			}
		}while(input != EXIT);
		run();
	}
	
	private int getValidOption(int options) {//prompts user for a number less than options-1 or 9 for exit
		System.out.println("Which option would you like to choose?");
		String input;
		int option;
		do {
			input = scan.nextLine();
			try {
				option = Integer.parseInt(input);
				if((option!=9) && (option<1 || option>(options-1))) {
					option = 0;
					System.out.println("ERROR: That is not one of the options listed!");
				}
			}catch(NumberFormatException e) {
				System.out.println("ERROR: Please enter a number from the options available!");
				option = 0;
			}
		}while(option == 0);
		return option;
	}
	
	private int getValidID() { //prompts user until it receives a positive id number
		String input;
		int id;
		
		do {
			input = scan.nextLine();
			try {
				id = Integer.parseInt(input);
			}catch(NumberFormatException e){
				id = 0;
				System.out.println("ERROR: invalid id number. Please enter a positive integer.");
			}
		}while(!(id > 0));
		return id;
	}
	
	private String getValidPhone() {
		String phone = "";
		do {
			phone = scan.nextLine();
			try {
				Long.parseLong(phone);
			}catch(NumberFormatException e){
				phone = "";
				System.out.println("Error: invalid phone number. Please enter a valid phone number!");
			}
			if(phone.length() != 10)
				System.out.println("Error: please enter the full 10-digit phone number!");
		}while(phone.length()!=10); //phone numbers are 10 digits
		return phone;
	}
}
