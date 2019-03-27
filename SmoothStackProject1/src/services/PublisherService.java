package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import daoImplementations.BookDaoImpl;
import daoImplementations.PublisherDaoImpl;
import daoInterface.BookDao;
import daoInterface.PublisherDao;
import driver.Main;
import pojos.Book;
import pojos.Publisher;
import utils.Validate;

public class PublisherService {
	
	private static PublisherDao publisherDao = new PublisherDaoImpl();
	private static BookDao bookDao = new BookDaoImpl();
	static File csvBookFile = new File("./database/Books.csv");
	static Scanner scan = new Scanner(System.in);
	
	public static boolean checkPublisherExists(int id) {
		Publisher publisher = publisherDao.getPublisher(id);
		
		if(publisher.getPublisherName() == null)
			return false;
		else
			return true;
	}
	
	public static void addPublisher() {
		int publisherId;
		String publisherName, publisherAddress, publisherPhone;
		boolean publisherExists, isValidPhoneNum;
		
		System.out.print("Enter publisher id: ");
		publisherId = scan.nextInt();
		publisherExists = checkPublisherExists(publisherId);
		
		if(publisherExists) {
			System.out.println("Publisher id already exists!");
			Main.menu();
		}
		else {
			//fixes issue of nextInt not consuming /n
			scan.nextLine();
			
			System.out.print("Enter publisher name: ");
			publisherName = scan.nextLine();
			
			System.out.print("Enter publisher address: ");
			publisherAddress = scan.nextLine();
			
			System.out.print("Enter publisher phone xxx-xxx-xxxx: ");
			publisherPhone = scan.nextLine();
			isValidPhoneNum = Validate.checkValidPhoneNum(publisherPhone);
			if(!isValidPhoneNum) {
				System.out.println("Phone number doesn't match format xxx-xxx-xxxx! \n");
				Main.addOperations();
			}
			
			Publisher publisher = new Publisher(publisherId, publisherName, publisherAddress, publisherPhone);
			publisherDao.addPublisher(publisher);
			System.out.println("Publisher Saved!");
			Main.menu();
		}
	}
	
	public static void getPublisher() {
		int publisherId;
		
		System.out.print("What is the publisher id: ");
		publisherId = scan.nextInt();
		Publisher publisher = publisherDao.getPublisher(publisherId);
		
		if(publisher.getPublisherName() == null) {
			System.out.println("Publisher id not found!");
			Main.menu();
		}
		else {
			System.out.println("Name: " + publisher.getPublisherName());
			System.out.println("Address: " + publisher.getPublisherAddress());
			System.out.println("Phone: " + publisher.getPublisherPhone());
			Main.menu();
		}
	}
	
	public static void getAllPublishers() {
		ArrayList<Publisher> publishers = publisherDao.getAllPublishers();
		
		System.out.println(String.format("%-15s %-5s %-15s %-5s %-35s %-5s %-15s", "PublisherId", "|", "Name", "|", "Address", "|", "Phone Number"));
		System.out.println(String.format("%s", "------------------------------------------------------------------------------------------------------"));
		 
		for(int i=0; i<publishers.size(); i++) {
			System.out.println(String.format("%-15s %-5s %-15s %-5s %-35s %-5s %-15s", 
				publishers.get(i).getPublisherId(), 
				"|", 
				publishers.get(i).getPublisherName(), 
				"|", 
				publishers.get(i).getPublisherAddress(),
				"|",
				publishers.get(i).getPublisherPhone())
			);
		}
		Main.menu();
	}
	
	public static void updatePublisher() {
		int publisherId;
		String publisherName, publisherAddress, publisherPhone;
		boolean publisherExists, isValidPhoneNum;

		System.out.print("What is the publisher id: ");
		publisherId = scan.nextInt();
		publisherExists = checkPublisherExists(publisherId);
		
		//assume user will always update all 3 publisher fields
		if(publisherExists) {
			//fixes issue of nextInt not consuming /n
			scan.nextLine();
			
			System.out.print("Update publisher name to: ");
			publisherName = scan.nextLine();
			
			System.out.print("Update publisher address to: ");
			publisherAddress = scan.nextLine();
			
			System.out.print("Update publisher phone xxx-xxx-xxxx: ");
			publisherPhone = scan.nextLine();
			isValidPhoneNum = Validate.checkValidPhoneNum(publisherPhone);
			if(!isValidPhoneNum) {
				System.out.println("Phone number doesn't match format xxx-xxx-xxxx! \n");
				Main.updateOperations();
			}
			
			Publisher publisher = new Publisher(publisherId, publisherName, publisherAddress, publisherPhone);
			publisherDao.updatePublisher(publisher);
			System.out.println("Publisher updated!");
			Main.menu();
		}
		else {
			//user starts over again if publisherId not found
			System.out.println("Publisher id not found!");
			Main.menu();
		}
	}
	
	//when deleting a publisher, need to delete all linked books as well
	public static void deletePublisher() {
		int publisherId;
		boolean publisherExists;
		ArrayList<Integer> matchedBookIds = new ArrayList<>();
		
		System.out.print("What is the publisher id: ");
		publisherId = scan.nextInt();
		publisherExists = checkPublisherExists(publisherId);
		
		if(publisherExists) {
			//delete publisher
			Publisher publisher = new Publisher();
			publisher.setPublisherId(publisherId);
			publisherDao.deletePublisher(publisher);
			
			//find the bookIds linked to publisherId and add to an ArrayList
			try {
				Scanner scan = new Scanner(csvBookFile);
				while(scan.hasNextLine()) {
					String bookLine = scan.nextLine();
					String[] strArray = bookLine.split(",");
					
					if(strArray[3].equals(Integer.toString(publisherId))) {
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
			
			System.out.println("Publisher deleted (along with their associated books)!");
			Main.menu();
		}
		else {
			System.out.println("Publisher id not found!");
			Main.menu();
		}
	}
}