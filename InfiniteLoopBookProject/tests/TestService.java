import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Test;

import com.st.lms.dao.DAOAuthorImpl;
import com.st.lms.dao.DAOBookImpl;
import com.st.lms.model.Author;
import com.st.lms.service.Service;

public class TestService {

	private int authorTestId = 555;
	private String authorTestName = "Bill Jones";
	private DAOAuthorImpl authordao = new DAOAuthorImpl();
	private DAOBookImpl bookdao = new DAOBookImpl();
	
	//do this after all test cases
	@After
	public void cleanUp() throws IOException {
		authordao.delete(authorTestId);
	}
	
	@Test
	public void addAuthorTest() throws IOException {
		Service.addAuthor(authorTestId, authorTestName);
		assertTrue(authordao.hasAuthor(authorTestId));
	}
	
	@Test
	public void getAuthorTest() {
		Service.addAuthor(authorTestId, authorTestName);
		Author author = Service.getAuthor(authorTestId);
		assertEquals(author.getName(), authorTestName);
        //assertTrue(author.getName().equals(authorTestName));  another version
	}
	
	@Test
	public void updateAuthorTest() throws IOException {
		Service.addAuthor(authorTestId, authorTestName);
		Author author = new Author("Bill Smith", authorTestId);
		Service.updateAuthor(authorTestId, author);
		assertTrue(authordao.getAuthor(authorTestId).getName().equals("Bill Smith"));
	}
	
	@Test
	public void removeAuthorTest() throws IOException {
		//first, add dummy book that matches authorTestId
		Service.addBook(3333, "dummybook", 555, 786);
		
		Service.addAuthor(authorTestId, authorTestName);
		Service.removeAuthor(authorTestId);
		assertTrue(authordao.hasAuthor(authorTestId)==false && bookdao.hasBook(3333)==false);
	}
}