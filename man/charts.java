//this is the JFrame where the charts for productivity are displayed

package man;

import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class charts extends JFrame {

	private JPanel contentPane;
	private ChartPanel chartPanel;
	static String url = "jdbc:sqlite:ComsciIA.db";

	String arr[]= {"Busiest Days", "Busiest Hours", "Past 7 Days"};


	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					charts frame = new charts();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	 public void drawchartday() {
		if(chartPanel!=null) {
			this.remove(chartPanel);
		}
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		try {
			Connection c;
			c = DriverManager.getConnection(url);
			String sql = "Select * from DayOfWeek";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String daym = rs.getString("DayName");
				String tasksdone = rs.getString("TaskDone");
				int x = Integer.parseInt(tasksdone);
				String day = daym.substring(0,3);
				dataset.addValue(x, daym, day);
			}
			stmt.close();
			rs.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		JFreeChart chart = ChartFactory.createBarChart("Busiest Days", "Day", "Tasks Done", dataset, PlotOrientation.VERTICAL, false, false, false);
		chartPanel = new ChartPanel(chart);
		chartPanel.setSize(988, 425);
		chartPanel.setLocation(6, 45);
		getContentPane().add(chartPanel);
		this.repaint();
	}
	
	public void drawcharttime() {
		if(chartPanel!=null) {
			this.remove(chartPanel);
		}
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		try {
			Connection c;
			c = DriverManager.getConnection(url);
			String sql = "Select * from TimeOfDay";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String daym = rs.getString("TimeID");
				String tasksdone = rs.getString("TasksDone");
				int x = Integer.parseInt(tasksdone);
				int y = Integer.parseInt(daym)-1;
				String time = y+""/*+":00"*/;
				dataset.addValue(x, "Tasks Completed", time);
			}
			stmt.close();
			rs.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		JFreeChart chart = ChartFactory.createLineChart("Busiest Hours (0:00 - 23:00)","Time", "Tasks Done",dataset);
		chartPanel = new ChartPanel(chart);
		chartPanel.setSize(988, 425);
		chartPanel.setLocation(6, 45);
		getContentPane().add(chartPanel);
		this.repaint();
	}
	
	public void past7days() {
		if(chartPanel!=null) {
			this.remove(chartPanel); //if there is an existing chart on the screen, it removes it
		}
		DefaultCategoryDataset dataset = new DefaultCategoryDataset(); //creates a dataset for the graph
		try {
			Connection c;
			c = DriverManager.getConnection(url);
			String sql = "Select * from Productivity"; //selects everything from the Productivity table in the database
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) { //loops through each row of the database
				LocalDate days8ago = LocalDate.now().minusDays(8); //creates a variable that takes a date 8 days ago
				String daym = rs.getString("Date"); //takes the date from the database
				LocalDate localdate = LocalDate.parse(daym); //parses the database date into a LocalDate
				String tasksdone = rs.getString("TasksCompleted"); //number of tasks completed on that day
				if(localdate.isAfter(days8ago)) { //checks if the date from the database is after the date 8 days ago
					int x = Integer.parseInt(tasksdone);
					dataset.addValue(x, "Tasks Completed", localdate); 
				//stores the date in the database, with the number of tasks done on the y-axis, the label for the graph and the date on the x-axis
				}
			}
			stmt.close();
			rs.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		JFreeChart chart = ChartFactory.createLineChart("Tasks Finished over the Past 7 Days","Days", "Tasks Done",dataset); 
		//creates a line graph with the dataset and labels 
		chartPanel = new ChartPanel(chart);
		chartPanel.setSize(988, 425);
		chartPanel.setLocation(6, 45);
		getContentPane().add(chartPanel); //draws the chart on the GUI
		this.repaint();
	}
	
	public charts() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox(arr);
		comboBox.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		comboBox.setForeground(Color.BLACK);
		comboBox.setBounds(6, 6, 220, 27);
		contentPane.add(comboBox);
		
		JButton btnSeeChart = new JButton("See Chart");
		btnSeeChart.setForeground(Color.BLACK);
		btnSeeChart.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnSeeChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex()==0) {
					drawchartday();
				}else if (comboBox.getSelectedIndex()==1) {
					drawcharttime();
				}else if (comboBox.getSelectedIndex()==2) {
					past7days();
				}
			}
		});
		btnSeeChart.setBounds(236, 5, 117, 29);
		contentPane.add(btnSeeChart);
		
		JButton btnBack = new JButton("Back");
		btnBack.setForeground(Color.BLACK);
		btnBack.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBack.setBounds(877, 5, 117, 29);
		contentPane.add(btnBack);
	}
}
