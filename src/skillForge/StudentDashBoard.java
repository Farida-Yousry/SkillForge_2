package skillForge;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.*;

public class StudentDashBoard extends JFrame {

    private Student student;
    private Database db;
    private CourseManager courseManager;
    private LessonManager lessonManager;

    private JTable tblAvailable;
    private JTable tblMyCourses;

    public StudentDashBoard(String studentId, Database db) {
        this.db = db;

        
        this.courseManager = new CourseManager(db);
        this.lessonManager = new LessonManager(courseManager);

      
        this.student = loadStudentFromDb(studentId);

        if (this.student == null) {
            JOptionPane.showMessageDialog(null, "Student not found!");
            dispose();
            return;
        }

        setTitle("Student Dashboard - " + student.getUserName());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        
            allCourses(s);
            return s;
        }

        Student s = new Student(u.getUserName(), u.getPassword(), u.getEmail(),
                                u.getUserId(), u.getRole(), u.getAge());
       
        s.setEnrolledCourses(new ArrayList<>());
        s.setProgress(new ArrayList<>());

       
        allCourses(s);

        return s;
    }


    private void allCourses(Student s) {
        s.getEnrolledCourses().clear();
        ArrayList<Course> all = courseManager.getAllCourses();
        for (Course c : all) {
            if (c.getEnrolledStudents() != null && c.getEnrolledStudents().contains(s.getUserId())) {
                s.getEnrolledCourses().add(c);
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

      
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Available Courses", availableCoursesTab());
        tabs.add("My Courses",CoursesTab());

        add(topPanel, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
    }

    private JPanel availableCoursesTab() {
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

    private JPanel CoursesTab() {
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
        ArrayList<Course> list = courseManager.getAllCourses();

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
        ArrayList<Course> list = student.getEnrolledCourses();

        String[] cols = {"ID", "Title", "Description"};
        Object[][] data = new Object[list.size()][3];

        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getCourseId();
            data[i][1] = list.get(i).getTitle();
            data[i][2] = list.get(i).getDescription();
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

       
        boolean added = student.enrollCourse(c); 

        if (!added) {
            JOptionPane.showMessageDialog(this, "Already enrolled.");
            return;
        }

      
        boolean enrolledInCourseManager = courseManager.enrollStudent(courseId, student.getUserId());
        if (!enrolledInCourseManager) {
         
            for (int i = 0; i < student.getEnrolledCourses().size(); i++) {
                if (student.getEnrolledCourses().get(i).getCourseId().equals(courseId)) {
                    student.getEnrolledCourses().remove(i);
                    break;
                }
            }
            JOptionPane.showMessageDialog(this, "Failed to enroll.");
            return;
        }

      
        saveStudentToDb();

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

        
        new StudentLessonPannel(student, courseId, lessonManager, db);
        
        refreshMyCourses();
        refreshAvailableCourses();
    }

   
    private void saveStudentToDb() {
        ArrayList<User> users = db.loadUsers();
        boolean replaced = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(student.getUserId())) {
              
                users.set(i, student);
                replaced = true;
                break;
            }
        }
        if (!replaced) {
          
            users.add(student);
        }
        db.saveToUsersFile(users);
    }
}