

package skillForge;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class HomePage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static Database db;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try { 
					db = new Database();
					db.loadUsers();
					HomePage frame = new HomePage();
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
	public HomePage() {
		setTitle("Skill Forge");
		
		setBounds(100, 100, 500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		JLabel lblTitle = new JLabel("  Skill Forge",SwingConstants.CENTER);
		lblTitle.setFont(new Font("Verdana", Font.PLAIN, 30));
		lblTitle.setForeground(Color.DARK_GRAY);
		getContentPane().add(lblTitle,BorderLayout.NORTH);
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setBorder(BorderFactory.createEmptyBorder(30,50,30,0));
		getContentPane().add(contentPane, BorderLayout.SOUTH);
		
		Dimension btnSize=new Dimension(200,50);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setForeground(Color.DARK_GRAY);
		btnLogin.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnLogin.setBackground(SystemColor.inactiveCaption);
		btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLogin.setMaximumSize(btnSize);
		contentPane.add(btnLogin);
		contentPane.add(Box.createRigidArea(new Dimension(0,15)));
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		     LoginGUI loginFrame= new LoginGUI(db);
		     loginFrame.setVisible(true);
		     dispose();
			}
		});
		
		JButton btnSignup = new JButton("Signup");
		btnSignup.setForeground(Color.DARK_GRAY);
		btnSignup.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnSignup.setBackground(SystemColor.inactiveCaption);
		btnSignup.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSignup.setMaximumSize(btnSize);
		contentPane.add(btnSignup);
		contentPane.add(Box.createRigidArea(new Dimension(0,15)));
		
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		     SignupGUI SignupFrame= new SignupGUI(db);
		     SignupFrame.setVisible(true);
		     dispose();
			}
		});
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setAlignmentY(1.0f);
		btnLogout.setForeground(Color.DARK_GRAY);
		btnLogout.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnLogout.setBackground(SystemColor.inactiveCaption);
		btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLogout.setMaximumSize(btnSize);
		contentPane.add(btnLogout);
		contentPane.add(Box.createRigidArea(new Dimension(0,15)));
		
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		     System.exit(0);
			}
		});

	}

}