package com.jdbc.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import com.jdbc.core.Teacher;
import com.jdbc.db.MainDB;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Create Dialog for update or insert row of the Teacher table
 * 
 * @author Shul, Oskwik
 * @version 2.0
 */
public class AddTeacherDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField NumbertextField;
	private JTextField FirstNametextField;
	private JTextField LastNametextField;
	private JTextField CommenttextField;
	
	private MainDB mainDB;
	private TeacherSearch teacherSearch;
	
	private Teacher previousTeacher = null;
	private boolean updateMode = false;
	
	public AddTeacherDialog(TeacherSearch theTeacherSearch, MainDB theTeacherDB, Teacher thePreviousTeacher,
			 boolean theUpdateMode) {
		this();
		mainDB = theTeacherDB;
		teacherSearch = theTeacherSearch;
		previousTeacher = thePreviousTeacher;
		updateMode = theUpdateMode;
		
		if(updateMode) {
			setTitle("Update Teacher");
			setGui(previousTeacher);
		}
	}
	
	/**
	 * Fill out the GUI-form from Teacher object
	 * @param theTeacher object
	 */
	private void setGui(Teacher theTeacher) {

		FirstNametextField.setText(theTeacher.getFirstName());
		LastNametextField.setText(theTeacher.getLastName());
		CommenttextField.setText(theTeacher.getComment());
		NumbertextField.setText(Integer.toString(theTeacher.getNr()));		
	}

	public AddTeacherDialog(TeacherSearch theTeacherSearch,
			MainDB theTeacherDB) {
		this(theTeacherSearch, theTeacherDB, null, false);
	}
	
	/**
	 * Create the dialog for Insert or Update teachers data.
	 */
	public AddTeacherDialog() {
		setTitle("Add Teacher");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		{
			JLabel lblNewLabel = new JLabel("Teachers Number");
			contentPanel.add(lblNewLabel, "2, 2, right, default");
		}
		{
			NumbertextField = new JTextField();
			contentPanel.add(NumbertextField, "4, 2, fill, default");
			NumbertextField.setColumns(10);
		}
		{
			JLabel lblFirstName = new JLabel("First Name");
			contentPanel.add(lblFirstName, "2, 4, right, default");
		}
		{
			FirstNametextField = new JTextField();
			contentPanel.add(FirstNametextField, "4, 4, fill, default");
			FirstNametextField.setColumns(10);
		}
		{
			JLabel lblLastName = new JLabel("Last Name");
			contentPanel.add(lblLastName, "2, 6, right, default");
		}
		{
			LastNametextField = new JTextField();
			contentPanel.add(LastNametextField, "4, 6, fill, default");
			LastNametextField.setColumns(10);
		}
		{
			JLabel lblComment = new JLabel("Comment");
			contentPanel.add(lblComment, "2, 8, right, default");
		}
		{
			CommenttextField = new JTextField();
			contentPanel.add(CommenttextField, "4, 8, fill, default");
			CommenttextField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveTeacher();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
	}
	
	/**
	 * Convert Teacher Number from String to integer
	 * @param teacherNrStr
	 * @return  result
	 */
	protected int convertStringToInteger(String teacherNrStr) {

		int result = 0;

		try {
			int numberInt = Integer.parseInt(teacherNrStr);

			result = Integer.valueOf(numberInt);
		} catch (Exception exc) {
			System.out.println("Invalid value. Defaulting to 0");
			result = Integer.valueOf(0);
		}

		return result;
	}
	
	/**
	 * Insert or Update teachers data to database
	 */
	protected void saveTeacher() {

		String numberStr = NumbertextField.getText();
		String firstName = FirstNametextField.getText();
		String lastName = LastNametextField.getText();
		String comment = CommenttextField.getText();
		
		int number = convertStringToInteger(numberStr);
		Teacher tempTeacher = null;
		
		if (updateMode) {
			tempTeacher = previousTeacher;
			
			tempTeacher.setLastName(lastName);
			tempTeacher.setFirstName(firstName);
			tempTeacher.setComment(comment);
			tempTeacher.setNr(number);
			
		} else {
			tempTeacher = new Teacher(number, firstName, lastName, comment);
		}

				
		try {
			// save to the database
			if(updateMode) {
				mainDB.updateTeacher(tempTeacher);
			} else {
				mainDB.addTeacher(tempTeacher);
			}
			
			// close dialog
			setVisible(false);
			dispose();

			// refresh gui list
			teacherSearch.refreshTeacherView();
			
			// show success message
			JOptionPane.showMessageDialog(teacherSearch,
					"Teacher added succesfully.",
					"Teacher Added",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(
					teacherSearch,
					"Error saving teacher: "
							+ exc.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
