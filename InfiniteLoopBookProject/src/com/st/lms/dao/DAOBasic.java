package com.st.lms.dao;

import java.io.IOException;

public interface DAOBasic {
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