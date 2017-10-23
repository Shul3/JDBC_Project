package com.jdbc.core;

/**
 * Creates a Student object with get- and set- methods
 * @author Shul, Oskwik
 * @version 2.0
 */

public class Student {
	
	private int id;
	private int nr;
	private String firstName;
	private String lastName;
	private String comment;
	private String status;
	
	public Student(int id, int nr, String firstName, String lastName, String comment, String status)
	{
		this.id = id;
		this.nr = nr;
		this.firstName = firstName;
		this.lastName = lastName;
		this.comment = comment;
		this.status = status;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", nr=" + nr + ", firstName=" + firstName + ", lastName=" + lastName + ", comment="
				+ comment + ", status=" + status + "]";
	}
	
}
