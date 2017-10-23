package com.jdbc.ui;

import com.jdbc.core.Teacher;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Create table model for Teacher table
 * 
 * @author Shul, Oskwik
 * @version 2.0
 */
public class TeacherTableModel extends AbstractTableModel {

	public static final int OBJECT_COL = -1;
	private static final int NUMBER_COL = 0;
	private static final int FIRST_NAME_COL = 1;
	private static final int LAST_NAME_COL = 2;
	private static final int COMMENT_COL = 3;
	
	private String[] columnNames = { "Teachers Number", "First Name", "Last Name", "Comment" };
	private List<Teacher> teachers;

	public TeacherTableModel(List<Teacher> theTeachers) {
		teachers = theTeachers;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return teachers.size();
	}

	@Override
	public String  getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		Teacher tempTeacher = teachers.get(row);

		switch (col) {
		case NUMBER_COL:
			return Integer.toString(tempTeacher.getNr());
		case FIRST_NAME_COL:
			return tempTeacher.getFirstName();
		case LAST_NAME_COL:
			return tempTeacher.getLastName();
		case COMMENT_COL:
			return tempTeacher.getComment();
		case OBJECT_COL:
			return tempTeacher;
		default:
			return tempTeacher.getLastName();
		}
	}
	
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

}
