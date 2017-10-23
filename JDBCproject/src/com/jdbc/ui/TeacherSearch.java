package com.jdbc.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jdbc.db.MainDB;
import com.jdbc.core.Course;
import com.jdbc.core.History;
import com.jdbc.core.Teacher;
import com.jdbc.core.TeacherCourse;
import javax.swing.JOptionPane;
import java.util.List;

import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

/**
 * Start the application
 * 
 * @author Shul, Oskwik
 * @version 2.0
 *
 */
public class TeacherSearch extends JFrame {

	private JPanel contentPane;
	private JTextField NameTextField;
	private JTable table;
	
	private MainDB mainDB;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeacherSearch frame = new TeacherSearch();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TeacherSearch() {
		
		try {
			mainDB = new MainDB();
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this,  "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE );
		}
		
		setTitle("Teacher Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 296);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblEnterName = new JLabel("Enter Name");
		panel.add(lblEnterName);
		
		NameTextField = new JTextField();
		panel.add(NameTextField);
		NameTextField.setColumns(10);
		
		JButton btnNewButton = new JButton("Search by First and Last names");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					String name = NameTextField.getText();
					
					List<Teacher> teachers = null;
					
					if (name != null && name.trim().length() > 0) {
						teachers = mainDB.searchTeachers(name);
					} else {
						teachers = mainDB.getTeachers();
					}
					
					//create the model and update the "table"
					TeacherTableModel model = new TeacherTableModel(teachers);
					table.setModel(model);
					
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(TeacherSearch.this,  "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE );
				}
				
			}
		});
		panel.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		JButton btnNewButton_1 = new JButton("Add");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				AddTeacherDialog dialog = new AddTeacherDialog(TeacherSearch.this, mainDB);
				dialog.setVisible(true);
			}
		});
		btnNewButton_1.setHorizontalAlignment(SwingConstants.LEFT);
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Update");
		btnNewButton_2.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int row = table.getSelectedRow();
				
				if (row < 0) {
					JOptionPane.showMessageDialog(TeacherSearch.this, "You must select an teacher", "Error",
							JOptionPane.ERROR_MESSAGE);				
					return;
				}
				
				// get the current teacher
				Teacher tempTeacher = (Teacher) table.getValueAt(row, TeacherTableModel.OBJECT_COL);
					
				// create dialog
				AddTeacherDialog dialog = new AddTeacherDialog(TeacherSearch.this, mainDB, 
															tempTeacher, true);

				// show dialog
				dialog.setVisible(true);
			}
		});
		panel_1.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Delete");
		btnNewButton_3.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					int row = table.getSelectedRow();

					if (row < 0) {
						JOptionPane.showMessageDialog(TeacherSearch.this, 
								"You must select an teacher", "Error", JOptionPane.ERROR_MESSAGE);				
						return;
					}

					int response = JOptionPane.showConfirmDialog(
							TeacherSearch.this, "Delete this teacher?", "Confirm", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (response != JOptionPane.YES_OPTION) {
						return;
					}

					// get the current teacher
					Teacher tempTeacher = (Teacher) table.getValueAt(row, TeacherTableModel.OBJECT_COL);

					// delete the teacher
					MainDB.deleteTeacher(tempTeacher.getId());

					refreshTeacherView();

					JOptionPane.showMessageDialog(TeacherSearch.this,
							"The Teacher deleted succesfully.", "The Teacher Deleted",
							JOptionPane.INFORMATION_MESSAGE);

				} catch (Exception exc) {
					JOptionPane.showMessageDialog(TeacherSearch.this,
							"Error deleting employee: " + exc.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		panel_1.add(btnNewButton_3);
		
		JButton btnViewHistory = new JButton("View History");
		btnViewHistory.setHorizontalAlignment(SwingConstants.RIGHT);
		btnViewHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Call database and get all histories
				// Print out histories
				
				try {
										
					List<History> histories = null;
					histories = mainDB.getHistory();
					
					//create the model for History
					HistoryTableModel modelH = new HistoryTableModel(histories);
					table.setModel(modelH);
					
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(TeacherSearch.this,  "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE );
				}
			}
		});
		panel_1.add(btnViewHistory);
		
		JButton btnViewCourses = new JButton("View Courses");
		btnViewCourses.setHorizontalAlignment(SwingConstants.RIGHT);
		btnViewCourses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					List<Course> courses = null;
					courses = mainDB.getCourses();
					
											
					//create the model for Course
					CourseTableModel model = new CourseTableModel(courses);
					table.setModel(model);
					
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(TeacherSearch.this,  "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE );
				}
				
				
			}
		});
		panel_1.add(btnViewCourses);
		
		JButton btnNewButton_4 = new JButton("View by Courses");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					int row = table.getSelectedRow();

					if (row < 0) {
						JOptionPane.showMessageDialog(TeacherSearch.this, 
								"You must select an teacher", "Error", JOptionPane.ERROR_MESSAGE);				
						return;
					}
					
					// get the current teacher
					Teacher tempTeacher = (Teacher) table.getValueAt(row, TeacherTableModel.OBJECT_COL);

					// search the teachers courses 
					List<TeacherCourse> teacherCourses = null;
					teacherCourses = mainDB.getTeacherCourse(tempTeacher.getId());
					
					//create the model for TeacherCourse
					TeacherCourseTableModel model = new TeacherCourseTableModel(teacherCourses);
					table.setModel(model);
					} catch (Exception exc) {
					JOptionPane.showMessageDialog(TeacherSearch.this,
							"Error deleting employee: " + exc.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel_1.add(btnNewButton_4);
	}

	/**
	 * Refresh the Teacher View after insert, update or delete rows in the table.
	 */
	public void refreshTeacherView() {

		try {
			List<Teacher> teachers = mainDB.getTeachers();

			// create the model and update the "table"
			TeacherTableModel model = new TeacherTableModel(teachers);

			table.setModel(model);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(this, "Error: " + exc, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}
}
