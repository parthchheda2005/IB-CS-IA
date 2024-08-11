//TodosTab is a is the tab where todos are displayed, added, and sorted based on priority and date

package man;

import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Color; 

public class TodosTab extends JFrame {

	private JPanel contentPane;
	private static ArrayList<String> projects = new ArrayList<String>();
	static JList lstTodos;
	static private DefaultListModel model;
	JComboBox cmbProjects;
	private JButton btnBack;
	public static String[] array;
	static boolean f;
	String p;
	public static String priority[] = {"High", "Medium", "Low"};
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_2;
	private JComboBox cmbPriority;
	private JButton btnAddTodo;
	private JButton btnFinishTodo;
	private JScrollPane scrollPane;
	private JSpinner spnDates;
	SpinnerDateModel spn;
	private JButton btnEditTodo;
	public static String editTask = "";
	public static String editDueDate = "";
	JTextArea txtTask;
	private JScrollPane scrollPane_1;
	private JButton btnSort;
	static ArrayList<Integer> checking = new ArrayList<Integer>();
	private JComboBox cmbSort;
	String sort[] = {"Date", "Priority"};
	static boolean t;
	static int num=0;
	private JButton btnNewButton;
	static String url = "jdbc:sqlite:ComsciIA.db";


	
	public static void main(String[] args) {
		try {
			Connection c;
			c=DriverManager.getConnection(url);
			String sql = "Select ProjectID from ProjectList";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int n = rs.getInt("ProjectID");
				checking.add(n);
			}
			String sql2 = "Select ProjectID from Todos";
			Statement stmt2 = c.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sql2);
			while(rs2.next()) {
				int y = rs2.getInt("ProjectID");
				if(!checking.contains(y)) {
					String sql3 = "DELETE from Todos where ProjectID = "+y;
					Statement stmt3 = c.createStatement();
					stmt3.executeUpdate(sql3);
				}
			}
			stmt.close();
			stmt2.close();
			rs.close();
			rs2.close();
			c.close();
		}
		catch(SQLException x) {
			x.printStackTrace();
		}
		try {
			Connection c;
			LocalDate today = LocalDate.now(); //assigns the current date as a variable
			c=DriverManager.getConnection(url);
			String sql = "SELECT Date FROM Productivity";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql); //runs sql statement in the database
			String nm = "";
			while(rs.next()){ //loops to each row and getting the date on each row
				nm = rs.getString("Date");
				LocalDate localdate = LocalDate.parse(nm); //parses the date into a LocalDate
				if(localdate.isBefore(today)) { //checks whether the date in the database exists
					t=true; //if it doesn't, public boolean t is true
				}else {
					t=false; //if it does exist, the boolean is false
				}	
			}
			stmt.close();
			rs.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		if (t==true) { //if the date does not exist in the database
		try {
			Connection c;
			LocalDate today = LocalDate.now(); //gets today's date
			c=DriverManager.getConnection(url);
			String sql = "INSERT INTO Productivity  (Date, TasksCompleted) VALUES ('"+today+"', "+0+")"; 
			//inserts today's date with zero tasks done so far 
			Statement stmt = c.createStatement();
			stmt.executeUpdate(sql); //runs the sql statement 
			stmt.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		}
			try {
			Connection c;
			c = DriverManager.getConnection(url);
			String sql = "Select ProjectName from ProjectList";
			sql = sql + " where ProjectName like '%'" ;
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String nm = rs.getString("ProjectName");
				f = projects.contains(nm);
				if (f == false) {
					projects.add(nm);
				}				
			}
			stmt.close();
			rs.close();
			c.close();
		} catch(SQLException e){
			e.printStackTrace();
		}

		int ww = projects.size()+3;
		array = new String[ww];
		for(int i = 0; i < (array.length); i++) {
		    if(i==0) {
		    	array[i]="All Projects";
		    }else if(i == 1) {
		    	array[i]="Due This Week";
		    }else if (i==2) {
		    	array[i]="Overdue";
		    }else {
		    	array[i] = projects.get(i-3);
			}
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TodosTab frame = new TodosTab();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	void addProject(String a, String b, String d, String f){
		try{
			Connection c;
			c = DriverManager.getConnection(url);
			String sql = "INSERT INTO Todos (ProjectID, Task, Priority, DueDate)";
			String projectid = a;
			String task = b;
			String priority = d;
			String duedate = f;
			sql = sql + " VALUES ("+projectid+", '"+task+"', "+priority+", '"+duedate+"')";
			Statement stmt = c.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		}catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(new JFrame(), "didnt fucking work", "Error",
			        JOptionPane.ERROR_MESSAGE);
		}
	}
	void edit(String b, String c, String d, String e, String p, String g){
		try {
				Connection f;
			f = DriverManager.getConnection(url);
			String sql = "UPDATE Todos SET "
					+ "ProjectID = (SELECT ProjectID FROM ProjectList WHERE ProjectName = '"+b+"'), "
							+ "Task = '"+c+"', "
									+ "Priority = "+d+", "
											+ "DueDate = '"+e+"' "
													+ "WHERE Task = '"+p+"' AND "
															+ "DueDate = '"+g+"'";
			Statement stmt = f.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			f.close();
			} catch(SQLException y) {
				y.printStackTrace();
				JOptionPane.showMessageDialog(new JFrame(), "didnt fucking work", "Error",
				        JOptionPane.ERROR_MESSAGE);
			}
	}
	
	void siuu(String a) {
			try {
			Connection c;
			c = DriverManager.getConnection(url);
			String sql = "Select ProjectID from ProjectList";
			sql = sql + " where ProjectName like '"+a+"%'" ;
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			String nm = "";
			while(rs.next()){
				nm = rs.getString("ProjectID");
			}
			String sql2 = "Select * from Todos where ProjectID = "+nm;
			Statement stmt2 = c.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sql2);
			model = new DefaultListModel();
			lstTodos.setModel(model);
			while(rs2.next()){
				String ts = rs2.getString("Task");
				String pr = rs2.getString("Priority");
				String dd = rs2.getString("DueDate");
				String num = "";
				if (pr.equals("1")) {
					num = "High";
				}else if (pr.equals("2")) {
					num = "Medium";
				}else if (pr.equals("3")) {
					num = "Low";
				}
				model.addElement(ts+", "+dd+", "+num+" Priority");
			}
			stmt.close();
			stmt2.close();
			rs.close();
			rs2.close();
			c.close();
		} catch(SQLException e){
			e.printStackTrace();

		}
	}
	
	void delete(String a, String b) {
		Connection c;
		try {
			c = DriverManager.getConnection(url);
			String sql = "DELETE from Todos WHERE Task = '"+a+"' AND DueDate = '"+b+"'";
			Statement stmt = c.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	void orderbydate(String a) {
		try {
			Connection c;
			c = DriverManager.getConnection(url);
			String sql = "Select ProjectID from ProjectList";
			sql = sql + " where ProjectName like '"+a+"%'" ;
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			String nm = "";
			String sql2;
			while(rs.next()){
				nm = rs.getString("ProjectID");
			}
			if (nm.equals("")) {
				sql2 = "Select * from Todos ORDER BY DueDate DESC";
			} else {
				sql2 = "Select * from Todos where ProjectID = "+nm+" ORDER BY DueDate DESC";
			}
			Statement stmt2 = c.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sql2);
			model = new DefaultListModel();
			lstTodos.setModel(model);
			while(rs2.next()){
				String ts = rs2.getString("Task");
				String pr = rs2.getString("Priority");
				String dd = rs2.getString("DueDate");
				String num = "";
				if (pr.equals("1")) {
					num = "High";
				}else if (pr.equals("2")) {
					num = "Medium";
				}else if (pr.equals("3")) {
					num = "Low";
				}
				model.addElement(ts+", "+dd+", "+num+" Priority");
			}
			stmt.close();
			stmt2.close();
			rs.close();
			rs2.close();
			c.close();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	void orderbypriority(String a) {
		try {
			Connection c;
			c = DriverManager.getConnection(url);
			String sql = "Select ProjectID from ProjectList";
			sql = sql + " where ProjectName like '"+a+"%'" ; //first selects a specific project's Todo List
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql); //runs sql statement
			String nm = "";
			while(rs.next()){
				nm = rs.getString("ProjectID"); //gets ProjectID 
			}
			
			String sql2 = "";
			if (nm.equals("")) {
				sql2 = "Select * from Todos ORDER BY Priority"; //in case there are no ProjectID selected
			} else {
				sql2 = "Select * from Todos where ProjectID = "+nm+" ORDER BY Priority"; 
				//gets all the todos for the projects, and orders them by priority
			}
			Statement stmt2 = c.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sql2);
			model = new DefaultListModel();
			lstTodos.setModel(model);
			while(rs2.next()){ //outputs the results in the JList
				String ts = rs2.getString("Task");
				String pr = rs2.getString("Priority");
				String dd = rs2.getString("DueDate");
				String num = "";
				if (pr.equals("1")) {
					num = "High";
				}else if (pr.equals("2")) {
					num = "Medium";
				}else if (pr.equals("3")) {
					num = "Low";
				}
				model.addElement(ts+", "+dd+", "+num+" Priority");
			}
			stmt.close();
			stmt2.close();
			rs.close();
			rs2.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	void overdue() {
			try {
			Connection c;
			c = DriverManager.getConnection(url); //connects to the database
		
			String sql2 = "Select * from Todos"; //sql statement selecting everything from Todos
			Statement stmt2 = c.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sql2); //runs sql statement in the database
			model = new DefaultListModel();
			lstTodos.setModel(model);
			while(rs2.next()){ //loops through each row in Todos
				String ts = rs2.getString("Task");
				String pr = rs2.getString("Priority");
				String dd = rs2.getString("DueDate");
				String num = "";
				if (pr.equals("1")) {
					num = "High";
				}else if (pr.equals("2")) {
					num = "Medium";
				}else if (pr.equals("3")) {
					num = "Low";
				}
				//prepares all the data in the database to be displayed on the GUI
				LocalDate today = LocalDate.now(); //gets today's date
				LocalDate now = LocalDate.parse(dd); //gets the date that the task is due
				if(today.isAfter(now)) {
				model.addElement(ts+", "+dd+", "+num+" Priority"); 
				//if it was due before today's date, it displays the task on the GUI
				}
			}
			//stmt.close();
			stmt2.close();
			//rs.close();
			rs2.close();
			c.close();
			}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	void duethisweek() {
			try {
				Connection c;
			c = DriverManager.getConnection(url);
			String sql2 = "Select * from Todos"; //sql statement 
			Statement stmt2 = c.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sql2); //runs statement in database
			model = new DefaultListModel();
			lstTodos.setModel(model);
			while(rs2.next()){
				String ts = rs2.getString("Task");
				String pr = rs2.getString("Priority");
				String dd = rs2.getString("DueDate");
				String num = "";
				if (pr.equals("1")) {
					num = "High";
				}else if (pr.equals("2")) {
					num = "Medium";
				}else if (pr.equals("3")) {
					num = "Low";
				} //prepares data to be shown in the GUI
				LocalDate yesterday = LocalDate.now().minusDays(1); //Creates yesterday's date as a variable
				LocalDate week = LocalDate.now().minusDays(-8); //Creates the date a week from now as a variable
				LocalDate now = LocalDate.parse(dd);
				if(now.isAfter(yesterday)&&now.isBefore(week)) { //if statement to run check whether the date is within the week
				model.addElement(ts+", "+dd+", "+num+" Priority"); //loads data in the GUI if it meets the parameters of the if statement
				}
			}
			stmt2.close();
			rs2.close();
			c.close();
		}catch(SQLException e) {
		e.printStackTrace();
		}
	}
	
	void seealltodos() {
			try {
				Connection c;
			c = DriverManager.getConnection(url);
			String sql = "Select ProjectID from ProjectList";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			String nm = "";
			while(rs.next()){
				nm = rs.getString("ProjectID");
			}
			String sql2 = "Select * from Todos";
			Statement stmt2 = c.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sql2);
			model = new DefaultListModel();
			lstTodos.setModel(model);
			while(rs2.next()){
				String ts = rs2.getString("Task");
				String pr = rs2.getString("Priority");
				String dd = rs2.getString("DueDate");
				String num = "";
				if (pr.equals("1")) {
					num = "High";
				}else if (pr.equals("2")) {
					num = "Medium";
				}else if (pr.equals("3")) {
					num = "Low";
				}
				model.addElement(ts+", "+dd+", "+num+" Priority");
			}
			stmt.close();
			stmt2.close();
			rs.close();
			rs2.close();
			c.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	void productivity() {
		String cc = "";
		try {
			Connection c;
			LocalDate today = LocalDate.now(); //establishes the current date
			c=DriverManager.getConnection(url); 
			String sql = "SELECT * FROM Productivity WHERE Date = '"+today+"'"; //sql statement to select a row with the current date
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql); //runs the statement in the database
			String d = "";
			String a = "";
			while(rs.next()){
				d = rs.getString("Date");
				a = rs.getString("TasksCompleted");
				if(d.equals(today.toString())) { 
					//parses the current date to a the string, and compares it to the date in the database.
					cc=d; 
					num=Integer.parseInt(a)+1; //num is a public variable that has all the tasks done in a day
				}
			}
			String sql2 = "UPDATE Productivity SET TasksCompleted = "+num+" WHERE Date = '"+cc+"'"; 
			//updates the database with the new amount of tasks done
			Statement stmt2=c.createStatement();
			stmt2.executeUpdate(sql2); //runs the statement in the database
			stmt2.close();
			stmt.close();
			rs.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	void dayofweek() {
		String cc = "";
		Calendar cal = Calendar.getInstance();
		int today = cal.get(Calendar.DAY_OF_WEEK);
		try {
			Connection c;
			c=DriverManager.getConnection(url);
			String sql = "SELECT * FROM DayOfWeek WHERE WeekDayID = "+today;
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			String d = "";
			String a = "";
			while(rs.next()){
				d = rs.getString("WeekDayID");
				a = rs.getString("TaskDone");
				int check = Integer.parseInt(d);
				if(check == today) {
					cc=d;
					num=Integer.parseInt(a)+1;
				}
			}
			String sql2 = "UPDATE DayOfWeek SET TaskDone = "+num+" WHERE WeekDayID = "+cc;
			Statement stmt2=c.createStatement();
			stmt2.executeUpdate(sql2);
			stmt.close();
			stmt2.close();
			rs.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	void timeofday() {
		String cc = "";
		Calendar cal = Calendar.getInstance();
		int today = cal.get(Calendar.HOUR_OF_DAY)-1;
		try {
			Connection c;
			c=DriverManager.getConnection(url);
			String sql = "SELECT * FROM TimeOfDay WHERE TimeID = "+today;
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			String d = "";
			String a = "";
			while(rs.next()){
				d = rs.getString("TimeID");
				a = rs.getString("TasksDone");
				int check = Integer.parseInt(d);
				if(check == today) {
					cc=d;
					num=Integer.parseInt(a)+1;
				}
			}
			String sql2 = "UPDATE TimeOfDay SET TasksDone = "+num+" WHERE TimeID = "+cc;
			Statement stmt2=c.createStatement();
			stmt2.executeUpdate(sql2);
			stmt.close();
			stmt2.close();
			rs.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	public TodosTab() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 623, 666);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 288, 626);
		contentPane.add(scrollPane);
		
		lstTodos = new JList();
		lstTodos.setForeground(Color.WHITE);
		lstTodos.setBackground(Color.GRAY);
		lstTodos.setFont(new Font("STIXGeneral", Font.PLAIN, 15));
		scrollPane.setViewportView(lstTodos);
		
		JLabel lblNewLabel = new JLabel("Select Project to View/Add Todos!");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblNewLabel.setBounds(306, 6, 252, 16);
		contentPane.add(lblNewLabel);
		
		cmbProjects = new JComboBox(array);
		cmbProjects.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		cmbProjects.setEditable(true);
		cmbProjects.setBounds(306, 34, 190, 27);
		contentPane.add(cmbProjects);
		
		btnBack = new JButton("Back");
		btnBack.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i =0; i < array.length; i++) {
					array[i] = null;
				}
				ShowProjects.populateProjectList();
				ShowProjects.main(null);
				dispose();
			}
		});
		btnBack.setBounds(441, 603, 117, 29);
		contentPane.add(btnBack);
		
		JButton btnSeeTodos = new JButton("See Todos!");
		btnSeeTodos.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnSeeTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cmbProjects.getSelectedIndex() == 0) {
					seealltodos();
				}else if(cmbProjects.getSelectedIndex() == 1){
					duethisweek();
				}
				else if(cmbProjects.getSelectedIndex() == 2){
					overdue();
				}else {
					 p = array[cmbProjects.getSelectedIndex()];	
					siuu(p);
				}
			}
		});
		btnSeeTodos.setBounds(496, 34, 117, 29);
		contentPane.add(btnSeeTodos);
		
		JLabel lblNewLabel_1 = new JLabel("What task needs to be done?");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblNewLabel_1.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(306, 85, 225, 16);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("When is it due?");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(306, 297, 190, 16);
		contentPane.add(lblNewLabel_2);
		
		lblNewLabel_4 = new JLabel("Priority");
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblNewLabel_4.setBounds(306, 370, 61, 16);
		contentPane.add(lblNewLabel_4);
		
		cmbPriority = new JComboBox(priority);
		cmbPriority.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		cmbPriority.setEditable(true);
		cmbPriority.setBounds(306, 398, 97, 27);
		contentPane.add(cmbPriority);
		
		btnAddTodo = new JButton("Add Todo");
		btnAddTodo.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnAddTodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LocalDate now = LocalDate.now();
				String t = ShowProjects.siuuu.format(spn.getDate());
				String s[] = t.split("/");
				String a = "20"+s[2]+"-"+s[1]+"-"+s[0];
				LocalDate d = LocalDate.parse(a);
				if(cmbProjects.getSelectedIndex()<2 || txtTask.getText().equals("") || cmbPriority.getSelectedIndex()<0) {
					JOptionPane.showMessageDialog(new JFrame(), "Please fill in all inputs.", "Error",
					        JOptionPane.ERROR_MESSAGE);
				}else if(d.isBefore(now)) {
					JOptionPane.showMessageDialog(new JFrame(), "Please input a valid date.", "Error",
					        JOptionPane.ERROR_MESSAGE);
				}else {
				String prj = "";
				if (cmbProjects.getSelectedIndex() > -1) {
					prj = array[cmbProjects.getSelectedIndex()];
				}
				String suwi = "";
				try {
				Connection c = DriverManager.getConnection(url);
				String sql = "Select ProjectID from ProjectList where ProjectName like '"+prj+"'" ;
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()){
					suwi = rs.getString("ProjectID");
					}}
				catch (SQLException z) {
					z.printStackTrace();
				} 
				String task = txtTask.getText();
				String pp = "";
				String p = "";
				if (cmbPriority.getSelectedIndex()>-1) {
					pp = priority[cmbPriority.getSelectedIndex()];
					if(pp.equalsIgnoreCase("High")) {
						p = "1";
					}
					else if (pp.equalsIgnoreCase("Medium")) {
						p = "2";
					} else if(pp.equalsIgnoreCase("Low")) {
						p = "3";
					}
				}
				
				String date = ShowProjects.siuuu.format(spn.getDate());
				String split[] = date.split("/");
				String add = "20"+split[2]+"-"+split[1]+"-"+split[0];
				addProject(suwi, task, p, add);}
			}
		});
		btnAddTodo.setBounds(441, 562, 117, 29);
		contentPane.add(btnAddTodo);
		
		btnFinishTodo = new JButton("Finish Todo");
		btnFinishTodo.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnFinishTodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lstTodos.getSelectedIndex()>-1) {
				String s[] = ((String)lstTodos.getSelectedValue()).split(", ");
				String tsk = s[0];
				String dd = s[1];
				delete(tsk, dd);
				productivity();
				timeofday();
				dayofweek();
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Please select a project to delete.", "Error",
					        JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnFinishTodo.setBounds(306, 562, 117, 29);
		contentPane.add(btnFinishTodo);
		
		spn = new SpinnerDateModel(); //creating a JSpinner
		spn.setValue(Date.from(LocalDate.now().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant())); 
		//setting the default value of the spinner to the current date
		spnDates = new JSpinner(spn); //creates JSpinner in the GUI
		spnDates.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		spnDates.setEditor(new JSpinner.DateEditor(spnDates, "dd/MM/yy")); 
		//ensures that the spinner increments by date instead of numbers, foll0wing the date format of dd/MM/yy
		spnDates.setBounds(306, 325, 133, 26);
		contentPane.add(spnDates); //adds the spinner in the GUI
		
		btnEditTodo = new JButton("Edit Todo");
		btnEditTodo.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnEditTodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LocalDate now = LocalDate.now();
				String t = ShowProjects.siuuu.format(spn.getDate());
				String z[] = t.split("/");
				String a = "20"+z[2]+"-"+z[1]+"-"+z[0];
				LocalDate d = LocalDate.parse(a);
				if(cmbProjects.getSelectedIndex()<2 || txtTask.getText().equals("") || cmbPriority.getSelectedIndex()<0) {
					JOptionPane.showMessageDialog(new JFrame(), "Please fill in all inputs.", "Error",
					        JOptionPane.ERROR_MESSAGE);
				}else if(d.isBefore(now)) {
					JOptionPane.showMessageDialog(new JFrame(), "Please input a valid date.", "Error",
					        JOptionPane.ERROR_MESSAGE);
				}else if (lstTodos.getSelectedIndex()<0) {
					JOptionPane.showMessageDialog(new JFrame(), "Please select a todo to edit", "Error",
					        JOptionPane.ERROR_MESSAGE);
				}else {
				String s[] = ((String)lstTodos.getSelectedValue()).split(", ");
				editTask = s[0];
				editDueDate = s[1];
				String prj = "";
				if (cmbProjects.getSelectedIndex() > -1) {
					prj = array[cmbProjects.getSelectedIndex()];
				}
				String task = txtTask.getText();
				String pp = "";
				String p = "";
				if (cmbPriority.getSelectedIndex()>-1) {
					pp = priority[cmbPriority.getSelectedIndex()];
					if(pp.equalsIgnoreCase("High")) {
						p = "1";
					}
					else if (pp.equalsIgnoreCase("Medium")) {
						p = "2";
					} else if(pp.equalsIgnoreCase("Low")) {
						p = "3";
					}
				}
				String date = ShowProjects.siuuu.format(spn.getDate());
				String split[] = date.split("/");
				String add = "20"+split[2]+"-"+split[1]+"-"+split[0];
				edit(prj, task, p, add, editTask, editDueDate);}
			
			}
		});
		
		btnEditTodo.setBounds(306, 603, 123, 29);
		contentPane.add(btnEditTodo);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(306, 113, 225, 168);
		contentPane.add(scrollPane_1);
		
		txtTask = new JTextArea();
		txtTask.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		scrollPane_1.setViewportView(txtTask);
		
		btnSort = new JButton("Sort");
		btnSort.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cmbProjects.getSelectedIndex() > -1) {
					 p = array[cmbProjects.getSelectedIndex()];			
				}
				if(cmbSort.getSelectedIndex()==0) {
					orderbydate(p);
				} else if(cmbSort.getSelectedIndex()==1) {
					orderbypriority(p);
				}
			}
		});
		btnSort.setBounds(496, 451, 117, 29);
		contentPane.add(btnSort);
		
		cmbSort = new JComboBox(sort);
		cmbSort.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		cmbSort.setBounds(306, 452, 190, 27);
		contentPane.add(cmbSort);
		
		btnNewButton = new JButton("Productivity");
		btnNewButton.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				productivitystats.main(null);
			}
		});
		btnNewButton.setBounds(306, 517, 252, 29);
		contentPane.add(btnNewButton);
	}
}
