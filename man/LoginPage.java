package man;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.UIManager;

public class LoginPage extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage frame = new LoginPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void error() {
		String message = "Incorrect Username or Password!";
	    JOptionPane.showMessageDialog(new JFrame(), message, "Error",
	        JOptionPane.ERROR_MESSAGE);
	}
	
	public LoginPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 565, 509);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		textField.setForeground(Color.BLACK);
		textField.setBackground(Color.WHITE);
		textField.setBounds(403, 183, 130, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setForeground(Color.BLACK);
		passwordField.setBackground(Color.WHITE);
		passwordField.setBounds(403, 221, 130, 26);
		contentPane.add(passwordField);
		
		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblNewLabel.setBounds(333, 189, 96, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(334, 227, 95, 16);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((textField.getText()).equals("admin") && (passwordField.getText()).equals("1234")) {
					ChooseProject.main(null);
					dispose();
				} else {
					error();
				}
			}
		});
		btnNewButton.setBounds(333, 270, 200, 29);
		contentPane.add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(6, 0, 290, 475);
		contentPane.add(panel);
		
		JLabel lblNewLabel_2 = new JLabel("");
		ImageIcon imageIcon = new ImageIcon(new ImageIcon("/Users/parthchheda/eclipse-workspace/IA/images for gui/Windows-cannot-download-drivers.jpeg").getImage().getScaledInstance(290, 290, Image.SCALE_DEFAULT));
		lblNewLabel_2.setIcon(imageIcon);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setForeground(Color.DARK_GRAY);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_3 = new JLabel("Welcome to your Project Manager.");
		lblNewLabel_3.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblNewLabel_3.setForeground(Color.WHITE);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_5 = new JLabel("Please login to continue.");
		lblNewLabel_5.setFont(new Font("STIXGeneral", Font.PLAIN, 18));
		lblNewLabel_5.setForeground(Color.WHITE);
		panel.add(lblNewLabel_5);
	}
}
