package skillForge;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class AdminDashBoard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	 private JTable tblPending;
	 private Database db;
	 private ArrayList<Course> courses;
	 private CourseManager courseManager;
	


	public AdminDashBoard(String id,Database db) {
		this.db = db;
		this.courses = db.loadCourses();
		this.courseManager = new CourseManager(db);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		
		JPanel panelLogout = new JPanel(new BorderLayout());
	      JButton btnLogout = new JButton("Logout");
	      panelLogout.add(btnLogout,BorderLayout.EAST);
	      contentPane.add(panelLogout,BorderLayout.NORTH);
	      
	        btnLogout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
	            dispose();
	            new LoginGUI(db).setVisible(true);
				}
	        });
	        JPanel panel_1 = new JPanel(new BorderLayout());
	        tblPending = new JTable();
	        tblPending.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"CourseID", "Course Title", "Course Description","InstructorID","Status"
			}) {
		    public boolean isCellEditable(int row,int column) {
        	return false;
		
		    }
	        });

		  panel_1.add(new JScrollPane(tblPending),BorderLayout.CENTER);
          contentPane.add(panel_1,BorderLayout.CENTER);
		  
		  tblPending.setFont(new Font("Tahoma", Font.PLAIN, 10));
		  tblPending.setFillsViewportHeight(true);
		  tblPending.setColumnSelectionAllowed(true);
		  tblPending.setCellSelectionEnabled(true);
		  tblPending.setBackground(SystemColor.inactiveCaption);
            
            JButton btnAccept = new JButton("ACCEPT");
            btnAccept.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				int row = tblPending.getSelectedRow();
    				if(row ==-1) {
    					JOptionPane.showMessageDialog(contentPane,"Please select a course","Error",JOptionPane.ERROR_MESSAGE);
    					return;
    				}
    				DefaultTableModel model = (DefaultTableModel)tblPending.getModel();
    				String courseId = (String)model.getValueAt(row,0);
    				Course foundCourse = null;
    				for(Course c : courses) {
    					if(c.getCourseId().equals(courseId)) {
    						foundCourse = c;
    					break;
    					}
    				}
    				if(foundCourse != null) {
    					foundCourse.setStatus("APPROVED");
    				db.saveToCourseFile(courses);
    				model.removeRow(row);
    				JOptionPane.showMessageDialog(contentPane,courseId + "-->Approved","Success",JOptionPane.INFORMATION_MESSAGE);
    				}
    			}
            });
         
           
            JButton btnReject = new JButton("Reject");
            btnReject.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				int row = tblPending.getSelectedRow();
    				if(row ==-1) {
    					JOptionPane.showMessageDialog(contentPane,"Please select a course","Error",JOptionPane.ERROR_MESSAGE);
    					return;
    				}
    				DefaultTableModel model = (DefaultTableModel)tblPending.getModel();
    				String courseId = (String)model.getValueAt(row,0);
    				Course foundCourse = null;
    				for(Course c : courses) {
    					if(c.getCourseId().equals(courseId)) {
    						foundCourse = c;
    					break;
    					}
    				}
    				if(foundCourse != null) {
    					foundCourse.setStatus("REJECTED");
    				db.saveToCourseFile(courses);
    				model.removeRow(row);
    				JOptionPane.showMessageDialog(contentPane,courseId + "-->Rejected","Success",JOptionPane.INFORMATION_MESSAGE);
    				}
    			}
    			
            });
            
            JButton btnView = new JButton("View Lessons");
            btnView.addActionListener(new ActionListener(){
            	public void actionPerformed(ActionEvent e) {
            		int row = tblPending.getSelectedRow();
            		if(row == -1) {
            			JOptionPane.showMessageDialog(contentPane,"Please select a course","Error",JOptionPane.ERROR_MESSAGE);
            			return;
            		}
            		 DefaultTableModel model = (DefaultTableModel)tblPending.getModel();
            		 String courseId = (String)model.getValueAt(row, 0);
            		 
            		 Course selectedCourse = courseManager.getCourseById(courseId);
      
            		 
            		 if(selectedCourse != null) {
            			 view(selectedCourse);
            		 }
            		 else 
            	  			JOptionPane.showMessageDialog(contentPane,"Course not Found","Error",JOptionPane.ERROR_MESSAGE);
            		 
            	}
            });

            JPanel panel_3 = new JPanel(new FlowLayout());
            panel_3.add(btnAccept);
            panel_3.add( btnReject);
            panel_3.add(btnView);
            
            contentPane.add(panel_3,BorderLayout.SOUTH);
     
            DefaultTableModel model = (DefaultTableModel)tblPending.getModel();
            model.setRowCount(0);
            ArrayList<Course> pending = courseManager.viewPendingCourses();

			
            for(Course c : pending) {
            	ArrayList<String> lessonTitle = new ArrayList<>();
   			 for(Lesson l : c.getLessons()) {
   				 lessonTitle .add(l.getTitle());
   			 }

            
            	model.addRow(new Object[] {
            			c.getCourseId(),
            			c.getTitle(),
            			c.getDescription(),
            			c.getInstructorId(),
            			c.getStatus()
            	});
            } 


} 
    private void view(Course selectedCourse){
     	

     	 ArrayList<Lesson> lessons = selectedCourse.getLessons();
     	 if(lessons.isEmpty()) {
     		JOptionPane.showMessageDialog(AdminDashBoard.this,"No lessons in this Course!!");
     		return;
     	 }
     		StringBuilder sb = new StringBuilder("Lessons:" + selectedCourse.getTitle()+ "\n");
     		
     			
     		for(Lesson s: lessons) {
     			sb.append("-").append(s.getTitle()).append("\n");
     		}
     		JTextArea textArea = new JTextArea(sb.toString());
     		textArea.setEditable(false);
     		JScrollPane scrollPane = new JScrollPane(textArea);
     	
     		JOptionPane.showMessageDialog(AdminDashBoard.this,scrollPane,"Lessons",JOptionPane.INFORMATION_MESSAGE);
     	 }
  
}