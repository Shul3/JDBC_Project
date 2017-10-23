package com.jdbc.db;
import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.io.*;

import com.jdbc.core.Course;
import com.jdbc.core.Student;
import com.jdbc.core.Teacher;
import com.jdbc.core.TeacherCourse;
import com.jdbc.core.History;

/**
 * Read database name, user, password from the text file.
 * Create database connection to MySQL database.
 * 
 * @author Shul, Oskwik
 * @version 2.0
 *  
 */

public class MainDB {
	
	private static Connection myConn;
	
	public MainDB() throws Exception
	{
		//get db configuration from the file
		Properties props = new Properties();
		props.load(new FileInputStream("configuration"));
		
		String user = props.getProperty("user");
		String password = props.getProperty("password");
		String url = props.getProperty("url");
		
		// connect to MySQL database
		myConn = DriverManager.getConnection(url, user, password);
		System.out.println("DB connection successful to: " + url);
}
	
	/**
	 * Returns all rows from Teacher table
	 * @return array of all teachers
	 * 
	 */
	public List<Teacher> getTeachers() throws SQLException {
		List<Teacher> listT = new ArrayList<>();
		
		try ( 
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery("select * from teacher");		
		)
		{
			while (myRs.next()) {
				Teacher tempTeacher = convertRowToTeacher(myRs);
				listT.add(tempTeacher);
			}
		return listT;		
		}
	}
	
	/**
	 * Insert new row to  Teacher table
	 * @param theTeacher object
	 * 
	 */
	public void addTeacher(Teacher theTeacher) throws Exception {
		String sql = "INSERT INTO teacher (tnr, fname, lname, comment) VALUES (?,?,?,?)";
		
		try (
			PreparedStatement ps = myConn.prepareStatement(sql);
				)
		{
			ps.setInt(1, theTeacher.getNr());
			ps.setString(2,theTeacher.getFirstName());
			ps.setString(3, theTeacher.getLastName());
			ps.setString(4, theTeacher.getComment());
			 
			ps.executeUpdate();			
		}
	}
	
	/**
	 * Update the row in the Teacher table
	 * @param theTeacher object
	 * 
	 */
	public void updateTeacher(Teacher theTeacher) throws SQLException {
		
		try (
			PreparedStatement ps = myConn.prepareStatement("update teacher"	+ " set tnr=?, fname=?, lname=?, comment=?"
						+ " where tid=?");
				)
		{
			
			ps.setInt(1, theTeacher.getNr());
			ps.setString(2, theTeacher.getFirstName());
			ps.setString(3, theTeacher.getLastName());
			ps.setString(4, theTeacher.getComment());
			ps.setInt(5, theTeacher.getId());
				
			ps.executeUpdate();			
		}
	}
	
	/**
	 * Search teacher by the first or last name  in the table
	 * @param name - search string
	 * @return listT - array of the teachers
	 * 
	 */
	public List<Teacher> searchTeachers(String name) {
		List<Teacher> listT = new ArrayList<>();

		String sql = "SELECT * FROM teacher WHERE fname LIKE ? OR lname LIKE ?";
		
		try (
				PreparedStatement ps = myConn.prepareStatement(sql);
		)
		{
			ps.setString(1, "%" + name + "%");
			ps.setString(2, "%" + name + "%");
			
			try(ResultSet rs = ps.executeQuery();)
			{	 while(rs.next()) {
				Teacher tempTeacher = convertRowToTeacher(rs);
				listT.add(tempTeacher);
			}}
			catch (SQLException e) {
			e.printStackTrace();
			} 
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return listT;
	}
	
	/**
	 * Delete the row from Teacher table
	 * @param tID - primary key in the Teacher table
	 * 
	 */
	public static void deleteTeacher(int tID) throws SQLException {
		
		try (
				PreparedStatement ps = myConn.prepareStatement("DELETE FROM teacher WHERE tid= ?");
				)
		{
			ps.setInt(1, tID);
			ps.executeUpdate();			
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Convert the row of the Teacher table to the Teacher object
	 * @param myRs - ResultSet
	 * @return Teacher object 
	 * @throws SQLException
	 */
	private Teacher convertRowToTeacher(ResultSet myRs) throws SQLException {
		
		int id = myRs.getInt("tid");
		int nr = myRs.getInt("tnr");
		String firstName = myRs.getString("fname");
		String lastName = myRs.getString("lname");
		String comment = myRs.getString("comment");
		
		return new Teacher(id, nr, firstName, lastName,  comment);
	}
	
	/**
	 * Returns all rows from TeacherHistory table
	 * @return LinkedList of all rows from the table
	 * 
	 */
	public List<History> getHistory() throws SQLException {
		List<History> listH = new LinkedList<>();
		
		try ( 
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery("select * from teacherhistory");		
		)
		{
			while (myRs.next()) {
				History tempHistory = convertRowToHistory(myRs);
				listH.add(tempHistory);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return listH;
	}
	
	/**
	 * Convert the row of the TeacherHistory table to the History object
	 * @param myRs
	 * @return History object
	 * @throws SQLException
	 */
	private History convertRowToHistory(ResultSet myRs) throws SQLException {
		
		int nr = myRs.getInt("tnr");
		String firstName = myRs.getString("fname");
		String lastName = myRs.getString("lname");
		String comment = myRs.getString("comment");
		String action = myRs.getString("action");
		String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(myRs.getTimestamp("date"));
				
		return new History(nr, firstName, lastName,  comment, action, dateTime);
	}

	/**
	 * Returns all rows from Course table
	 * @return Array of all rows from Course table
	 * 
	 */
	public List<Course> getCourses() throws SQLException {
		List<Course> listC = new ArrayList<>();
		
		try ( 
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery("select * from course");		
		)
		{
			while (myRs.next()) {
				Course tempCourse = convertRowToCourse(myRs);
				listC.add(tempCourse);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return listC;
	}
	
	/**
	 * Convert the row of the Course table to the Course object
	 * @param myRs
	 * @return Course object
	 * @throws SQLException
	 */
	private Course convertRowToCourse(ResultSet myRs) throws SQLException {
		
		int nr = myRs.getInt("cnr");
		String name = myRs.getString("cname");
		String desc = myRs.getString("description");
				
		return new Course(nr, name, desc);
	}
	
	/**
	 * Returns all courses by the teacher
	 * @param tID - primary key of Teacher table
	 * @return Array - result of JOIN SQL statement 
	 * 
	 */
	public List<TeacherCourse> getTeacherCourse(int tID) throws SQLException {
		List<TeacherCourse> list = new LinkedList<>();
		String sql ="SELECT a.tnr, a.fname, a.lname, c.cname FROM teacher AS a JOIN teachercourse as b ON a.tid=b.tid JOIN course as c ON c.cid=b.cid WHERE a.tid= ?";
		
		try ( 
				PreparedStatement ps = myConn.prepareStatement(sql);
			)
		{
			ps.setInt(1, tID);
			
			try(ResultSet rs = ps.executeQuery();)
			{	 while(rs.next()) {
				TeacherCourse tempTeacherCourse = convertRowToTeacherCourse(rs);
				list.add(tempTeacherCourse);
			}}
			catch (SQLException e) {
			e.printStackTrace();
			} 
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}
	
	/**
	 * Convert the result  of all courses by the teacher to the TeacherCourse object
	 * @param myRs
	 * @return TeacherCourse object
	 * @throws SQLException
	 */
	private TeacherCourse convertRowToTeacherCourse(ResultSet myRs) throws SQLException {
		int tNr = myRs.getInt("tnr");
		String fName = myRs.getString("fname");
		String lName = myRs.getString("lname");
		String cName = myRs.getString("cname");
				
		return new TeacherCourse(tNr, fName,lName, cName);
	}
	
	//Vidare utveckling
	public List<Student> getStudents() throws Exception {
		List<Student> list = new ArrayList<>();
		
		try ( 
				Statement myStmt = myConn.createStatement();
				ResultSet myRs = myStmt.executeQuery("select * from student");		
		)
		{
			while (myRs.next()) {
				Student tempStudent = convertRowToStudent(myRs);
				list.add(tempStudent);
			}
		return list;		
		}
	}
	public List<Student> searchStudents(String name) throws Exception {
		List<Student> list = new ArrayList<>();

		String sql = "SELECT * FROM student WHERE fname LIKE ? OR lname LIKE ?";
		

		try (
				PreparedStatement ps = myConn.prepareStatement(sql);
				
		)
		{
			ps.setString(1, "%" + name + "%");
			ps.setString(2, "%" + name + "%");
			ResultSet rs = ps.executeQuery();
						
			while(rs.next()) {
				Student tempStudent = convertRowToStudent(rs);
				list.add(tempStudent);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<Course> searchCourses(String name) throws Exception {
		List<Course> list = new ArrayList<>();

		String sql = "SELECT * FROM course WHERE cname LIKE ? ";
		

		try (
				PreparedStatement ps = myConn.prepareStatement(sql);
				
		)
		{
			ps.setString(1, "%" + name + "%");
			ResultSet rs = ps.executeQuery();
						
			while(rs.next()) {
				Course tempCourse = convertRowToCourse(rs);
				list.add(tempCourse);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	private Student convertRowToStudent(ResultSet myRs) throws SQLException {
		
		int id = myRs.getInt("sid");
		int nr = myRs.getInt("snr");
		String firstName = myRs.getString("fname");
		String lastName = myRs.getString("lname");
		String comment = myRs.getString("comment");
		String status = myRs.getString("status");
				
		return new Student(id, nr, firstName, lastName,  comment, status);
	}
}
