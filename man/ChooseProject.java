package man;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class ChooseProject extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChooseProject frame = new ChooseProject();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ChooseProject() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 428, 348);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewProject = new JButton("New Project");
		btnNewProject.setForeground(Color.BLACK);
		btnNewProject.setBackground(new Color(238, 238, 238));
		btnNewProject.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnNewProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				CreateProject.main(null);
			}
		});
		btnNewProject.setBounds(248, 100, 117, 50);
		contentPane.add(btnNewProject);	
		
		JButton btnExisitngProject = new JButton("Existing Project");
		btnExisitngProject.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnExisitngProject.setForeground(Color.BLACK);
		btnExisitngProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowProjects.main(null);
				ShowProjects.populateProjectList();
				dispose();
			}
		});
		btnExisitngProject.setBounds(248, 179, 117, 50);
		contentPane.add(btnExisitngProject);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(6, 6, 218, 308);
		contentPane.add(panel);
	}
}
