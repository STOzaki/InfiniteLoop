package com.st.lms.dao;

import java.io.IOException;
import java.util.List;
import com.st.lms.model.*;

public interface DAOAuthor extends DAOBasic{
	public List<Author> loadAuthors() throws IOException;
	public Author getAuthor(int id) throws IOException;
	public void addAuthor(Author author) throws IOException; //appends given author to author database
	public void replace(int id, Author author) throws IOException; //replaces author with given id with given author data
	public boolean hasAuthor(int id) throws IOException; //returns true if an author with given id already exists in database
}
