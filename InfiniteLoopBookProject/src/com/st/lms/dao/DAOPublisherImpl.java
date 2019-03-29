package com.st.lms.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import com.st.lms.model.Publisher;

public class DAOPublisherImpl implements DAOPublisher {
	//paths to csv database and tempfile used when rewriting
	private final String PublisherFile = "./resources/Publisher.csv";
	private final String PublisherTemp = "./resources/PublisherTemp.csv";
	
	//values used when dividing each line of csv into different parts. number correlates to order
	private final int ID = 0;
	private final int NAME = 1;
	private final int ADDRESS = 2;
	private final int PHONE = 3;

	@Override
	public List<Publisher> loadPublishers() throws IOException {
		FileInputStream fin = new FileInputStream(PublisherFile);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
		List<Publisher> publishers = new ArrayList<Publisher>();
		String publisherLine;
		
		while((publisherLine = buffReader.readLine()) != null) {
			String[] parts = publisherLine.split(",");
			publishers.add(new Publisher(CommaDecode(parts[NAME]), Integer.parseInt(parts[ID]), CommaDecode(parts[ADDRESS]), Long.parseLong(parts[PHONE])));
		}
		buffReader.close();
		return publishers;
	}

	@Override
	public Publisher getPublisher(int id) throws IOException {
		FileInputStream fin = new FileInputStream(PublisherFile);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
		String publisherLine;
		Publisher publisher = null;
		
		while((publisherLine = buffReader.readLine()) != null) {
			String[] parts = publisherLine.split(",");
			if(Integer.parseInt(parts[ID]) == id) {
				publisher = new Publisher(CommaDecode(parts[NAME]), Integer.parseInt(parts[ID]), CommaDecode(parts[ADDRESS]), Long.parseLong(parts[PHONE]));
			}
		}
		buffReader.close();
		return publisher;
	}
	

	@Override
	public void addPublisher(Publisher publisher) throws IOException {
		String output = "" + publisher.getID() + "," + CommaEncode(publisher.getName()) + "," + CommaEncode(publisher.getAddr()) + "," + publisher.getPhone() + "\n";
		Files.write(Paths.get(PublisherFile), output.getBytes(), StandardOpenOption.APPEND);
	}
	
	@Override
	public void replace(int id, Publisher publisher) throws IOException {
		FileInputStream fin = new FileInputStream(PublisherFile);
		FileWriter fw = new FileWriter(PublisherTemp, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter fout = new PrintWriter(bw);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
		String publisherLine;
		while((publisherLine = buffReader.readLine()) != null) {
			String[] parts = publisherLine.split(",");
			if(Integer.parseInt(parts[ID]) == id) {
				publisherLine = "" + publisher.getID() + ','
						+ CommaEncode(publisher.getName()) + ','
						+ CommaEncode(publisher.getAddr()) + ','
						+ publisher.getPhone();
			}
			fout.println(publisherLine);
		}
		buffReader.close();
		fout.close();
		File oldFile = new File(PublisherFile);
		oldFile.delete();
		File newFile = new File(PublisherTemp);
		newFile.renameTo(oldFile);
	}
	
	@Override
	public boolean hasPublisher(int id) throws IOException {
		FileInputStream fin = new FileInputStream(PublisherFile);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
		String publisherLine;
		
		while((publisherLine = buffReader.readLine()) != null) {
			String[] parts = publisherLine.split(",");
			if(Integer.parseInt(parts[ID]) == id) {
				buffReader.close();
				return true;
			}
		}
		buffReader.close();
		return false;
	}


	@Override
	public void delete(int id) throws IOException{
		FileInputStream fin = new FileInputStream(PublisherFile);
		FileWriter fw = new FileWriter(PublisherTemp, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter fout = new PrintWriter(bw);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
		String publisherLine;
		while((publisherLine = buffReader.readLine()) != null) {
			String[] parts = publisherLine.split(",");
			if(Integer.parseInt(parts[ID]) != id) {
				fout.println(publisherLine);
			}
		}
		buffReader.close();
		fout.close();
		File oldFile = new File(PublisherFile);
		oldFile.delete();
		File newFile = new File(PublisherTemp);
		newFile.renameTo(oldFile);
	}

	@Override
	public void setName(int id, String name) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setID(int oldID, int newID) throws IOException {
		// TODO Auto-generated method stub
		
	}

	

}
