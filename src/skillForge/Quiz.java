package skillForge;
import java.util.ArrayList;

public class Quiz {
	// attributes
	private String lessonId;
	private String courseId;
	private ArrayList<Question> questions;

 // constructor
  public Quiz(String lessonId, ArrayList<Question> questions) {
		this.lessonId = lessonId;
		this.questions = questions;
	}

 // calculate score method
  public int calculateScore(ArrayList<Integer> answers) {
	  int score=0;
	  for(int i=0;i<questions.size();i++) {
		  if(questions.get(i).getCorrectChoice()==answers.get(i)) {
			  score+=1;
		  }
	  }
	  return score;
  }
  public ArrayList<Question> getQuestions() {
	return questions;
}

  public String getCourseId() {
	return courseId;
}

  public void setQuestions(ArrayList<Question> questions) {
	this.questions = questions;
  }

  public void addQuestion(Question question) {
	  questions.add(question);
  }
  public String getLessonId() {
	  return lessonId;
  }
  
}
