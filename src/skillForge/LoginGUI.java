

package skillForge;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private JTextField txtUserName;
	private JPasswordField txtPassword;
	private JComboBox<String> roleBox;
	private CourseManager courseManager;

	

	/**
	 * Create the panel.
	 */
	public LoginGUI(Database db) {
		
		this.courseManager = new CourseManager(db);
	
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,300);
		setLocationRelativeTo(null);
        getContentPane().setBackground(SystemColor.controlHighlight);
        getContentPane().setLayout(new BorderLayout(20,20));
		
		JLabel lblTitle = new JLabel("Login",SwingConstants.CENTER);
		lblTitle.setFont(new Font("Verdana", Font.PLAIN, 35));
		lblTitle.setForeground(Color.DARK_GRAY);
		lblTitle.setBorder(BorderFactory.createEmptyBorder(20,0,10,0));
        getContentPane().add(lblTitle,BorderLayout.NORTH);
		
	    JPanel panel = new JPanel(new GridLayout(3,2,10,10));
	    panel.setBackground(SystemColor.controlHighlight);
	    panel.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));

	    
		JLabel label = new JLabel("  User Name :");
		label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(label);
		txtUserName = new JTextField(15);
		txtUserName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtUserName.setBackground(SystemColor.inactiveCaption);
		panel.add(txtUserName);
	
		
		JLabel label_1 = new JLabel("  Password : ");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(label_1);
		txtPassword = new JPasswordField(15);
		txtPassword.setBackground(SystemColor.inactiveCaption);
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(txtPassword);
		
		JLabel label_2 = new JLabel("  Role : ");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel.add(label_2);
		roleBox = new JComboBox<>();
		roleBox.setBackground(SystemColor.inactiveCaption);
		roleBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		roleBox.setModel(new DefaultComboBoxModel(new String[] {"Student", "Instructor","Admin"}));
		panel.add(roleBox);
        getContentPane().add(panel,BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,20,10));
		buttonPanel.setBackground(SystemColor.controlHighlight);
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setFont(new Font("Tahoma",Font.PLAIN,15));
		btnNewButton.setBackground(SystemColor.inactiveCaption);
		buttonPanel.add(btnNewButton);
		 getContentPane().add(buttonPanel,BorderLayout.SOUTH);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName=txtUserName.getText().trim();
				String password=new String(txtPassword.getPassword());
				String role=(String)roleBox.getSelectedItem();
				UserManager user = new UserManager(db);
				User valid = user.login(userName,password,role);
	
				if(valid != null) {
					JOptionPane.showMessageDialog(null,"Login Successfully");
					if(role.equals("Instructor")) {
						InstructorDashBoard dash =  new InstructorDashBoard(valid.getUserId(),courseManager);
					dash.setVisible(true);
				}
					else if(role.equals("Student")) {
						StudentDashBoard dash =	new StudentDashBoard(valid.getUserId(),db);
						dash.setVisible(true);
					}
					else {
						AdminDashBoard dash = new AdminDashBoard(valid.getUserId(),db);
					    dash.setVisible(true);}
					
						dispose();
				}
				else {
					JOptionPane.showMessageDialog(null,"Login failed");
				}
			}
		});

        
	

	}

}