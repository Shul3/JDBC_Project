package com.jdbc.ui;

import com.jdbc.core.TeacherCourse;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Create table model for TeacherCourse table
 * Use to show all courses by the teacher
 * 
 * @author Shul, Oskwik
 * @version 2.0
 */
public class TeacherCourseTableModel extends AbstractTableModel {

	public static final int OBJECT_COL = -1;
	private static final int NUMBER_COL = 0;
	private static final int FIRST_NAME_COL = 1;
	private static final int LAST_NAME_COL = 2;
	private static final int COURSE_NAME_COL = 3;
	

	private String[] columnNames = { "Teachers Number", "First Name", "Last Name", "Course Name" };
	private List<TeacherCourse> teacherCourses;

	public TeacherCourseTableModel(List<TeacherCourse> theTeacherCourse) {
		teacherCourses = theTeacherCourse;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return teacherCourses.size();
	}

	@Override
	public String  getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		TeacherCourse tempTeacherCourse = teacherCourses.get(row);

		switch (col) {
		case NUMBER_COL:
			return Integer.toString(tempTeacherCourse.getNr());
		case FIRST_NAME_COL:
			return tempTeacherCourse.getfName();
		case LAST_NAME_COL:
			return tempTeacherCourse.getlName();
		case COURSE_NAME_COL:
			return tempTeacherCourse.getcName();
		case OBJECT_COL:
			return tempTeacherCourse;
		default:
			return tempTeacherCourse.getlName();
		}
	}
	
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

}
