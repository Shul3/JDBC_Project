package com.jdbc.core;

/**
 * Creates a Course object with get-methods
 * 
 * @author Shul, Oskwik
 * @version 2.0
 */
public class Course {
	
	private int nr;
	private String name;
	private String desc;
	
	public Course(int nr, String name, String desc)
	{
		this.nr = nr;
		this.name = name;
		this.desc = desc;
	}

		
	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "Course [nr=" + nr + ", name=" + name + ", desc=" + desc + "]";
	}

	
}
