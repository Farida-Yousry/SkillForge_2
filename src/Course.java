import java.util.ArrayList;

public class Course {
	private String courseId;
	private String title;
	private String description;
	private String instructorId;
	private ArrayList<Lesson> lessons;
	private ArrayList<String> enrolledStudents;
	private String status;


   public String getCourseId() {
		return courseId;
	}

	public Course(String courseId, String title, String description, String instructorId,String status) {
	super();
	this.courseId = courseId;
	this.title = title;
	this.description = description;
	this.instructorId = instructorId;
	this.status = status;
	lessons =  new ArrayList<>();
	enrolledStudents = new ArrayList<>();
}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInstructorId() {
		return instructorId;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Lesson> getLessons() {
		return lessons;
	}
	public void setLessons(ArrayList<Lesson> lessons) {
		this.lessons = lessons;
	}
	public ArrayList<String> getEnrolledStudents() {
		if(enrolledStudents == null)
			enrolledStudents = new ArrayList<>();
		return enrolledStudents;
	}
	public void setEnrolledStudents(ArrayList<String> enrolledStudents) {
		this.enrolledStudents = enrolledStudents;
	}
	public void addLesson(Lesson lesson) {
	lessons.add(lesson);	
}
   public boolean deleteLesson(String lessonId) {
	for(int i=0;i<lessons.size();i++) {
	if(lessons.get(i).getLessonId().equals(lessonId)) {
	lessons.remove(i);
	
	return true;
	}}
	return false;
}
  public boolean editLesson(Lesson updatedLesson) {
	for(int i=0;i<lessons.size();i++) {
		if(lessons.get(i).getLessonId().equals(updatedLesson.getLessonId())) {
		lessons.set(i,updatedLesson);
		return true;
		
		}}
	return false;	
}

public void enrollStudent(String studentId) {
	if(!enrolledStudents.contains(studentId)) {
		enrolledStudents.add(studentId);
	     }
	
}
public boolean removeEnrolledStudent(String studentId) {
	return	enrolledStudents.remove(studentId);
}

public ArrayList<Lesson> fetchLesson(){
	return lessons;
}
public Lesson getLessonById(String lessonId) {
	for(int i=0;i<lessons.size();i++) {
		if(lessons.get(i).getLessonId().equals(lessonId)){
			return lessons.get(i);
		}
	}
	return null;	
}
public String toString() {
	return title;
}
}