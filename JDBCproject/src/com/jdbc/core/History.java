package com.jdbc.core;

/**
 * Creates a History object with get-methods
 * @author Shul, Oskwik
 * @version 2.0
 */
public class History {
		
	private int nr;
	private String firstName;
	private String lastName;
	private String comment;
	private String action;
	private String dateTime;
	
	public History(int nr, String firstName, String lastName, String comment, String action, String dateTime)
	{
		this.nr = nr;
		this.firstName = firstName;
		this.lastName = lastName;
		this.comment = comment;
		this.action = action;
		this.dateTime = dateTime;
	}

	
	public int getNr() {
		return nr;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getComment() {
		return comment;
	}

	public String getAction() {
		return action;
	}


	public String getDateTime() {
		return dateTime;
	}


	@Override
	public String toString() {
		return String
				.format("Teacher [nr=%s, firstName=%s, lastName=%s,  comment=%s, action=%s, dateTime=%s ]",
						nr, firstName, lastName,  comment, action, dateTime);
	}
}

