package skillForge;
import java.util.Date;

public class TakeQuiz {
	//attributes
	private String studentId;
	private String lessonId;
	private int score;
	private Date dateTaken;
	
	// constructor
	public TakeQuiz(String studentId, String lessonId, int score, Date dateTaken) {
		
		this.studentId = studentId;
		this.lessonId = lessonId;
		this.score = score;
		this.dateTaken = dateTaken;
	}
  // Setters and getters
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getLessonId() {
		return lessonId;
	}

	public void setLessonId(String lessonId) {
		this.lessonId = lessonId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Date getDateTaken() {
		return dateTaken;
	}

	public void setDateTaken(Date dateTaken) {
		this.dateTaken = dateTaken;
	}

	
}
