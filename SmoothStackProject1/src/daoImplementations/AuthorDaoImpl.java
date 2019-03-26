package daoImplementations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import daoInterface.AuthorDao;
import pojos.Author;

public class AuthorDaoImpl implements AuthorDao {

	File csvFile = new File("./database/Authors.csv");
	
	//ok to save
	@Override
	public void addAuthor(Author author) {	
		try {
			FileWriter fw = new FileWriter(csvFile, true);
			
			//u can only write Strings to a .csv
			fw.write(Integer.toString(author.getAuthorId()));
			fw.write(",");
			fw.write(author.getAuthorName());
			fw.write("\n");
			fw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Author getAuthor(int authorId) {
		Author author = new Author();
		
		try {
			Scanner scan = new Scanner(csvFile);
			while(scan.hasNextLine()) {
				String authorLine = scan.nextLine();
				String[] strArray = authorLine.split(",");
				
				if(strArray[0].equals(Integer.toString(authorId))) {
					author.setAuthorName(strArray[1]);
					break;
				}
			}
			scan.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return author;
	}
	
	@Override
	public ArrayList<Author> getAllAuthors() {
		ArrayList<Author> authors = new ArrayList<>();
		
		try {
			Scanner scan = new Scanner(csvFile);
			scan.nextLine(); //skip column titles
			
			//get all authors and add to an arraylist
			while(scan.hasNextLine()) {
				String authorLine = scan.nextLine();
				String[] strArray = authorLine.split(",");
				
				Author author = new Author(
					Integer.parseInt(strArray[0]),
					strArray[1]
				);
				
				authors.add(author);
			}
			scan.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return authors;
	}

	@Override
	public void updateAuthor(Author author) {
		File file1 = csvFile;
		File file2 = new File("./database/AuthorsTemp.csv");
		
		try {
			Scanner scan = new Scanner(file1);
			FileWriter fw = new FileWriter(file2);
			
			//first line can be written without any checks
			String authorLine = scan.nextLine();
			fw.write(authorLine);
			fw.write("\n");
			
			//2nd line and rest
			while(scan.hasNextLine()) {
				authorLine = scan.nextLine();
				String[] strArray = authorLine.split(",");
				
				if(Integer.parseInt(strArray[0]) == author.getAuthorId()) {
					fw.write(author.getAuthorId() + "," + author.getAuthorName() + "\n");
				}
				else {
					fw.write(authorLine);
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
	public void deleteAuthor(Author author) {
		File file1 = csvFile;
		File file2 = new File("./database/AuthorsTemp.csv");
		
		try {
			Scanner scan = new Scanner(file1);
			FileWriter fw = new FileWriter(file2);
			
			//first line can be written without any checks
			String authorLine = scan.nextLine();
			fw.write(authorLine);
			fw.write("\n");
			
			//2nd line and rest
			while(scan.hasNextLine()) {
				authorLine = scan.nextLine();
				String[] strArray = authorLine.split(",");
				
				if(Integer.parseInt(strArray[0]) == author.getAuthorId()) {
					//do nothing, don't write the line to temp file
				}
				else {
					fw.write(authorLine);
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