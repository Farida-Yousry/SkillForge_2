package skillForge;
import java.util.ArrayList;
import java.util.Date;

public class QuizManager {
	private ArrayList<Quiz> quizzes;
	private CourseManager cManager;


	public QuizManager(CourseManager cManager) {
	quizzes=new ArrayList<>();
	this.cManager=cManager;
	ArrayList<Course> courses=cManager.getAllCourses();
	if(courses!=null) {
		for(Course c:courses) {
			if(c!=null) {
				for(Lesson l:c.getLessons()) {
					if(l.getQuiz() != null)
					quizzes.add(l.getQuiz());
				}
			}
			
		}
	}
	}
	// add Quiz
	public void addQuiz(Quiz quiz) {
		quizzes.add(quiz);
	}
 // get quiz using lesson ID
	public Quiz getQuizByLessonId(String lessonId) {
		for(Quiz q:quizzes) {
			if(q.getLessonId().equals(lessonId)) {
				return q;
			}
		}
		return null;
	}
	// submit a quiz
	public int submitQuiz(String studentId,String lessonId,ArrayList<Integer> answers) {
		Quiz q= getQuizByLessonId(lessonId);
		if(q==null) return -1;//quiz not found
			int score=q.calculateScore(answers);
			TakeQuiz quiz=new TakeQuiz(studentId,lessonId,score,new Date());
			Course c=cManager.getCourseByLesson(lessonId);
			cManager.addTrials(c.getCourseId(),quiz);
		
			return score;
		
	}
	// see last quiz taken
	public TakeQuiz getLastTrial(String studentId,String lessonId) {
		Course c=cManager.getCourseByLesson(lessonId);
		TakeQuiz lastTaken=null;
		for(TakeQuiz t:c.getTrials()) {
			if(t.getStudentId().equals(studentId) && t.getLessonId().equals(lessonId)) {
				if(lastTaken==null || t.getDateTaken().after(lastTaken.getDateTaken())) {
					lastTaken=t;
				}
			}
		}
		return lastTaken;
		
	}
	// check if passed
	public boolean passed(String studentId,String lessonId) {
		TakeQuiz lastTrial=getLastTrial(studentId,lessonId);
		if(lastTrial==null)return false;
		Quiz quiz=getQuizByLessonId(lessonId);
		return lastTrial.getScore() >= (quiz.getQuestions().size()*0.6);
		}
	

	
		
	}


