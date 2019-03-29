import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.st.lms.dao.DAOAuthorImpl;
import com.st.lms.dao.DAOBookImpl;
import com.st.lms.dao.DAOPublisherImpl;
import com.st.lms.model.Author;
import com.st.lms.model.Book;
import com.st.lms.model.Publisher;

public class TestDAO {
	
	@BeforeClass
	public static void setUp() throws IOException{
		AuthorDaoTest.setUp();
		BookDaoTest.setUp();
		PublisherDaoTest.setUp();
	}
	
	@AfterClass
	public static void tearDown() throws IOException{
		AuthorDaoTest.tearDown();;
		BookDaoTest.tearDown();;
		PublisherDaoTest.tearDown();;
	}
	
	public static void testGet() throws IOException{
		AuthorDaoTest.testGet();
		BookDaoTest.testGet();
		PublisherDaoTest.testGet();
	}
	
	public static void testSet() throws IOException{
		AuthorDaoTest.testSet();
		BookDaoTest.testSet();
		PublisherDaoTest.testSet();
	}
	
	public static void testAdd() throws IOException{
		AuthorDaoTest.testAdd();
		BookDaoTest.testAdd();
		PublisherDaoTest.testAdd();
	}
	
	public static void testDelete() throws IOException{
		AuthorDaoTest.testDelete();
		BookDaoTest.testDelete();
		PublisherDaoTest.testDelete();
	}
	
	static class AuthorDaoTest{
		public static void setUp() throws IOException{
			DAOAuthorImpl aDao = new DAOAuthorImpl();
			Author author = new Author();
			author.setName("JK Rowling");
			author.setID(2001);
			aDao.addAuthor(author);
			author.setName("Masashi Kishimoto");
			author.setID(2003);
			aDao.addAuthor(author);
			author.setName("George RR Martin");
			author.setID(2005);
			aDao.addAuthor(author);
		}
		
		public static void tearDown() throws IOException{
			DAOAuthorImpl aDao = new DAOAuthorImpl();
			aDao.delete(2001);
			aDao.delete(2005);
			aDao.delete(2020);
		}
		
		public static void testGet() throws IOException {
			DAOAuthorImpl dao = new DAOAuthorImpl();
			assertTrue(dao.getAuthor(2001) != null);
			Author author = dao.getAuthor(2001);
			assertTrue(dao.getAuthor(5000) == null);
			assertTrue(dao.getAuthor(2001).getName().equals("JK Rowling"));
			assertTrue(dao.getAuthor(2001).getID() == 2001);
			
		}
		
		public static void testAdd() throws IOException{
			DAOAuthorImpl dao = new DAOAuthorImpl();
			Author author = new Author();
			author.setID(2020);
			author.setName("Joanne Rowling");
			dao.addAuthor(author);
			author = dao.getAuthor(2020);
			assertTrue(author != null);
			assertTrue(author.getID() == 2020);
			assertTrue(author.getName().equals("Joanne Rowling"));
		}
		
		public static void testSet() throws IOException{
			DAOAuthorImpl dao = new DAOAuthorImpl();
			Author author;
			author = dao.getAuthor(2005);
			author.setName("Edit");
			dao.replace(2005, author);
			author = dao.getAuthor(2005);
			assertTrue((author = dao.getAuthor(2005)) != null);
			assertTrue(author.getID() == 2005);
			assertTrue(author.getName().equals("Edit"));
			
		}
		
		public static void testDelete() throws IOException{
			DAOAuthorImpl dao = new DAOAuthorImpl();
			Author author = new Author();
			author = dao.getAuthor(2003); 	
			dao.delete(2003); 
			
			assertFalse(dao.loadAuthors().stream()
							.anyMatch(e -> e.getName().compareTo("Masashi Kishimoto") == 0));
			assertFalse(dao.loadAuthors().stream()
					.anyMatch(e -> e.getID() == 2003));
		}
	}
	
	static class BookDaoTest{
		public static void setUp() throws IOException{
			DAOBookImpl bDao = new DAOBookImpl();
			Book book = new Book();
			book.setAuthorID(2001);
			book.setID(1001);
			book.setName("Harry Potter");
			book.setPublisherID(3001);
			bDao.addBook(book);
			book.setAuthorID(2003);
			book.setID(1005);
			book.setName("Naruto");
			book.setPublisherID(3005);
			bDao.addBook(book);
		}
		
		public static void tearDown() throws IOException{
			DAOBookImpl dao = new DAOBookImpl();
			dao.delete(1001);
			dao.delete(1020);
		}
		
		public static void testGet() throws IOException {
			DAOBookImpl dao = new DAOBookImpl();
			assertTrue(dao.getBook(1000) == null);
			Book book = dao.getBook(1001);
			assertTrue(book.getID() == 1001);
			assertTrue(dao.getBook(2000) == null);
			assertTrue(book.getAuthorID() == 2001);
			assertTrue(book.getPublisherID() == 3001);
		}
		
		public static void testAdd() throws IOException{
			DAOBookImpl dao = new DAOBookImpl();
			Book book = new Book();
			book.setID(1020);
			book.setAuthorID(2020);
			book.setPublisherID(3020);
			book.setName("Add");
			dao.addBook(book);
			book = new Book();
			assertTrue((book = dao.getBook(1020)) != null);
			assertTrue(book.getID() == 1020);
		}
		
		public static void testSet() throws IOException{
			DAOBookImpl dao = new DAOBookImpl();
			Book book;
			book = dao.getBook(1001);
			book.setAuthorID(2030);
			book.setPublisherID(3030);
			book.setName("Edit");
			dao.replace(1001, book);
			assertTrue((book = dao.getBook(1001)) != null);
			assertTrue(book.getAuthorID() == 2030);
			assertTrue(book.getPublisherID() == 3030);
			assertTrue(book.getName().compareTo("Edit") == 0);
			
		}
		
		public static void testDelete() throws IOException{
			DAOBookImpl dao = new DAOBookImpl();
			Book book = new Book();
			book = dao.getBook(1005); 
			dao.delete(1005); 
			assertTrue(!dao.loadBooks().stream()
							.anyMatch(e -> e.getName().compareTo("Naruto") == 0));
			assertTrue(!dao.loadBooks().stream()
					.anyMatch(e -> e.getID() == 1005));
			
		}
	}
	
	static class PublisherDaoTest{
		public static void setUp() throws IOException{
			DAOPublisherImpl pubDao = new DAOPublisherImpl();
			Publisher pub = new Publisher();
			pub.setID(3001);
			pub.setName("Bloombury Publishing");
			pub.setAddr("zzz");
			pub.setPhone(new Long("4390981234"));
			pubDao.addPublisher(pub);
			pub.setAddr("Japan");
			pub.setID(3005);
			pub.setName("Seiusha");
			pub.setPhone(new Long("3457895721"));
			pubDao.addPublisher(pub);
			pub.setAddr("England");
			pub.setID(3009);
			pub.setName("Bantam Books");
			pub.setPhone(new Long("2903845761"));
		}
		
		public static void tearDown() throws IOException{
			DAOPublisherImpl pubDao = new DAOPublisherImpl();
			pubDao.delete(3001);
			pubDao.delete(3005);
			pubDao.delete(3020);
		}
		
		public static void testGet() throws IOException {
			DAOPublisherImpl dao = new DAOPublisherImpl();
			assertTrue(dao.getPublisher(3001) != null);
			Publisher publisher = dao.getPublisher(2001);
			assertTrue(dao.getPublisher(5000) == null);
			assertTrue(dao.getPublisher(3001).getName().equals("Bloombury Publishing"));
			assertTrue(dao.getPublisher(3001).getID() == 3001);
			
		}
		
		public static void testAdd() throws IOException{
			DAOPublisherImpl dao = new DAOPublisherImpl();
			Publisher publisher = new Publisher();
			publisher.setID(3020);
			publisher.setName("Addition");
			publisher.setAddr("whatev");
			publisher.setPhone(new Long("9083245671"));
			dao.addPublisher(publisher);
			publisher = dao.getPublisher(3020);
			assertTrue(publisher != null);
			assertTrue(publisher.getID() == 3020);
			assertTrue(publisher.getName().equals("Addition"));
		}
		
		public static void testSet() throws IOException{
			DAOPublisherImpl dao = new DAOPublisherImpl();
			Publisher publisher;
			publisher = dao.getPublisher(3005);
			publisher.setName("Edit");
			dao.replace(3005, publisher);
			publisher = dao.getPublisher(3005);
			assertTrue((publisher = dao.getPublisher(3005)) != null);
			assertTrue(publisher.getID() == 3005);
			assertTrue(publisher.getName().equals("Edit"));
			
		}
		
		public static void testDelete() throws IOException{
			DAOPublisherImpl dao = new DAOPublisherImpl();
			Publisher publisher = new Publisher();
			publisher = dao.getPublisher(3009); 	
			dao.delete(3009); 
			
			assertFalse(dao.loadPublishers().stream()
							.anyMatch(e -> e.getName().compareTo("Bantam Books") == 0));
			assertFalse(dao.loadPublishers().stream()
					.anyMatch(e -> e.getID() == 3009));
			
		}
	}
	
	
}
