package man;

import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Font;
import java.awt.Color;

public class SavedResources extends JFrame {

	private JPanel contentPane;
	private static ArrayList<String> projects = new ArrayList<String>();
	public static String[] array1;
	static boolean f;
	private JTextField txtURL;
	private JTextField txtLinkName;
	private DefaultListModel model4;
	private JList list4;
	private static String p; 
	static ArrayList<Integer> checking = new ArrayList<Integer>();
	private Desktop d;
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
			String sql2 = "Select ProjectID from SavedLinks";
			Statement stmt2 = c.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sql2);
			while(rs2.next()) {
				int y = rs2.getInt("ProjectID");
				if(!checking.contains(y)) {
					String sql3 = "DELETE from SavedLinks where ProjectID = "+y;			
					
					Statement stmt3 = c.createStatement();
					stmt3.executeUpdate(sql3);
					stmt3.close();
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
		array1 = new String[projects.size()];
		for(int i = 0; i < array1.length; i++) {
		    array1[i] = projects.get(i);
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SavedResources frame = new SavedResources();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	void siuuuu(String a) {
		
		try {
			Connection c;
			c = DriverManager.getConnection(url);
			String sql = "Select * from SavedLinks where ProjectID = (SELECT ProjectID from ProjectList where ProjectName  like '"+a+"%')";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			model4 = new DefaultListModel();
			list4.setModel(model4);
			while(rs.next()){
				String nm = rs.getString("LinkName");
				String siu = rs.getString("URL");
				String sneaky = rs.getString("ProjectID");
				model4.addElement(nm+", "+siu+", "+sneaky);
			}
			stmt.close();	
			rs.close();
			c.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	 void add(String a, String b, String d){
		try{
			Connection c;
			c = DriverManager.getConnection(url);
			String sql = "INSERT INTO SavedLinks (LinkName, URL, ProjectID)";
			sql = sql + "VALUES ('"+a+"', '"+b+"', "+d+")";
			Statement stmt = c.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	void delete(String a, String b, String d) {
		try {
			Connection c;
			c = DriverManager.getConnection(url);
			String sql = "DELETE from SavedLinks WHERE LinkName = '"+a+"' AND URL = '"+b+"' AND ProjectID = "+d+"";
			Statement stmt = c.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			c.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	void edit(String b, String c, String e, String p, String g){
			try {
			Connection f;
			f = DriverManager.getConnection(url);
			String sql = "UPDATE SavedLinks SET "
					+ "ProjectID = (SELECT ProjectID FROM ProjectList WHERE ProjectName = '"+b+"'), "
							+ "LinkName = '"+c+"', "
											+ "URL = '"+e+"' "
													+ "WHERE LinkName = '"+p+"' AND "
															+ "URL = '"+g+"'";
			Statement stmt = f.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			f.close();
			} catch(SQLException y) {
				y.printStackTrace();
			}
	}

	
	public SavedResources() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 627, 544);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		list4 = new JList();
		list4.setBackground(Color.GRAY);
		list4.setForeground(Color.WHITE);
		list4.setFont(new Font("STIXGeneral", Font.PLAIN, 15));
		list4.setBounds(6, 6, 365, 504);
		contentPane.add(list4);
		
		JLabel lblNewLabel = new JLabel("Choose Project");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblNewLabel.setBounds(383, 6, 148, 16);
		contentPane.add(lblNewLabel);
		
		JComboBox cmbProject = new JComboBox(array1);
		cmbProject.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		cmbProject.setEditable(true);
		cmbProject.setBounds(383, 34, 203, 27);
		contentPane.add(cmbProject);
		
		JLabel lblNewLabel_1 = new JLabel("URL");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(383, 87, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		txtURL = new JTextField();
		txtURL.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		txtURL.setBounds(383, 117, 203, 26);
		contentPane.add(txtURL);
		txtURL.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Link Name");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(383, 169, 203, 16);
		contentPane.add(lblNewLabel_2);
		
		txtLinkName = new JTextField();
		txtLinkName.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		txtLinkName.setForeground(new Color(0, 0, 0));
		txtLinkName.setBounds(383, 197, 203, 26);
		contentPane.add(txtLinkName);
		txtLinkName.setColumns(10);
		
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnBack.setForeground(Color.BLACK);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i =0; i < array1.length; i++) {
					array1[i] = null;
				}
				ShowProjects.main(null);
				ShowProjects.populateProjectList();
				dispose();
			}
		});
		btnBack.setBounds(376, 481, 117, 29);
		contentPane.add(btnBack);
		
		JButton btnSaveLInk = new JButton("Save Link");
		btnSaveLInk.setForeground(Color.BLACK);
		btnSaveLInk.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnSaveLInk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String prj = "";
				if (cmbProject.getSelectedIndex() > -1) {
					prj = array1[cmbProject.getSelectedIndex()];
				}
				String suwi = "";
				try {
				Connection c = DriverManager.getConnection(url);
				String sql = "Select ProjectID from ProjectList where ProjectName like '"+prj+"%'" ;
				Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				while(rs.next()){
					suwi = rs.getString("ProjectID");
					}}
				catch (SQLException z) {
					z.printStackTrace();
				}
				add(txtLinkName.getText(), txtURL.getText(), suwi);
			}
		});
		btnSaveLInk.setBounds(505, 481, 117, 29);
		contentPane.add(btnSaveLInk);
		
		JButton btnShowLink = new JButton("Show Links");
		btnShowLink.setForeground(Color.BLACK);
		btnShowLink.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnShowLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cmbProject.getSelectedIndex() > -1) {
					 p = array1[cmbProject.getSelectedIndex()];			
				}
				siuuuu(p);
			}
		});
		btnShowLink.setBounds(510, 440, 117, 29);
		contentPane.add(btnShowLink);
		
		JButton btnAccessLink = new JButton("Access Link");
		btnAccessLink.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnAccessLink.setForeground(Color.BLACK);
		btnAccessLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list4.getSelectedIndex()>-1) {
				String s[] = ((String)list4.getSelectedValue()).split(", ");
				String l = s[1];
				try {
				d = Desktop.getDesktop();
				d.browse(new URI(l));
				} catch (URISyntaxException zz) {
				    zz.printStackTrace();
				} catch (IOException m) {
				    m.printStackTrace();
				}}
				else {
					JOptionPane.showMessageDialog(new JFrame(), "Please select a link to access", "Error",
					        JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
		btnAccessLink.setBounds(376, 440, 117, 29);
		contentPane.add(btnAccessLink);
		
		JButton btnDelete = new JButton("Delete Link");
		btnDelete.setForeground(Color.BLACK);
		btnDelete.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list4.getSelectedIndex()>-1) {
				String s[] = ((String)list4.getSelectedValue()).split(", ");
				String tsk = s[0];
				String dd = s[1];
				String idd = s[2];
				delete(tsk, dd, idd);}
				else {
					JOptionPane.showMessageDialog(new JFrame(), "Please select a link to delete", "Error",
					        JOptionPane.ERROR_MESSAGE);	
				}
			}
		});
		btnDelete.setBounds(376, 397, 117, 29);
		contentPane.add(btnDelete);
		
		JButton btnEdit = new JButton("Edit Link");
		btnEdit.setForeground(Color.BLACK);
		btnEdit.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cmbProject.getSelectedIndex()<0||txtLinkName.getText().equals("")||txtURL.getText().equals("")) {
					JOptionPane.showMessageDialog(new JFrame(), "Please fill in all inputs.", "Error",
					        JOptionPane.ERROR_MESSAGE);	
				}else {
				String prj = "";
				if (cmbProject.getSelectedIndex() > -1) {
					prj = array1[cmbProject.getSelectedIndex()];
				}
				String s[] = ((String)list4.getSelectedValue()).split(", ");
				String tsk = s[0];
				String dd = s[1];
				edit(prj, txtLinkName.getText(), txtURL.getText(), tsk, dd);
			}}
		});
		btnEdit.setBounds(505, 399, 117, 29);
		contentPane.add(btnEdit);
		
		JButton btnNewButton = new JButton("Find more resources!");
		btnNewButton.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search.main(null);
				dispose();
			}
		});
		btnNewButton.setBounds(381, 358, 241, 29);
		contentPane.add(btnNewButton);
	}
}
