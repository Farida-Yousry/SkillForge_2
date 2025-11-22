package skillForge;
import java.util.ArrayList;

public class LessonManager {

	private CourseManager courseManager;
	
	public LessonManager(CourseManager courseManager) {
		this.courseManager = courseManager;
	}
	public ArrayList<Lesson> getLessonByCourse(String courseId) {
		Course c=courseManager.getCourseById(courseId);
		if(c==null)return null;
		return c.getLessons();
		
	}
	public Lesson getLesson(String courseId,String lessonId) {
		Course c=courseManager.getCourseById(courseId);
		if(c==null)return null;
		for(Lesson e:c.getLessons()) {
			if(e.getLessonId().equals(lessonId))
			return e;
		}
		return null;
		
	}

}
