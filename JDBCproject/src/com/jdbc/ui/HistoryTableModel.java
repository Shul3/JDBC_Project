package com.jdbc.ui;

import com.jdbc.core.History;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Create table model for TeacherHistory table
 * Show all rows from the teacherhistory table
 * 
 * @author Shul, Oskwik
 * @version 2.0
 */
public class HistoryTableModel extends AbstractTableModel {

	public static final int OBJECT_COL = -1;
	private static final int NUMBER_COL = 0;
	private static final int FIRST_NAME_COL = 1;
	private static final int LAST_NAME_COL = 2;
	private static final int COMMENT_COL = 3;
	private static final int ACTION_COL = 4;
	private static final int DATE_AND_TIME_COL = 5;
	
	

	private String[] columnNames = { "Teachers Number", "First Name", "Last Name", "Comment", "Action", "Date and Time" };
	private List<History> histories;

	public HistoryTableModel(List<History> theHistory) {
		histories = theHistory;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return histories.size();
	}

	@Override
	public String  getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		History tempHistory = histories.get(row);

		switch (col) {
		case NUMBER_COL:
			return Integer.toString(tempHistory.getNr());
		case FIRST_NAME_COL:
			return tempHistory.getFirstName();
		case LAST_NAME_COL:
			return tempHistory.getLastName();
		case COMMENT_COL:
			return tempHistory.getComment();
		case ACTION_COL:
			return tempHistory.getAction();
		case DATE_AND_TIME_COL:
			return tempHistory.getDateTime();
		case OBJECT_COL:
			return tempHistory;
		default:
			return tempHistory.getLastName();
		}
	}
	
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

}
