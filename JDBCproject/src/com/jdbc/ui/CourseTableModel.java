package com.jdbc.ui;

import com.jdbc.core.Course;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Create table model for Course table
 * Show all rows from the Course table
 * 
 * @author Shul, Oskwik
 * @version 2.0
 */
public class CourseTableModel extends AbstractTableModel  {

	public static final int OBJECT_COL = -1;
	private static final int COURSE_NUMBER_COL = 0;
	private static final int COURSE_NAME_COL = 1;
	private static final int DESCRIPTION_COL = 2;
	
	private String[] columnNames = { "Course Number", "Course name", "Description" };
	private List<Course> courses;

	public CourseTableModel(List<Course> theCourses) {
		courses = theCourses;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return courses.size();
	}

	@Override
	public String  getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		Course tempCourse = courses.get(row);

		switch (col) {
		case COURSE_NUMBER_COL:
			return Integer.toString(tempCourse.getNr());
		case COURSE_NAME_COL:
			return tempCourse.getName();
		case DESCRIPTION_COL:
			return tempCourse.getDesc();
		case OBJECT_COL:
			return tempCourse;
		default:
			return tempCourse.getName();
		}
	}
	
	@Override
	public Class getColumnClass(int c)   {
		return getValueAt(c, 0).getClass();
	}

}
