package daoImplementations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import daoInterface.BookDao;
import pojos.Book;

public class BookDaoImpl implements BookDao {
	
	File csvFile = new File("./database/Books.csv");
	
	@Override
	public void addBook(Book book) {	
		try {
			FileWriter fw = new FileWriter(csvFile, true);
			
			//u can only write Strings to a .csv
			fw.write(Integer.toString(book.getBookId()) + ",");
			fw.write(book.getTitle() + ",");
			fw.write(Integer.toString(book.getBookId()) + ",");
			fw.write(Integer.toString(book.getPublisherId()));
			fw.write("\n");
			fw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Book getBook(int bookId) {
		Book book = new Book();
		
		try {
			Scanner scan = new Scanner(csvFile);
			while(scan.hasNextLine()) {
				String authorLine = scan.nextLine();
				String[] strArray = authorLine.split(",");
				
				if(strArray[0].equals(Integer.toString(bookId))) {
					book.setTitle(strArray[1]);
					book.setAuthorId(Integer.parseInt(strArray[2]));
					book.setPublisherId(Integer.parseInt(strArray[3]));
					break;
				}
			}
			scan.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return book;
	}

	@Override
	public ArrayList<Book> getAllBooks() {
		ArrayList<Book> books = new ArrayList<>();
		
		try {
			Scanner scan = new Scanner(csvFile);
			scan.nextLine(); //skip column titles
			
			//get all books and add to an arraylist
			while(scan.hasNextLine()) {
				String authorLine = scan.nextLine();
				String[] strArray = authorLine.split(",");
				
				Book book = new Book(
					Integer.parseInt(strArray[0]),
					strArray[1],
					Integer.parseInt(strArray[2]),
					Integer.parseInt(strArray[3])
				);
				
				books.add(book);
			}
			scan.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return books;
	}

	@Override
	public void updateBook(Book book) {
		File file1 = csvFile;
		File file2 = new File("./database/BooksTemp.csv");
		
		try {
			Scanner scan = new Scanner(file1);
			FileWriter fw = new FileWriter(file2);
			
			//first line can be written without any checks
			String bookLine = scan.nextLine();
			fw.write(bookLine);
			fw.write("\n");
			
			//2nd line and rest
			while(scan.hasNextLine()) {
				bookLine = scan.nextLine();
				String[] strArray = bookLine.split(",");
				
				if(Integer.parseInt(strArray[0]) == book.getBookId()) {
					fw.write(book.getBookId() + ",");
					fw.write(book.getTitle() + ",");
					fw.write(strArray[2] + ",");
					fw.write(strArray[3]);
					fw.write("\n");
				}
				else {
					fw.write(bookLine);
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
	public void deleteBook(Book book) {
		File file1 = csvFile;
		File file2 = new File("./database/BooksTemp.csv");
		
		try {
			Scanner scan = new Scanner(file1);
			FileWriter fw = new FileWriter(file2);
			
			//first line can be written without any checks
			String bookLine = scan.nextLine();
			fw.write(bookLine);
			fw.write("\n");
			
			//2nd line and rest
			while(scan.hasNextLine()) {
				bookLine = scan.nextLine();
				String[] strArray = bookLine.split(",");
				
				if(Integer.parseInt(strArray[0]) == book.getBookId()) {
					//do nothing, don't write the line to temp file
				}
				else {
					fw.write(bookLine);
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