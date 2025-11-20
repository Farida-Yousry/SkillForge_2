import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

public class InstructorDashBoard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtInstructor;
	private CourseManager courseManager;
	private JComboBox<Course> availableCourses;

	/**
	 * Create the frame.
	 */
	public InstructorDashBoard(String id) {
	    
		this.courseManager=new CourseManager(new Database());
		ArrayList<Course> instCourses=courseManager.getCourseByInstructor(id);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);
		
		txtInstructor = new JTextField("Instructor DashBoard");
		txtInstructor.setBackground(SystemColor.activeCaption);
		txtInstructor.setFont(new Font("Tahoma", Font.PLAIN, 25));
		txtInstructor.setHorizontalAlignment(SwingConstants.CENTER);
		txtInstructor.setEditable(false);
	
		availableCourses=new JComboBox<>();
		DefaultComboBoxModel<Course> model=new DefaultComboBoxModel<>();
		if(instCourses!=null) {
		for(Course c:instCourses) {
			model.addElement(c);
		}}
		
		availableCourses.setModel(model);
		
		JPanel cPanel=new JPanel(new GridLayout(2,1,6,6));
		cPanel.add(txtInstructor);
		cPanel.add(availableCourses);
		contentPane.add(cPanel,BorderLayout.NORTH);
		
		
		JPanel buttonPanel = new JPanel(new GridLayout(3,2,8,8));
		
		
		JButton btnNewButton = new JButton("Manage Courses");
		btnNewButton.setBackground(SystemColor.inactiveCaption);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Manage Lessons");
		btnNewButton_1.setBackground(SystemColor.inactiveCaption);
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonPanel.add(btnNewButton_1);
		

		
		JButton btnNewButton_3 = new JButton("Logout");
		btnNewButton_3.setBackground(SystemColor.inactiveCaption);
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonPanel.add(btnNewButton_3);
		
		contentPane.add(buttonPanel,BorderLayout.CENTER);
		
			

			
		btnNewButton.addActionListener(new ActionListener() {
	 		public void actionPerformed(ActionEvent e) {
				
				JFrame frame = new JFrame("Manage Courses ");
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setContentPane(new CourseGUI(courseManager,id));
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
	 			CourseGUI  courGui = new CourseGUI(courseManager,id);
	 			
	 			courGui.setVisible(true);
	 		}
	 	    
	    	});
		
		
		btnNewButton_1.addActionListener(new ActionListener() {
	 		public void actionPerformed(ActionEvent e) {
	 			Course selectedCourse=(Course)availableCourses.getSelectedItem();
	 			if(selectedCourse==null) {
	 				JOptionPane.showMessageDialog(null, "Please Select a Course");
	 				return;
	 			}
	 		
	 			
	 			JFrame frame = new JFrame("Manage Lessons - " +selectedCourse.getTitle());
	 			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	 			frame.setContentPane(new InstructorLessonPannel(selectedCourse,courseManager));
	 			frame.pack();
	 			frame.setLocationRelativeTo(null);
	 			frame.setVisible(true);
	
	 		}
	 	    
	    	});

		
		btnNewButton_3.addActionListener(new ActionListener() {
	 		public void actionPerformed(ActionEvent e) {
	 			int log = JOptionPane.showConfirmDialog(null, "Logout?","Confirm",JOptionPane.YES_NO_CANCEL_OPTION);
	 			if(log==JOptionPane.YES_OPTION) {
	 				dispose();

	 			}
	 		}
		});
		

	}

}
