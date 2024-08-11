package man;

import java.awt.BorderLayout;



import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.time.ZoneId;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;

public class CreateProject extends JFrame {

	private JPanel contentPane;
	private JTextField txtProjectName;
	private JTextField txtSupervisor;
	private JTextField txtSubject;
	JButton btnCancel;
	JButton btnSave;
	SpinnerDateModel yo;
	JComboBox cmbDay;
	private JSpinner spnDates;
	static String url = "jdbc:sqlite:ComsciIA.db";

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateProject frame = new CreateProject();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	public void checkConn(Connection c) {
		try {
			if(c != null && !c.isClosed()) {
				c.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void add(String a, String b, String d, String f){
		try{
			Connection c;
			c = DriverManager.getConnection(url); //establishes connection to database
			String sql = "INSERT INTO ProjectList (ProjectName, Supervisor, Subject, DueDate)"; //sql query to add 
			String projectname = a;
			String supervisor = b;
			String subject = d;  // assigning stuff to variables that need to be added to the database
			String duedate = f;
			sql = sql + "VALUES ('"+projectname+"', '"+supervisor+"', '"+subject+"', '"+duedate+"')"; //updating sql query with the updated variables
			Statement stmt = c.createStatement();
			stmt.executeUpdate(sql); //running sql query in the database and adds stuff to the database
			stmt.close();
			c.close(); //closing connection
			}catch(SQLException e){
				e.printStackTrace();
				String message = e.getMessage(); 
				JOptionPane.showMessageDialog(new JFrame(), message, "Error",
				        JOptionPane.ERROR_MESSAGE); //creates pop-up if database connection fails
		}
	}
	
	public CreateProject() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 386);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Project Name");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblNewLabel.setBounds(280, 84, 83, 16);
		contentPane.add(lblNewLabel);
		
		txtProjectName = new JTextField();
		txtProjectName.setForeground(new Color(0, 0, 0));
		txtProjectName.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		txtProjectName.setBounds(280, 112, 130, 26);
		contentPane.add(txtProjectName);
		txtProjectName.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Date (Day/Month)");
		lblNewLabel_1.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(280, 216, 112, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("Supervisor");
		lblNewLabel_3.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(280, 18, 90, 16);
		contentPane.add(lblNewLabel_3);
		
		txtSupervisor = new JTextField();
		txtSupervisor.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		txtSupervisor.setBounds(280, 46, 130, 26);
		contentPane.add(txtSupervisor);
		txtSupervisor.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Subject");
		lblNewLabel_4.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setBounds(280, 150, 61, 16);
		contentPane.add(lblNewLabel_4);
		
		txtSubject = new JTextField();
		txtSubject.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		txtSubject.setBounds(280, 178, 130, 26);
		contentPane.add(txtSubject);
		txtSubject.setColumns(10);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setForeground(Color.BLACK);
		btnCancel.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ChooseProject.main(null);
				
			}
		});
		btnCancel.setBounds(258, 316, 83, 29);
		contentPane.add(btnCancel);
		
		btnSave = new JButton("Save");
		btnSave.setForeground(Color.BLACK);
		btnSave.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LocalDate now = LocalDate.now();
				String date = ShowProjects.siuuu.format(yo.getDate());
				String split[] = date.split("/");
				String add = "20"+split[2]+"-"+split[1]+"-"+split[0];
				LocalDate d = LocalDate.parse(add);
				if(txtProjectName.getText().equals("")||txtSupervisor.getText().equals("")||txtSubject.getText().equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), "Please fill in all inputs.", "Error",
					        JOptionPane.ERROR_MESSAGE);
				}else if(d.isBefore(now)) {
					JOptionPane.showMessageDialog(new JFrame(), "Please input a valid date.", "Error",
					        JOptionPane.ERROR_MESSAGE);
				}else {
				add(txtProjectName.getText(), txtSupervisor.getText(), txtSubject.getText(), ShowProjects.siuuu.format(yo.getDate()));
				dispose();
				ShowProjects.main(null);
				ShowProjects.populateProjectList();
				}
			}
		});
		btnSave.setBounds(361, 316, 83, 29);
		contentPane.add(btnSave);
		
		Date today = new Date();
		yo = new SpinnerDateModel();
		yo.setValue(Date.from(LocalDate.now().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()));
		spnDates = new JSpinner(yo);
		spnDates.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		spnDates.setBackground(Color.LIGHT_GRAY);
		spnDates.setEditor(new JSpinner.DateEditor(spnDates, "dd/MM/yy"));
		spnDates.setBounds(280, 243, 122, 26);
		contentPane.add(spnDates);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(6, 6, 245, 339);
		contentPane.add(panel);
		
		JLabel lblNewLabel_2 = new JLabel("");
		ImageIcon imageIcon = new ImageIcon(new ImageIcon("/Users/parthchheda/Desktop/images for gui/photo-1612534526511-dd27833d6e50.jpeg").getImage().getScaledInstance(245, 245, Image.SCALE_DEFAULT));
		lblNewLabel_2.setIcon(imageIcon);
		panel.add(lblNewLabel_2);
	}
}
