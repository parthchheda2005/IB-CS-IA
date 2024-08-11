package man;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import java.awt.Font;
import java.awt.Color;

public class EditProject extends JFrame {

	private JPanel contentPane;
	private JTextField txtNewName;
	private JTextField txtNewSupervisor;
	private JTextField txtNewSubject;
	JSpinner spnDate;
	SpinnerDateModel yo;
	static String url = "jdbc:sqlite:ComsciIA.db";


	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditProject frame = new EditProject();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	
	void edit(String a, String b, String c, String d, String e){ 
		//String holding the name of the selected project from the ShowProject JFrame
		try {
			Connection f;
			f = DriverManager.getConnection(url);
			String sql = "UPDATE ProjectList SET ProjectName = '"+b+"', Supervisor = '"+c+"', Subject = '"+d+"', DueDate = '"+e+"' WHERE ProjectName = '"+a+"'";
			//sql statement to edit project.
			Statement stmt = f.createStatement();
			stmt.executeUpdate(sql); //updates database with new information
			stmt.close();
			f.close();
		} catch(SQLException y) {
				y.printStackTrace();
		}
	}

	
	
	public EditProject() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 246, 379);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("New Project Name");
		lblNewLabel_1.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(6, 21, 129, 16);
		contentPane.add(lblNewLabel_1);
		
		txtNewName = new JTextField();
		txtNewName.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		txtNewName.setBounds(6, 45, 130, 26);
		contentPane.add(txtNewName);
		txtNewName.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Supervisor");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(6, 83, 106, 16);
		contentPane.add(lblNewLabel_2);
		
		txtNewSupervisor = new JTextField();
		txtNewSupervisor.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		txtNewSupervisor.setBounds(6, 111, 130, 26);
		contentPane.add(txtNewSupervisor);
		txtNewSupervisor.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Subject");
		lblNewLabel_3.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(6, 149, 129, 16);
		contentPane.add(lblNewLabel_3);
		
		txtNewSubject = new JTextField();
		txtNewSubject.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		txtNewSubject.setBounds(6, 177, 130, 26);
		contentPane.add(txtNewSubject);
		txtNewSubject.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Due Date");
		lblNewLabel_4.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setBounds(6, 215, 61, 16);
		contentPane.add(lblNewLabel_4);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setForeground(Color.BLACK);
		btnCancel.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(6, 315, 93, 29);
		contentPane.add(btnCancel);
		
		JButton btnSave = new JButton("Save");
		btnSave.setForeground(Color.BLACK);
		btnSave.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LocalDate now = LocalDate.now();
				String date = ShowProjects.siuuu.format(yo.getDate());
				String split[] = date.split("/");
				String add = "20"+split[2]+"-"+split[1]+"-"+split[0];
				LocalDate d = LocalDate.parse(add);
				if(txtNewName.getText().equals("")||txtNewSupervisor.getText().equals("")||txtNewSubject.getText().equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), "Please fill in all inputs.", "Error",
					        JOptionPane.ERROR_MESSAGE);
				}else if(d.isBefore(now)) {
					JOptionPane.showMessageDialog(new JFrame(), "Please input a valid date.", "Error",
					        JOptionPane.ERROR_MESSAGE);
				}else {
				String oname = ShowProjects.projectName;
				String nnmae = txtNewName.getText();
				String sup = txtNewSupervisor.getText();
				String sub = txtNewSubject.getText();
				edit(oname, nnmae, sup, sub, ShowProjects.siuuu.format(yo.getDate()));
				dispose();}
			}
		});
		btnSave.setBounds(152, 316, 93, 29);
		contentPane.add(btnSave);
		
		Date today = new Date();
		yo = new SpinnerDateModel();
		yo.setValue(Date.from(LocalDate.now().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()));
		spnDate = new JSpinner(yo);
		spnDate.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		spnDate.setEditor(new JSpinner.DateEditor(spnDate, "dd/MM/yy"));
		spnDate.setBounds(6, 238, 129, 26);
		contentPane.add(spnDate);
	}
}
