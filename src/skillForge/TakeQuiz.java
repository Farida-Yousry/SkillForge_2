package skillForge;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class TakeQuiz {
	//attributes
	@SerializedName("studentId")
	private String studentId;
	@SerializedName("lessonId")
	private String lessonId;
	@SerializedName("score")
	private int score;
	@SerializedName("dateTaken")
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
