package com.st.lms.model;

public class Entry {
	private String name;
	private int id;
	
	public Entry() { //default constructor that sets data to default until changed via set
		name = null;
		id = -1;
	}
	
	public Entry(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getID() {
		return this.id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
}
