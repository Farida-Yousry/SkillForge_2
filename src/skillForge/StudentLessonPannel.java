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
    private Database db;
    private JTable tblLessons;

    public StudentLessonPannel(Student student, String courseId, LessonManager lessonManager, Database db) {
        this.student = student;
        this.courseId = courseId;
        this.lessonManager = lessonManager;
        this.db = db;

        setTitle("Lessons for " + courseId);
        setSize(600, 400);
        setLocationRelativeTo(null);

        complete();
        setVisible(true);
    }

    private void complete() {
    	setLayout(new BorderLayout());
        tblLessons = new JTable();
        refreshLessons();

        JButton btnMark = new JButton("Mark Completed");
        btnMark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				markCompleted();
			}
        });
        add(new JScrollPane(tblLessons), BorderLayout.CENTER);
        add(btnMark, BorderLayout.SOUTH);
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

    private void markCompleted() {
        int row = tblLessons.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a lesson first");
            return;
        }

        String lessonId = (String) tblLessons.getValueAt(row, 0);

        student.markLessonCompleted(courseId, lessonId);  
        saveStudentToDb();

        JOptionPane.showMessageDialog(this, "Marked as completed.");
        refreshLessons();
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