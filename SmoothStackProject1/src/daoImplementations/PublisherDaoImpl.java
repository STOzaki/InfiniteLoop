package daoImplementations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import daoInterface.PublisherDao;
import pojos.Publisher;

public class PublisherDaoImpl implements PublisherDao {

	File csvFile = new File("./database/Publishers.csv");
	
	@Override
	public void addPublisher(Publisher publisher) {
		try {
			FileWriter fw = new FileWriter(csvFile, true);
			
			//u can only write Strings to a .csv
			fw.write(Integer.toString(publisher.getPublisherId()) + ",");
			fw.write(publisher.getPublisherName() + ",");
			fw.write(publisher.getPublisherAddress() + ",");
			fw.write(publisher.getPublisherPhone());
			fw.write("\n");
			fw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Publisher getPublisher(int publisherId) {
		Publisher publisher = new Publisher();
		
		try {
			Scanner scan = new Scanner(csvFile);
			while(scan.hasNextLine()) {
				String authorLine = scan.nextLine();
				String[] strArray = authorLine.split(",");
				
				if(strArray[0].equals(Integer.toString(publisherId))) {
					publisher.setPublisherName(strArray[1]);
					publisher.setPublisherAddress(strArray[2]);
					publisher.setPublisherPhone(strArray[3]);
					break;
				}
			}
			scan.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return publisher;
	}

	@Override
	public ArrayList<Publisher> getAllPublishers() {
		ArrayList<Publisher> publishers = new ArrayList<>();
		
		try {
			Scanner scan = new Scanner(csvFile);
			scan.nextLine(); //skip column titles
			
			//get all publishers and add to an arraylist
			while(scan.hasNextLine()) {
				String publisherLine = scan.nextLine();
				String[] strArray = publisherLine.split(",");
				
				Publisher publisher = new Publisher(
					Integer.parseInt(strArray[0]),
					strArray[1],
					strArray[2],
					strArray[3]
				);
				
				publishers.add(publisher);
			}
			scan.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return publishers;
	}

	@Override
	public void updatePublisher(Publisher publisher) {
		File file1 = csvFile;
		File file2 = new File("./database/PublishersTemp.csv");
		
		try {
			Scanner scan = new Scanner(file1);
			FileWriter fw = new FileWriter(file2);
			
			//first line can be written without any checks
			String publisherLine = scan.nextLine();
			fw.write(publisherLine);
			fw.write("\n");
			
			//2nd line and rest
			while(scan.hasNextLine()) {
				publisherLine = scan.nextLine();
				String[] strArray = publisherLine.split(",");
				
				if(Integer.parseInt(strArray[0]) == publisher.getPublisherId()) {
					fw.write(publisher.getPublisherId() + ",");
					fw.write(publisher.getPublisherName() + ",");
					fw.write(publisher.getPublisherAddress() + ",");
					fw.write(publisher.getPublisherPhone());
					fw.write("\n");
				}
				else {
					fw.write(publisherLine);
					fw.write("\n");
				}
			}
			scan.close();
			fw.close();
			
			file1.delete();        //delete old file
			file2.renameTo(file1); //rename new file to old file
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void deletePublisher(Publisher publisher) {
		File file1 = csvFile;
		File file2 = new File("./database/PublishersTemp.csv");
		
		try {
			Scanner scan = new Scanner(file1);
			FileWriter fw = new FileWriter(file2);
			
			//first line can be written without any checks
			String publisherLine = scan.nextLine();
			fw.write(publisherLine);
			fw.write("\n");
			
			//2nd line and rest
			while(scan.hasNextLine()) {
				publisherLine = scan.nextLine();
				String[] strArray = publisherLine.split(",");
				
				if(Integer.parseInt(strArray[0]) == publisher.getPublisherId()) {
					//do nothing, don't write the line to temp file
				}
				else {
					fw.write(publisherLine);
					fw.write("\n");
				}
			}
			scan.close();
			fw.close();
			
			file1.delete();        //delete old file
			file2.renameTo(file1); //rename new file to old file
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}