package com.st.lms.dao;

import java.util.ArrayList;
import java.util.List;

import com.st.lms.model.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DAOAuthorImpl implements DAOAuthor{
	//paths to the csv database and temp used to rewrite
	private final String AuthorFile = "./resources/Author.csv";
	private final String AuthorTemp = "./resources/AuthorTemp.csv";
	
	//values used when dividing each line into parts. value is set to the corresponding part
		private final int ID = 0;
		private final int NAME = 1;

	@Override
	public List<Author> loadAuthors() throws IOException {
		FileInputStream fin = new FileInputStream(AuthorFile);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
		List<Author> authors = new ArrayList<Author>();
		String authorLine;
		while((authorLine = buffReader.readLine()) != null) {
			String[] parts = authorLine.split(",");
			Author author = new Author(CommaDecode(parts[NAME]), Integer.parseInt(parts[ID]));
			authors.add(author);
		}
		buffReader.close();
		return authors;
	}
	
	@Override
	public Author getAuthor(int id) throws IOException { //returns null if given id not in table
		FileInputStream fin = new FileInputStream(AuthorFile);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
		String authorLine;
		Author author = null;
		
		while((authorLine = buffReader.readLine()) != null) {
			String[] parts = authorLine.split(",");
			if(Integer.parseInt(parts[ID]) == id) {
				author = new Author(CommaDecode(parts[NAME]), Integer.parseInt(parts[ID]));
			}
		}
		buffReader.close();
		return author;
	}

	@Override
	public void addAuthor(Author author) throws IOException {
		String output = "" + author.getID() + "," + CommaEncode(author.getName()) + "\n";
		Files.write(Paths.get(AuthorFile), output.getBytes(), StandardOpenOption.APPEND);
	}
	
	@Override
	public void replace(int id, Author author) throws IOException {
		FileInputStream fin = new FileInputStream(AuthorFile);
		FileWriter fw = new FileWriter(AuthorTemp, true);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter fout = new PrintWriter(bw);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
		String bookLine;
		while((bookLine = buffReader.readLine()) != null) {
			String[] parts = bookLine.split(",");
			if(Integer.parseInt(parts[ID]) == id) {
				bookLine = "" + author.getID() + ','
						+ CommaEncode(author.getName());
			}
			fout.println(bookLine);
		}
		buffReader.close();
		fout.close();
		File oldFile = new File(AuthorFile);
		oldFile.delete();
		File newFile = new File(AuthorTemp);
		newFile.renameTo(oldFile);
	}
	
	@Override
	public boolean hasAuthor(int id) throws IOException {
		FileInputStream fin = new FileInputStream(AuthorFile);
		BufferedReader buffReader = new BufferedReader(new InputStreamReader(fin));
		String authorLine;
		
		while((authorLine = buffReader.readLine()) != null) {
			String[] parts = authorLine.split(",");
			if(Integer.parseInt(parts[ID]) == id) {
				buffReader.close();
				return true;
			}
		}
		buffReader.close();
		return false;
	}
	
	@Override
	public void delete(int id) throws IOException {
		FileInputStream fin = new FileInputStream(AuthorFile);
		FileWriter fw = new FileWriter(AuthorTemp, true);
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
		File oldFile = new File(AuthorFile);
		oldFile.delete();
		File newFile = new File(AuthorTemp);
		newFile.renameTo(oldFile);
	}
}

	
