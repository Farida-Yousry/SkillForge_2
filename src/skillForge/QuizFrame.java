package skillForge;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class QuizFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblQuestion;
	private JLabel lblQuestionnum;
	private ArrayList<JRadioButton> choices;
	private ArrayList<ButtonGroup> questionButtons;
	private JButton btnSubmit;
	private JButton btnNext;
	private JButton btnBack;
	private JPanel questionsPanel;
	private ArrayList<Integer> answers;
	private int questionNum;
	private Quiz quiz;
	private Student student;
	private ArrayList<JPanel> questionPanels;
	private CourseManager cManager;
	private Database db;
    private String courseId;

	public QuizFrame(Student student,Quiz quiz,CourseManager cManager,Database db,String courseId) {
		questionButtons=new ArrayList<>();
		 questionPanels=new ArrayList<>();
		this.student=student;
		this.quiz=quiz;
		this.cManager=cManager;
		this.db=db;
		this.courseId=courseId;
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		displayQuiz();

	}
	private JPanel displayQuestions(Question question,int questionNumber) {
		JPanel questionPanel=new JPanel();
		questionPanel.setLayout(new BoxLayout(questionPanel,BoxLayout.Y_AXIS));
		questionPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		JLabel lblQuestion = new JLabel(questionNumber+")"+ question.getQuestion());
	    questionPanel.add(lblQuestion);
	    
	    ButtonGroup buttons= new ButtonGroup() ;
	    int index=0;
	    for(String q:question.getChoices()) {
	    	JRadioButton choice=new JRadioButton(q);
	    	buttons.add(choice);
	    		choice.setActionCommand(String.valueOf(index));
	    	index++;
	    	questionPanel.add(choice);
	    	
	    }
	    this.questionButtons.add(buttons);
	    questionPanels.add(questionPanel);
	    return questionPanel;
	}
	private void displayQuiz() {
	   questionsPanel=new JPanel();
		questionsPanel.setLayout(new BoxLayout(questionsPanel,BoxLayout.Y_AXIS));
		
		int questionNumber=1;
		for(Question q:quiz.getQuestions()) {
			JPanel panel=displayQuestions(q,questionNumber++);
			questionsPanel.add(panel);
		}
		JScrollPane scroll=new JScrollPane(questionsPanel);
		scroll.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		scroll.setPreferredSize(new java.awt.Dimension(400,200));
		contentPane.add(scroll,BorderLayout.CENTER);
		
		btnSubmit=new JButton("Submit Quiz");
		contentPane.add(btnSubmit,BorderLayout.SOUTH);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Integer> answers=new ArrayList<>();
			
				for(ButtonGroup b:questionButtons) {
					ButtonModel select=b.getSelection();
				if(select!=null) {
					String com=select.getActionCommand();
				   int index=Integer.parseInt(com);
				   answers.add(index);
				}
				else answers.add(-1);
				
			}
				String lessonId=quiz.getLessonId();
				QuizManager qManager=new QuizManager(cManager);
				
				int score=qManager.submitQuiz(student.getUserId(), lessonId, answers);
				displayResults(score,answers);
			
		
			}
	});
}
	 private void displayResults(int score,ArrayList<Integer> answers) {
		 int numberOfQuestions=quiz.getQuestions().size();
		 double percent=(double)score/numberOfQuestions*100;
		 btnSubmit.setEnabled(false);
		 //JPanel resultsPanel=new JPanel();
		// resultsPanel.setLayout(new BoxLayout(resultsPanel,BoxLayout.Y_AXIS));
		 
		 JOptionPane.showMessageDialog(QuizFrame.this,"Result = " + percent+"%");
		 
			for(int i=0;i<quiz.getQuestions().size();i++) {
				Question q=quiz.getQuestions().get(i);
				int correctAnswer=q.getCorrectChoice();
				String text=q.getChoices().get(correctAnswer);
				JPanel panel=questionPanels.get(i);
				panel.add(new JLabel("Correct Answer:"+text));
			}
			contentPane.revalidate();
			contentPane.repaint();
		
		 if(percent>=60.0) {
			 String lessonId=quiz.getLessonId();
				 student.markLessonCompleted(courseId, lessonId);
			saveStudentToDb();
			 
			 JOptionPane.showMessageDialog(QuizFrame.this,"Quiz passed successfully!");
		 }
		 else {
			 JOptionPane.showMessageDialog(QuizFrame.this,"Quiz failed,please try again");
		 }
		 
		 
		 
} 
	 private void saveStudentToDb()
	 {
		 ArrayList<User> users=db.loadUsers();
		 boolean replaced=false;
		 for(int i=0;i<users.size();i++) {
			// User x=users.get(i);
			 if(users.get(i).getUserId().equals(student.getUserId())) {
				 users.set(i,student);
				 replaced=true;
				 break;
			 }
		 }
		 if(!replaced) {
			 users.add(student);
		 }
		 db.saveToUsersFile(users);
		 
	 }	 }
