package com.jdbc.core;

/**
 * Creates a TeacherCoursey object with get-methods
 * @author Shul, Oskwik
 * @version 2.0
 */
public class TeacherCourse {
	
	private int tNr;
	private String fName;
	private String lName;
	private String cName;
	
	public TeacherCourse(int tNr, String fName, String lName, String cName)
	{
		this.tNr = tNr;
		this.fName = fName;
		this.lName = lName;
		this.cName = cName;
	}


	public int getNr() {
		return tNr;
	}


	public String getfName() {
		return fName;
	}
	public String getlName() {
		return lName;
	}


	public String getcName() {
		return cName;
	}

	@Override
	public String toString() {
		return "CourseTeacher [tNr=" + tNr + ", fName=" + fName   + ", lName=" + lName + ", cName=" + cName + "]";
	}

	
}
