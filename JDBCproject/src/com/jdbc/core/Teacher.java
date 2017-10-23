package com.jdbc.core;

/**
 * Creates a Teacher object with get- and set- methods.
 * Use two constructors. One - for Insert, Update and Delete database operation, another for Read.
 * @author Shul, Oskwik
 * @version 2.0
 */
public class Teacher {
	
	private int id;
	private int nr;
	private String firstName;
	private String lastName;
	private String comment;
	
	public Teacher(int nr, String firstName, String lastName, String comment)
	{
		this(0, nr, firstName, lastName, comment);
	}
	
	public Teacher(int id, int nr, String firstName, String lastName, String comment)
	{
		this.id = id;
		this.nr = nr;
		this.firstName = firstName;
		this.lastName = lastName;
		this.comment = comment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return String
				.format("Teacher [id=%s, nr=%s, firstName=%s, lastName=%s,  comment=%s]",
						id, nr, firstName, lastName,  comment);
	}
}
