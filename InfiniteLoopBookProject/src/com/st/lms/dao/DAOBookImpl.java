package com.st.lms.dao;

import java.util.ArrayList;
import java.util.List;

import com.st.lms.model.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DAOBookImpl implements DAOBook{
	//paths to the csv database and temp used to rewrite
	private final String BookFile = "./resources/Book.csv";
	private final String BookTemp = "./resources/BookTemp.csv";
	
	//values used when dividing each line into parts. value is set to the corresponding part
	private final int ID = 0;
	private final int TITLE = 1;
	private final int AUTHOR = 2;
	private final int PUBLISHER = 3;
	
	@Override
	public List<Book> loadBooks() throws IOException {
		FileInputStream fin = new FileInputStream(BookFile);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
		List<Book> books = new ArrayList<Book>();
		String bookLine;
		while((bookLine = buffReader.readLine()) != null) {
			String[] parts = bookLine.split(",");
			books.add(new Book(CommaDecode(parts[TITLE]), Integer.parseInt(parts[ID]), Integer.parseInt(parts[AUTHOR]), Integer.parseInt(parts[PUBLISHER])));
		}
		buffReader.close();
		return books;
	}

	@Override
	public Book getBook(int id) throws IOException{ //returns null if no book with given id exists
		FileInputStream fin = new FileInputStream(BookFile);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
		String bookLine;
		Book book = null;
		
		while((bookLine = buffReader.readLine()) != null) {
			String[] parts = bookLine.split(",");
			if(Integer.parseInt(parts[ID]) == id) {
				book = new Book(CommaDecode(parts[TITLE]), Integer.parseInt(parts[ID]), Integer.parseInt(parts[AUTHOR]), Integer.parseInt(parts[PUBLISHER]));
				break;
			}
		}
		buffReader.close();
		return book;
	}

	@Override
	public void addBook(Book book) throws IOException { //appends data of given book to the table
		String output = "" + book.getID() + "," + CommaEncode(book.getName()) + "," + book.getAuthorID() + "," + book.getPublisherID() + "\n";
		Files.write(Paths.get(BookFile), output.getBytes(), StandardOpenOption.APPEND);
	}
	
	@Override
	public void setName(int id, String name) throws IOException {
		FileInputStream fin = new FileInputStream(BookFile);
		FileWriter fw = new FileWriter(BookTemp, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter fout = new PrintWriter(bw);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
		String bookLine;
		while((bookLine = buffReader.readLine()) != null) {
			String[] parts = bookLine.split(",");
			if(Integer.parseInt(parts[ID]) == id) {
				bookLine = bookLine.replace(parts[TITLE], CommaEncode(name));
			}
			fout.println(bookLine);
		}
		buffReader.close();
		fout.close();
		File oldFile = new File(BookFile);
		oldFile.delete();
		File newFile = new File(BookTemp);
		newFile.renameTo(oldFile);
		
	}
	
	@Override
	public void replace(int id, Book book) throws IOException {
		FileInputStream fin = new FileInputStream(BookFile);
		FileWriter fw = new FileWriter(BookTemp, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter fout = new PrintWriter(bw);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
		String bookLine;
		while((bookLine = buffReader.readLine()) != null) {
			String[] parts = bookLine.split(",");
			if(Integer.parseInt(parts[ID]) == id) {
				bookLine = "" + book.getID() + ','
						+ CommaEncode(book.getName()) + ','
						+ book.getAuthorID() + ','
						+ book.getPublisherID();
			}
			fout.println(bookLine);
		}
		buffReader.close();
		fout.close();
		File oldFile = new File(BookFile);
		oldFile.delete();
		File newFile = new File(BookTemp);
		newFile.renameTo(oldFile);
	}

	@Override
	public void setID(int oldID, int newID) throws IOException{
		FileInputStream fin = new FileInputStream(BookFile);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
		FileOutputStream fout = new FileOutputStream(BookTemp);
		BufferedWriter buffWriter = new BufferedWriter(new OutputStreamWriter(fout));
		
		String bookLine;
		
		while((bookLine = buffReader.readLine()) != null) {
			String[] parts = bookLine.split(",");
			if(Integer.parseInt(parts[ID]) != oldID) {
				buffWriter.append(bookLine);
			}
			else {
				bookLine = bookLine.replace(parts[ID], "" + newID);
				buffWriter.append(bookLine);
			}
		}
		buffReader.close();
		buffWriter.close();
		File oldFile = new File(BookFile);
		oldFile.delete();
		File newFile = new File(BookTemp);
		newFile.renameTo(oldFile);
	}

	@Override
	public void delete(int id) throws IOException{
		FileInputStream fin = new FileInputStream(BookFile);
		FileWriter fw = new FileWriter(BookTemp, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter fout = new PrintWriter(bw);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
		String bookLine;
		while((bookLine = buffReader.readLine()) != null) {
			String[] parts = bookLine.split(",");
			if(Integer.parseInt(parts[ID]) != id) {
				fout.println(bookLine);
			}
		}
		buffReader.close();
		fout.close();
		File oldFile = new File(BookFile);
		oldFile.delete();
		File newFile = new File(BookTemp);
		newFile.renameTo(oldFile);
	}
	
	@Override
	public boolean hasBook(int id) throws IOException{
		FileInputStream fin = new FileInputStream(BookFile);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
		String bookLine;
		
		while((bookLine = buffReader.readLine()) != null) {
			String[] parts = bookLine.split(",");
			if(Integer.parseInt(parts[ID]) == id) {
				buffReader.close();
				return true;
			}
		}
		buffReader.close();
		return false;
	}

	@Override
	public void removePublisher(int publisherID) throws IOException {
		FileInputStream fin = new FileInputStream(BookFile);
		FileWriter fw = new FileWriter(BookTemp, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter fout = new PrintWriter(bw);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
		String bookLine;
		while((bookLine = buffReader.readLine()) != null) {
			String[] parts = bookLine.split(",");
			if(Integer.parseInt(parts[PUBLISHER]) != publisherID) {
				fout.println(bookLine);
			}
		}
		buffReader.close();
		fout.close();
		File oldFile = new File(BookFile);
		oldFile.delete();
		File newFile = new File(BookTemp);
		newFile.renameTo(oldFile);
	}

	@Override
	public void removeAuthor(int authorID) throws IOException {
		FileInputStream fin = new FileInputStream(BookFile);
		FileWriter fw = new FileWriter(BookTemp, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter fout = new PrintWriter(bw);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
		String bookLine;
		while((bookLine = buffReader.readLine()) != null) {
			String[] parts = bookLine.split(",");
			if(Integer.parseInt(parts[AUTHOR]) != authorID) {
				fout.println(bookLine);
			}
		}
		buffReader.close();
		fout.close();
		File oldFile = new File(BookFile);
		oldFile.delete();
		File newFile = new File(BookTemp);
		newFile.renameTo(oldFile);
	}
}
	
	
	