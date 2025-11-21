import java.util.ArrayList;
import java.util.Date;

public class QuizManager {
	private ArrayList<Quiz> quizzes;
	private ArrayList<TakeQuiz> trials;


	public QuizManager() {
	quizzes=new ArrayList<>();
	trials=new ArrayList<>();
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
		else {
			int score=q.calculateScore(answers);
			TakeQuiz quiz=new TakeQuiz(studentId,lessonId,score,new Date());
			trials.add(quiz);
			//saveToFile
			return score;
		}
		
	}
	// see last quiz taken
	public TakeQuiz getLastTrial(String studentId,String lessonId) {
		TakeQuiz lastTaken=null;
		for(TakeQuiz t:trials) {
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


