package com.st.lms.dao;

import java.io.IOException;

public interface DAOBasic {
//	public List<Entry> getAllEntries();			//loads all entries from database
//	public Entry getEntry(int id);				//gets a certain entry by id #
//	public Entry getEntry(String name);			//gets a certain entry by name
	public void setName(int id, String name) throws IOException;	//sets the name of entry by id#
	public void setID(int oldID, int newID) throws IOException;		//sets the id# of entry by name	
	public void delete (int id) throws IOException;					//deletes entry with id#
	
	public default String CommaEncode(String input) {
		final String MARKER = "†";
		return input.replaceAll(",", MARKER);
	}
	
	public default String CommaDecode(String input) {
		final String MARKER = "†";
		return input.replaceAll(MARKER, ",");
	}
}