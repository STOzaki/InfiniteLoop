package com.st.lms.model;

//parent class of all pojos
//all constructors set as protected so they can only be called by children
//basic methods shared by all pojos implemented here
public class Entry {
	private String name;
	private int id;
	
	protected Entry(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getID() {
		return this.id;
	}
}
