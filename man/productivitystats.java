//This is the JFrame where the user can view their productivity through text and numbers

package man;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class productivitystats extends JFrame {

	private JPanel contentPane;
	static JLabel lblTasksDoneToday;
	static JLabel lblTasksDoneYesterday;
	static JLabel lblTodosDoneThisWeek;
	static int now;
	static int kal;
	static int week;
	static int min3;
	static int min4;
	static int min5;
	static int min6;
	static int min7;
	private JButton btnNewButton;
	static int mostproductiveday = 0;
	static String proday;
	static int leastproductiveday = 2147483647;
	static String lowday;
	static String prohour;
	static String lowhour;
	JLabel lblMostProductiveDay;
	JLabel lblLeastProductiveDay;
	JLabel lblMostProductiveHour;
	JLabel lblLeastProductiveHour;
	static int mostproductivehour = 0;
	static int leastproductivehour = 2147483647;
	static String url = "jdbc:sqlite:ComsciIA.db";



	
	
	public static void main(String[] args) {
		LocalDate today = LocalDate.now();
		LocalDate yesterday = LocalDate.now().minusDays(1);
		Connection c;
		try {
			c=DriverManager.getConnection(url);
			String sql = "Select TasksCompleted from Productivity WHERE Date = '"+today+"'";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String a = rs.getString("TasksCompleted");
				now = Integer.parseInt(a);		
				}
			stmt.close();
			rs.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		try {
			c=DriverManager.getConnection(url);
			String sql = "Select TasksCompleted from Productivity WHERE Date = '"+yesterday+"'";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String a = rs.getString("TasksCompleted");
				kal = Integer.parseInt(a);		
				}
			stmt.close();
			rs.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		try {
			c=DriverManager.getConnection(url);
			String sql = "Select TasksCompleted from Productivity WHERE Date = '"+LocalDate.now().minusDays(2)+"'";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String a = rs.getString("TasksCompleted");
				min3=Integer.parseInt(a);		
				}
			stmt.close();
			rs.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		try {
			c=DriverManager.getConnection(url);
			String sql = "Select TasksCompleted from Productivity WHERE Date = '"+LocalDate.now().minusDays(3)+"'";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String a = rs.getString("TasksCompleted");
				min4 = Integer.parseInt(a);		
				}
			stmt.close();
			rs.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		try {
			c=DriverManager.getConnection(url);
			String sql = "Select TasksCompleted from Productivity WHERE Date = '"+LocalDate.now().minusDays(4)+"'";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String a = rs.getString("TasksCompleted");
				min5 = Integer.parseInt(a);		
				}
			stmt.close();
			rs.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		try {
			c=DriverManager.getConnection(url);
			String sql = "Select TasksCompleted from Productivity WHERE Date = '"+LocalDate.now().minusDays(5)+"'";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String a = rs.getString("TasksCompleted");
				min6 = Integer.parseInt(a);		
				}
			stmt.close();
			rs.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		try {
			c=DriverManager.getConnection(url);
			String sql = "Select TasksCompleted from Productivity WHERE Date = '"+LocalDate.now().minusDays(6)+"'";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String a = rs.getString("TasksCompleted");
				min7 = Integer.parseInt(a);		
				}
			stmt.close();
			rs.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		week = now+kal+min3+min4+min5+min6+min7;
		
		try {
			c = DriverManager.getConnection(url);
			String sql = "Select TaskDone from DayOfWeek";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String a = rs.getString("TaskDone");
				int b = Integer.parseInt(a);
				if(b>mostproductiveday) {
					mostproductiveday = b;
					}
				}
			String sql2 = "Select DayName from DayOfWeek where TaskDone = "+mostproductiveday;
			ResultSet rs2 = stmt.executeQuery(sql2);
			stmt.close();
			rs.close();
			rs2.close();
			c.close();
			while(rs.next()) {
				proday = rs2.getString("DayName");	
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		try {
			c = DriverManager.getConnection(url);
			String sql = "Select TaskDone from DayOfWeek";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String a = rs.getString("TaskDone");
				int b = Integer.parseInt(a);
				if(b<leastproductiveday) {
					leastproductiveday = b;
				}
			}
			String sql3 = "Select DayName from DayOfWeek where TaskDone = "+leastproductiveday;
			ResultSet rs3 = stmt.executeQuery(sql3);
			while(rs.next()) {
				lowday = rs3.getString("DayName");
			}
			stmt.close();
			rs3.close();
			rs.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		try {
			c = DriverManager.getConnection(url);
			String sql = "Select TasksDone from TimeOfDay";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String a = rs.getString("TasksDone");
				int b = Integer.parseInt(a);
				if(b>mostproductivehour) {
					mostproductivehour = b;
					}
				}
			String sql3 = "Select TimeID from TimeOfDay where TasksDone = "+mostproductivehour;
			ResultSet rs3 = stmt.executeQuery(sql3);
			while(rs.next()) {
				prohour = rs3.getString("TimeID");
				int x = Integer.parseInt(prohour)-1;
				prohour = x+":00";	
			}
			stmt.close();
			rs3.close();
			rs.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		try {
			c = DriverManager.getConnection(url);
			String sql = "Select TasksDone from TimeOfDay";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String a = rs.getString("TasksDone");
				int b = Integer.parseInt(a);
				if(b<leastproductivehour) {
					leastproductivehour = b;
					}
				}
			String sql3 = "Select TimeID from TimeOfDay where TasksDone = "+leastproductivehour;
			ResultSet rs3 = stmt.executeQuery(sql3);
			while(rs.next()) {
				lowhour = rs3.getString("TimeID");
				int x = Integer.parseInt(lowhour)-1;
				lowhour = x+":00";	
			}
			stmt.close();
			rs3.close();
			rs.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
				
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					productivitystats frame = new productivitystats();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public productivitystats() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 503, 237);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblTasksDoneToday = new JLabel("Todos done today:");
		lblTasksDoneToday.setForeground(Color.WHITE);
		lblTasksDoneToday.setFont(new Font("STIXGeneral", Font.PLAIN, 14));
		lblTasksDoneToday.setBounds(259, 185, 148, 16);
		contentPane.add(lblTasksDoneToday);
		
		lblTasksDoneYesterday = new JLabel("Todos done yesterday:");
		lblTasksDoneYesterday.setForeground(Color.WHITE);
		lblTasksDoneYesterday.setFont(new Font("STIXGeneral", Font.PLAIN, 14));
		lblTasksDoneYesterday.setBounds(259, 157, 177, 16);
		contentPane.add(lblTasksDoneYesterday);
		
		lblTodosDoneThisWeek = new JLabel("Todos done this week:");
		lblTodosDoneThisWeek.setForeground(Color.WHITE);
		lblTodosDoneThisWeek.setFont(new Font("STIXGeneral", Font.PLAIN, 14));
		lblTodosDoneThisWeek.setBounds(259, 129, 219, 16);
		contentPane.add(lblTodosDoneThisWeek);
		
		btnNewButton = new JButton("Check Productivity");
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblTasksDoneToday.setText("Todos done today: "+now);
				lblTasksDoneYesterday.setText("Todos done yesterday: "+kal);
				lblTodosDoneThisWeek.setText("Todos done this week: "+week);
				lblMostProductiveDay.setText("Most Productive Day: "+"Tuesday");
				lblLeastProductiveDay.setText("Least Productive Day: "+lowday);
				lblMostProductiveHour.setText("Most Productive Hour: "+prohour);
				lblLeastProductiveHour.setText("Least Productive Hour: "+lowhour);
			}
		});
		btnNewButton.setBounds(52, 96, 155, 29);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.setForeground(Color.BLACK);
		btnNewButton_1.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_1.setBounds(65, 137, 117, 29);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("See Chart");
		btnNewButton_2.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnNewButton_2.setForeground(Color.BLACK);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				charts.main(null);
			}
		});
		btnNewButton_2.setBounds(65, 55, 117, 29);
		contentPane.add(btnNewButton_2);
		
		lblMostProductiveDay = new JLabel("Most Productive Day:  Wednesday");
		lblMostProductiveDay.setForeground(Color.WHITE);
		lblMostProductiveDay.setFont(new Font("STIXGeneral", Font.PLAIN, 14));
		lblMostProductiveDay.setBounds(259, 101, 311, 16);
		contentPane.add(lblMostProductiveDay);
		
		lblLeastProductiveDay = new JLabel("Least Productive Day:");
		lblLeastProductiveDay.setForeground(Color.WHITE);
		lblLeastProductiveDay.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblLeastProductiveDay.setBounds(259, 17, 246, 16);
		contentPane.add(lblLeastProductiveDay);
		
		lblMostProductiveHour = new JLabel("Most Productive Hour: ");
		lblMostProductiveHour.setForeground(Color.WHITE);
		lblMostProductiveHour.setFont(new Font("STIXGeneral", Font.PLAIN, 14));
		lblMostProductiveHour.setBounds(259, 45, 248, 16);
		contentPane.add(lblMostProductiveHour);
		
		lblLeastProductiveHour = new JLabel("Least Productive Hour: ");
		lblLeastProductiveHour.setForeground(Color.WHITE);
		lblLeastProductiveHour.setFont(new Font("STIXGeneral", Font.PLAIN, 14));
		lblLeastProductiveHour.setBounds(259, 73, 246, 16);
		contentPane.add(lblLeastProductiveHour);
	}
}
