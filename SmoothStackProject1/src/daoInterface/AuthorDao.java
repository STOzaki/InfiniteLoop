package daoInterface;

import java.util.ArrayList;

import pojos.Author;

public interface AuthorDao {
	public void addAuthor(Author author);
	public Author getAuthor(int authorId);
	public ArrayList<Author> getAllAuthors();
	public void updateAuthor(Author author);
	public void deleteAuthor(Author author);
}