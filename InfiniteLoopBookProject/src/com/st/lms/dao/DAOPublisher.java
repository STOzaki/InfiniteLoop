package com.st.lms.dao;

import java.io.IOException;
import java.util.List;

import com.st.lms.model.Publisher;

public interface DAOPublisher extends DAOBasic{
	public List<Publisher> loadPublishers() throws IOException; //returns a list containing all publishers in the database
	public Publisher getPublisher(int id) throws IOException; //returns a publisher with corresponding id#. if not found, returns null
	public void addPublisher(Publisher publisher) throws IOException; //appends given publisher to publisher database
	public boolean hasPublisher(int id) throws IOException; //returns true if a publisher with given id already exists in database
	public void delete(int id) throws IOException; //deletes the publisher with the given id
	public void replace(int id, Publisher publisher) throws IOException; //replaces publisher with given id with given publisher data
}
