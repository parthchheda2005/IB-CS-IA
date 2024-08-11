//ShowProjects is a GUI which is the main area for projects. You can edit, add and determine when projects are due in this JFrame.

package man;

import java.awt.BorderLayout;


import java.util.Date;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import java.awt.Color;
import java.awt.Font;

public class ShowProjects extends JFrame {

	private JPanel contentPane;
	private static ArrayList<String> projects = new ArrayList<String>();
	DefaultListModel model;
	private static String array[];
	JList lstProjects;
	JButton btnRefresh;
	String lbl;
	String dothisweek;
	private JButton btnSeeTodos;
	private JButton btnDelete;
	private JButton btnEditTest;
	public static String projectName;
	private JButton btnBack;
	public static Date swag = new Date();
	public static SimpleDateFormat siuuu = new SimpleDateFormat("dd/MM/yy");
	private JLabel lblNewLabel;
	JComboBox cmbProjects;
	private JScrollPane scrollPane;
	private JLabel lblDueThisWeek;
	private JButton btnTimeLine;
	JLabel lblOverdue;
	static String url = "jdbc:sqlite:ComsciIA.db";
	public static void main(String[] args) { 
		EventQueue.invokeLater(new Runnable() {	
			public void run() {
				try {
					ShowProjects frame = new ShowProjects();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void populateProjectList() {
		boolean f;
			try {
			Connection c = DriverManager.getConnection(url); //connects to the database
			String sql = "Select ProjectName from ProjectList where ProjectName like '%'" ; //sql query that selects the column 'ProjectName'
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);  
			while(rs.next()){ //loops through the database based on query
				String nm = rs.getString("ProjectName"); //assigns each row of 'ProjectName' to a string
				f = projects.contains(nm); //checks whether the ArrayList named projects contains the ProjectName
				if (f == false) { 
					projects.add(nm); //if it does not, it adds it to the ArrayList
				}				
			}
			stmt.close();
			rs.close();
			c.close();
		} catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(new JFrame(), e.toString(), "Error",
			        JOptionPane.ERROR_MESSAGE); //error pop-up if connection to the database fails
		}
			int ww = projects.size()+1; //stores the size of the array 
			array = new String[ww]; // creates a new array with the size 
			for(int i = 0; i < (array.length); i++) { // for loop to transfer the data from the arraylist to the array
			    if(i==0) {
			    	array[i]="All Projects"; //first index of the array is reserved for 'All Projects' for users to have a way to view all projects
			    }else {
			    	array[i] = projects.get(i-1); //rest of the projects get placed into the array after 'All Projects'
				}
			}
	}

	 void show(String a){
		int i = 0;
			try {
			Connection c;
			i = 1;
			c = DriverManager.getConnection(url);
			i=2;
			String sql = "Select * from ProjectList";
			if (a != null) {
				sql = sql + " where ProjectName like '"+a+"%'" ;
			}
			i=3;
			Statement stmt = c.createStatement();
			i=4;
			ResultSet rs = stmt.executeQuery(sql);
			i=5;
			model = new DefaultListModel();
			i=6;
			lstProjects.setModel(model);
			while(rs.next()){
				String nm = rs.getString("ProjectName");
				String sup = rs.getString("Supervisor");
				String sub = rs.getString("Subject");
				String dd = rs.getString("DueDate");
				model.addElement(nm+", "+sup+", "+sub+", "+dd);
			}
			i=7;
			stmt.close();
			i=8;
			rs.close();
			c.close();
		} catch(SQLException e){
			e.printStackTrace();
			String message = i+ ": " + e.getMessage(); 
			JOptionPane.showMessageDialog(new JFrame(), message, "Error",
			        JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
	void delete(String a) { //takes a value from the selected index in the JList
		try {
		Connection c;
		c = DriverManager.getConnection(url);
		String sql = "DELETE from ProjectList WHERE ProjectName = '"+a+"'"; //sql statement to remove a project from the database using ProjectName
		Statement stmt = c.createStatement();
		stmt.executeUpdate(sql); //runs sql statement in the database and removing the project from it
		stmt.close();
		c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean duethisweek(String date) {
		siuuu.setLenient(false);
		try {
			Date d = siuuu.parse(date);
			Calendar today = Calendar.getInstance();
			today.add(Calendar.DATE, -1);
			Calendar thisweek = Calendar.getInstance();
			thisweek.add(Calendar.DATE, 7);
			if(d.after(today.getTime()) && d.before(thisweek.getTime())) {	
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean overdue(String date) {
		siuuu.setLenient(false);
		try {
			Date d = siuuu.parse(date);
			Calendar today = Calendar.getInstance();
			today.add(Calendar.DATE,0);
			if(d.before(today.getTime())) {
				return false;
			}else {
				return true;
			}
			
		}catch(ParseException e){
			e.printStackTrace();
			return false;
		}
	}
	

	
	public ShowProjects() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 566, 508);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 277, 468);
		contentPane.add(scrollPane);
		
		lstProjects = new JList();
		lstProjects.setForeground(Color.WHITE);
		lstProjects.setBackground(Color.GRAY);
		lstProjects.setFont(new Font("STIXGeneral", Font.PLAIN, 14));
		scrollPane.setViewportView(lstProjects);
		
		btnRefresh = new JButton("Refresh");
		btnRefresh.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnRefresh.setForeground(Color.BLACK);
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				show(null);
				
			}
		});
		btnRefresh.setBounds(443, 445, 117, 29);
		contentPane.add(btnRefresh);
		
		btnSeeTodos = new JButton("See Todos");
		btnSeeTodos.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnSeeTodos.setForeground(Color.BLACK);
		btnSeeTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				TodosTab.main(null);
			}
		});
		btnSeeTodos.setBounds(287, 218, 117, 29);
		contentPane.add(btnSeeTodos);
		
		btnDelete = new JButton("Delete");
		btnDelete.setForeground(Color.BLACK);
		btnDelete.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lstProjects.getSelectedIndex()>-1) {	
				String s[] = ((String)lstProjects.getSelectedValue()).split(", ");
				String id = s[0];
				delete(id);
				}else {
					JOptionPane.showMessageDialog(new JFrame(), "Please select a project to delete.", "Error",
					        JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnDelete.setBounds(287, 404, 117, 29);
		contentPane.add(btnDelete);
		
		btnEditTest = new JButton("Edit");
		btnEditTest.setForeground(Color.BLACK);
		btnEditTest.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnEditTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lstProjects.getSelectedIndex()>-1) {
				String s[] = ((String)lstProjects.getSelectedValue()).split(", "); //splits selected index in JList
				projectName = s[0]; //takes project name from the array
				EditProject.main(null);	//opens EditProject JFrame to edit the project
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Please select a project to edit.", "Error",
					        JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEditTest.setBounds(443, 404, 117, 29);
		contentPane.add(btnEditTest);
		
		btnBack = new JButton("Back");
		btnBack.setForeground(Color.BLACK);
		btnBack.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ChooseProject.main(null);
			}
		});
		btnBack.setBounds(287, 445, 117, 29);
		contentPane.add(btnBack);
		
		JButton btnSavedResources = new JButton("Resources");
		btnSavedResources.setForeground(Color.BLACK);
		btnSavedResources.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnSavedResources.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SavedResources.main(null);
				dispose();
			}
		});
		btnSavedResources.setBounds(427, 218, 133, 29);
		contentPane.add(btnSavedResources);
		
		lblNewLabel = new JLabel("Search Project");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblNewLabel.setBounds(295, 6, 109, 16);
		contentPane.add(lblNewLabel);
		
		JButton btnSearch = new JButton("Show Projects");
		btnSearch.setForeground(Color.BLACK);
		btnSearch.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if(cmbProjects.getSelectedIndex() == 0) {
				show(null);
			}else {
				String prj = array[cmbProjects.getSelectedIndex()];
				show(prj);
				}
			}
		});
		btnSearch.setBounds(437, 34, 123, 29);
		contentPane.add(btnSearch);
		
		cmbProjects = new JComboBox(array);
		cmbProjects.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		cmbProjects.setEditable(true);
		cmbProjects.setBounds(295, 34, 142, 27);
		contentPane.add(cmbProjects);
		
		lblOverdue = new JLabel("");
		lblOverdue.setForeground(Color.RED);
		lblOverdue.setBounds(295, 96, 238, 16);
		contentPane.add(lblOverdue);
		
		lblDueThisWeek = new JLabel("");
		lblDueThisWeek.setForeground(Color.ORANGE);
		lblDueThisWeek.setBounds(295, 143, 238, 44);
		contentPane.add(lblDueThisWeek);
		
		btnTimeLine = new JButton("Timeline");
		btnTimeLine.setForeground(Color.BLACK);
		btnTimeLine.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnTimeLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			int q = 0;
			int w = 0;
				for (int i = 0; i< model.getSize();i++) {
					String s[] = ((String)lstProjects.getModel().getElementAt(i)).split(", ");
					String id = s[3];
					if(duethisweek(id)==true) {
						q=q+1;
					}
					if (overdue(id)==false) {
						w=w+1;
					}
				}
			lblDueThisWeek.setText("You have "+q+" projects due this week.");
			lblOverdue.setText("You have "+w+" projects overdue.");
				
			}
		});
		btnTimeLine.setBounds(287, 253, 273, 29);
		contentPane.add(btnTimeLine);
	}
}
