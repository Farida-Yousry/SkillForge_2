package skillForge;
public class Progress {
 private String courseId;
 private String lessonId;
 private boolean completed;
 public Progress(String courseId, String lessonId, boolean completed) {
	
	this.courseId = courseId;
	this.lessonId = lessonId;
	this.completed = completed;
 }
 public String getCourseId() {
	return courseId;
 }
 public String getLessonId() {
	return lessonId;
 }

 public boolean isCompleted() {
	return completed;
 }
 public void setCompleted(boolean completed) {
	this.completed = completed;
 }

}