package man;

import java.awt.BorderLayout;

import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;

public class Search extends JFrame {

	private JPanel contentPane;
	private JTextField txtSearch;
	private Desktop d;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Search frame = new Search();
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
	public Search() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 699, 399);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtSearch = new JTextField();
		txtSearch.setBounds(137, 169, 402, 26);
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Search.");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("STIXGeneral", Font.PLAIN, 45));
		lblNewLabel.setBounds(242, 96, 225, 87);
		contentPane.add(lblNewLabel);
		
		

		ImageIcon google = new ImageIcon(new ImageIcon("/Users/parthchheda/Desktop/images for gui/google.png").getImage().getScaledInstance(117, 29, DO_NOTHING_ON_CLOSE));
		JButton btnGoogle = new JButton(google);
		btnGoogle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String l = txtSearch.getText(); //gets the search from the text field
				
				String txt[] = l.split(" "); //splits the search based on the number of spaces it has
				String search = txt[0]; //first index of the array set as the main search
				int size = txt.length; //stores the length of the array as an integer
				if(size>1) { //checks whether the size is greater than one
					for(int i = 1; i<(size); i++) //creates a for loop based on the size of the array
					search = search+"+"+txt[i]; //replaces the spaces in the search with +
				}	
				try {
				d = Desktop.getDesktop(); //accesses the users desktop
				d.browse(new URI("https://www.google.com/search?q="+search)); //searches the search on the user's default browser
				} catch (URISyntaxException zz) {
				    zz.printStackTrace();
				} catch (IOException m) {
				    m.printStackTrace();
				}
			}
		});
		btnGoogle.setBounds(8, 217, 117, 74);
		contentPane.add(btnGoogle);
		
		ImageIcon jstor = new ImageIcon(new ImageIcon("/Users/parthchheda/Desktop/images for gui/jstor.png").getImage().getScaledInstance(117, 74, DO_NOTHING_ON_CLOSE));
		JButton btnJSTOR = new JButton(jstor);
		btnJSTOR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String l = txtSearch.getText();
				String txt[] = l.split(" ");
				String search = txt[0];
				int size = txt.length;
				if(size>1) {
					for(int i = 1; i<(size); i++)
					search = search+"+"+txt[i];
				}				
				try {
				d = Desktop.getDesktop();
				d.browse(new URI("https://www.jstor.org/action/doBasicSearch?Query="+search+"&so=rel"));
				} catch (URISyntaxException zz) {
				    zz.printStackTrace();
				} catch (IOException m) {
				    m.printStackTrace();
				}
			}
		});
		btnJSTOR.setBounds(137, 217, 117, 74);
		contentPane.add(btnJSTOR);
		
		ImageIcon britannica = new ImageIcon(new ImageIcon("/Users/parthchheda/Desktop/images for gui/britannica.png").getImage().getScaledInstance(117, 74, DO_NOTHING_ON_CLOSE));
		JButton btnBritannica = new JButton(britannica);
		btnBritannica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String l = txtSearch.getText();
				String txt[] = l.split(" ");
				String search = txt[0];
				int size = txt.length;
				if(size>1) {
					for(int i = 1; i<(size); i++)
					search = search+"+"+txt[i];
				}				
				try {
				d = Desktop.getDesktop();
				d.browse(new URI("https://www.britannica.com/search?query="+search));
				} catch (URISyntaxException zz) {
				    zz.printStackTrace();
				} catch (IOException m) {
				    m.printStackTrace();
				}
			}
		});
		btnBritannica.setBounds(266, 215, 117, 74);
		contentPane.add(btnBritannica);
		
		ImageIcon nlb = new ImageIcon(new ImageIcon("/Users/parthchheda/Desktop/images for gui/nlb.png").getImage().getScaledInstance(117, 74, DO_NOTHING_ON_CLOSE));
		JButton btnLibrary = new JButton(nlb);
		btnLibrary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String l = txtSearch.getText();
				String txt[] = l.split(" ");
				String search = txt[0];
				int size = txt.length;
				if(size>1) {
					for(int i = 1; i<(size); i++)
					search = search+"+"+txt[i];
				}				
				try {
				d = Desktop.getDesktop();
				d.browse(new URI("https://search.nlb.gov.sg/onesearch/Search?query="+search+"&cont=book"));
				} catch (URISyntaxException zz) {
				    zz.printStackTrace();
				} catch (IOException m) {
				    m.printStackTrace();
				}
			}
		});
		btnLibrary.setBounds(395, 217, 153, 74);
		contentPane.add(btnLibrary);
		
		ImageIcon bbc = new ImageIcon(new ImageIcon("/Users/parthchheda/Desktop/images for gui/bbc.png").getImage().getScaledInstance(117, 74, DO_NOTHING_ON_CLOSE));
		JButton btnBBC = new JButton(bbc);
		btnBBC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String l = txtSearch.getText();
				String txt[] = l.split(" ");
				String search = txt[0];
				int size = txt.length;
				if(size>1) {
					for(int i = 1; i<(size); i++)
					search = search+"+"+txt[i];
				}				
				try {
				d = Desktop.getDesktop();
				d.browse(new URI("https://www.bbc.co.uk/search?q="+search+"&page=1"));
				} catch (URISyntaxException zz) {
				    zz.printStackTrace();
				} catch (IOException m) {
				    m.printStackTrace();
				}
			}
		});
		btnBBC.setBounds(560, 217, 117, 74);
		contentPane.add(btnBBC);
		
		JLabel lblNewLabel_1 = new JLabel("After typing in a search, select where you want to search.");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("STIXGeneral", Font.PLAIN, 25));
		lblNewLabel_1.setBounds(71, 303, 582, 39);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setFont(new Font("STIXGeneral", Font.PLAIN, 13));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SavedResources.main(null);
				dispose();
			}
		});
		btnNewButton.setBounds(6, 0, 117, 29);
		contentPane.add(btnNewButton);
	}

}
