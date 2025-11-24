package skillForge;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.*;

public class StudentDashBoard extends JFrame {

    private Student student;
    private Database db;
    private CourseManager courseManager;
    private LessonManager lessonManager;
    private JButton btnQuiz;
    private JTable tblCertificate;
    private JTable tblAvailable;
    private JTable tblMyCourses;
    private JButton btnAllCourses;
    private JButton btnMyCourses;
    private JButton btnCertificates;
    private CertificateManager certManager;
    private JPanel contentPanel;
    private String studentId;

    public StudentDashBoard(String studentId, Database db) {
        this.db = db;
        this.studentId=studentId;
        

        
        this.courseManager = new CourseManager(db);
        this.lessonManager = new LessonManager(courseManager);
        QuizManager q=new QuizManager(courseManager);
        this.certManager=new CertificateManager(q,courseManager,db);

      
        this.student = loadStudentFromDb(studentId);
        contentPanel=new JPanel(new BorderLayout());
        add(contentPanel,BorderLayout.CENTER);

        if (this.student == null) {
            JOptionPane.showMessageDialog(null, "Student not found!");
            dispose();
            return;
        }

        setTitle("Student Dashboard - " + student.getUserName());
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tblCertificate=new JTable();
        tblMyCourses=new JTable();
        tblAvailable=new JTable();
        
        table();
        
        setVisible(true);
    
    }

    private Student loadStudentFromDb(String studentId) {
    	
        User u = db.getUserById(studentId);
        if (u == null) return null;

       
        if (u instanceof Student) {
            Student s = (Student) u;
          
            if (s.getEnrolledCourses() == null) s.setEnrolledCourses(new ArrayList<>());
            if (s.getProgress() == null) s.setProgress(new ArrayList<>());
        if(s.getCertificate()==null)s.setCertificate(new ArrayList<>());
            allCourses(s);
            return s;
        }

        Student s = new Student(u.getUserName(), u.getPassword(), u.getEmail(),
                                u.getUserId(), u.getRole(), u.getAge());
       
        /*s.setEnrolledCourses(new ArrayList<>());
        s.setProgress(new ArrayList<>());
        s.setCertificate(new ArrayList<>());
*/
       
        allCourses(s);

        return s;
      
    }


    private void allCourses(Student s) {
        s.getEnrolledCourses().clear();
        ArrayList<Course> all = courseManager.viewApprovedCourses();
        for (Course c : all) {
            if (c.getEnrolledStudents() != null && c.getEnrolledStudents().contains(s.getUserId())) {
                s.getEnrolledCourses().add(c.getCourseId());
            }
        }
    }

    private void table() {

        JLabel lblWelcome = new JLabel("Welcome, " + student.getUserName());
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 20));

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
            dispose();
            new LoginGUI(db).setVisible(true);
			}
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(lblWelcome, BorderLayout.WEST);
        topPanel.add(btnLogout, BorderLayout.EAST);
        
        JPanel panel=new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));

      
        btnAllCourses = new JButton("Available Courses");
        btnAllCourses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPanel.removeAll();
				contentPanel.add(availableCourses(),BorderLayout.CENTER);
				contentPanel.revalidate();
				contentPanel.repaint();
			}
        });
        panel.add(btnAllCourses);
    
       
        btnMyCourses = new JButton("MyCourses");
        btnMyCourses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPanel.removeAll();
				contentPanel.add(courses(),BorderLayout.CENTER);
				contentPanel.revalidate();
				contentPanel.repaint();
			
			}
        });
        panel.add(btnMyCourses);
        btnCertificates=new JButton("Earned Certificates");
        btnCertificates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPanel.removeAll();
				contentPanel.add(viewCertificates(),BorderLayout.CENTER);
				contentPanel.revalidate();
				contentPanel.repaint();
			}
        });
        panel.add(btnCertificates);
       
      
 
        add(topPanel, BorderLayout.NORTH);
        add(panel,BorderLayout.SOUTH);
       
       
    }
private JPanel viewCertificates() {
	JPanel panel=new JPanel(new BorderLayout());
	 this.student=loadStudentFromDb(studentId);
	if( this.student==null)return panel;
	ArrayList<String> enrolled = this.student.getEnrolledCourses();
	for(String c : enrolled) {
		certManager.generateCertificate(c,studentId);
	}
	 this.student=loadStudentFromDb(studentId);
	if( this.student==null)return panel;
	ArrayList<Certificate> stuCertificate= this.student.getCertificate();
	if(stuCertificate == null)stuCertificate = new ArrayList<>();
	String[] cols= {"Certificate ID","CourseTitle","Release Date"};
	Object[][] data= new Object[stuCertificate.size()][3];
	
	for(int i=0;i<stuCertificate.size();i++) {
		Certificate x=stuCertificate.get(i);
	     data[i][0] = x.getCertificateId();
	     Course course = courseManager.getCourseById(x.getCourseId());
         data[i][1] = course != null ? course.getTitle() : "Unknown Course";
         data[i][2] = new SimpleDateFormat("MMMM d,yyyy").format(x.getReleaseDate());
     }
	tblCertificate=new JTable();
    tblCertificate.setModel(new DefaultTableModel(data, cols));
		panel.add(new JScrollPane(tblCertificate),BorderLayout.CENTER);
		
		JButton btnshow=new JButton("Open Certificate");
		btnshow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
		   int row = tblCertificate.getSelectedRow();
	        if (row == -1) {
	            JOptionPane.showMessageDialog( StudentDashBoard.this, "Select a certificate first");
	            return;
	        }
	        
	       
	      //  Certificate cs= student.getCertificateById((String) data[row][0]);
	        String id=(String)tblCertificate.getValueAt(row, 0);
	        Certificate cs = student.getCertificateById(id);
	        if (cs == null) {
                JOptionPane.showMessageDialog(StudentDashBoard.this, "Certificate data not found.");
                return;
            }
	        String fileName = id + "_" + student.getUserId() + ".pdf";
	        String path="Certificates" + File.separator + fileName;
	        File cerFile = new File(path);
	        try {
	        	Course course = courseManager.getCourseById(cs.getCourseId());
	        	if (!cerFile.exists() && course != null) {
                    certManager.generateCertificatePdf(cs, student, course);
                }
	        	
	        	if(!cerFile.exists()) {
	        		JOptionPane.showMessageDialog(StudentDashBoard.this, "Error: The certificate file was not found after generation: " + path);
                    return;
	        	}
	        	//certManager.generateCertificatePdf(cs, student, CourseManager.getCourseById(cs.getCourseId()));
				Desktop.getDesktop().open(cerFile);
				
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(StudentDashBoard.this,"Cannot open certificate");
				e1.printStackTrace();
			}
	   
				}
	        });
	        
		panel.add(btnshow,BorderLayout.SOUTH);
		return panel;
	}
	

    
    
    
    
    private JPanel availableCourses() {
        JPanel panel = new JPanel(new BorderLayout());

        tblAvailable = new JTable();
        refreshAvailableCourses();

        JButton btnEnroll = new JButton("Enroll");
        btnEnroll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
     enrollSelectedCourse();}
        });
        panel.add(new JScrollPane(tblAvailable), BorderLayout.CENTER);
        panel.add(btnEnroll, BorderLayout.SOUTH);
		
        return panel;
    }

    private JPanel courses() {
        JPanel panel = new JPanel(new BorderLayout());

        tblMyCourses = new JTable();
        refreshMyCourses();

        JButton btnViewLessons = new JButton("View Lessons");
       
        btnViewLessons.addActionListener(new ActionListener() {
   			public void actionPerformed(ActionEvent e) {
   				openLessonsWindow();}
   			});
   			
        panel.add(new JScrollPane(tblMyCourses), BorderLayout.CENTER);
        panel.add(btnViewLessons, BorderLayout.SOUTH);

        return panel;
    }

    private void refreshAvailableCourses() {
        ArrayList<Course> list = courseManager.viewApprovedCourses();

        String[] cols = {"ID", "Title", "Description"};
        Object[][] data = new Object[list.size()][3];

        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getCourseId();
            data[i][1] = list.get(i).getTitle();
            data[i][2] = list.get(i).getDescription();
        }

        tblAvailable.setModel(new DefaultTableModel(data, cols));
    }

    private void refreshMyCourses() {
    	this.student = loadStudentFromDb(this.studentId);
    	if(this.student==null)return;
    	
        ArrayList<String> list = student.getEnrolledCourses();

        String[] cols = {"ID", "Title", "Description"};
        Object[][] data = new Object[list.size()][3];

        for (int i = 0; i < list.size(); i++) {
        	Course cs=CourseManager.getCourseById(list.get(i));
            data[i][0] = cs.getCourseId();
            data[i][1] = cs.getTitle();
            data[i][2] = cs.getDescription();
        }

        tblMyCourses.setModel(new DefaultTableModel(data, cols));
    }

    private void enrollSelectedCourse() {
        int row = tblAvailable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a course first");
            return;
        }

        String courseId = (String) tblAvailable.getValueAt(row, 0);
        Course c = courseManager.getCourseById(courseId);

        if (c == null) {
            JOptionPane.showMessageDialog(this, "Course not found");
            return;
        }

       
        boolean added = student.enrollCourse(c.getCourseId()); 

        if (!added) {
            JOptionPane.showMessageDialog(this, "Already enrolled.");
            return;
        }

      
        boolean enrolledInCourseManager = courseManager.enrollStudent(courseId, student.getUserId());
        if (!enrolledInCourseManager) {
         
            for (int i = 0; i < student.getEnrolledCourses().size(); i++) {
                if (student.getEnrolledCourses().get(i).equals(courseId)) {
                    student.getEnrolledCourses().remove(i);
                    break;
                }
            }
            JOptionPane.showMessageDialog(this, "Failed to enroll.");
            return;
        }

      student.addProgress(c);
      if(student.getProgress()==null)
      System.out.println("notttt");
        saveStudentToDb(student);

        JOptionPane.showMessageDialog(this, "Enrolled successfully!");
        refreshMyCourses();
    }

    private void openLessonsWindow() {
        int row = tblMyCourses.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a course first");
            return;
        }

        String courseId = (String) tblMyCourses.getValueAt(row, 0);

        
        new StudentLessonPannel(student, courseId, lessonManager, db,courseManager);
        
        refreshMyCourses();
        refreshAvailableCourses();
    }

   
    private void saveStudentToDb(Student s) {
        ArrayList<User> users = db.loadUsers();
        boolean replaced = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(student.getUserId())) {
              
                users.set(i, s);
                replaced = true;
                break;
            }
        }
        if (!replaced) {
          
            users.add(s);
        }
        db.saveToUsersFile(users);
    }
}