package skillForge;
import java.util.ArrayList;

public class LessonManager {

	private CourseManager courseManager;
	
	public LessonManager(CourseManager courseManager) {
		this.courseManager = courseManager;
	}
	public ArrayList<Lesson> getLessonByCourse(String courseId) {
		Course c=courseManager.getCourseById(courseId);
		if(c==null && c.getLessons()==null)return new ArrayList<>();
		return c.getLessons();
		
	}
	public Lesson getLesson(String courseId,String lessonId) {
		Course c=courseManager.getCourseById(courseId);
		if(c==null && c.getLessons()==null)return null;
		for(Lesson e:c.getLessons()) {
			if(e.getLessonId()!=null && e.getLessonId().equals(lessonId))
			return e;
		}
		return null;
		
	}

}
