package skillForge;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentLessonPannel extends JFrame {

    private Student student;
    private String courseId;
    private LessonManager lessonManager;
    private CourseManager courseManager;
    private Database db;
    private JTable tblLessons;

    public StudentLessonPannel(Student student, String courseId, LessonManager lessonManager, Database db,CourseManager courseManager) {
        this.student = student;
        this.courseId = courseId;
        this.lessonManager = lessonManager;
        this.courseManager = courseManager;
        this.db = db;

        setTitle("Lessons for " + courseId);
        setSize(600, 400);
        setLocationRelativeTo(null);

        complete();
        setVisible(true);
    }



    private void refreshLessons() {
        ArrayList<Lesson> lessons = lessonManager.getLessonByCourse(courseId);
        if (lessons == null) lessons = new ArrayList<>();

        String[] cols = {"Lesson ID", "Title", "Status"};
        Object[][] data = new Object[lessons.size()][3];

        for (int i = 0; i < lessons.size(); i++) {
            Lesson l = lessons.get(i);
            data[i][0] = l.getLessonId();
            data[i][1] = l.getTitle();
            data[i][2] = student.checkIfLessonCompleted(courseId, l.getLessonId())
                            ? "Completed"
                            : "Not Completed";
        }

        tblLessons.setModel(new javax.swing.table.DefaultTableModel(data, cols));
    }

    private void complete() {
    	setLayout(new BorderLayout());
    	tblLessons=new JTable();
    	refreshLessons();
        JButton btnQuiz = new JButton("Take quiz");
        btnQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				takeQuiz();
			}
        });
        JPanel button=new JPanel(new FlowLayout(FlowLayout.CENTER));
		button.add(btnQuiz);
        add(new JScrollPane(tblLessons),BorderLayout.CENTER);
		add(button,BorderLayout.SOUTH);
    	
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
    private void takeQuiz() {
		int row=tblLessons.getSelectedRow();
		if(row==-1) {
			JOptionPane.showMessageDialog(this, "Select a lessosn first");
		    return;
		}
		ArrayList<Lesson>lessons=lessonManager.getLessonByCourse(courseId);
		if(lessons==null || lessons.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No lessons for this course");
		}
		Lesson lesson=lessons.get(row);
		if(!courseManager.canAccess(student,courseId,lesson.getLessonId())) {
			JOptionPane.showMessageDialog(this, "You must pass the previous lesson quiz");
			return;
		}
		
		Quiz quiz=lesson.getQuiz();
		
  QuizFrame q=new QuizFrame(student,quiz,courseManager,db,courseId);
  q.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosed(java.awt.event.WindowEvent windowEvent) {
   refreshLessons(); }
  });
   q.setVisible(true);
}
    }