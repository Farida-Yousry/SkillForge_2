import java.util.ArrayList;

public class AdminManager {
	 private Database db;
	
	 
	public AdminManager(Database db) {
		this.db = db;
	}
	public void approveCourses(String courseId,ArrayList<Course> courses) {
		for(Course c : courses) {
			if(c.getCourseId().equals(courseId))
				c.setStatus("APPROVED");
			    break;
			 
		}
		 db.saveToCourseFile(courses);
	}
		public void rejectCourses(String courseId,ArrayList<Course> courses) {
			for(Course c : courses) {
				if(c.getCourseId().equals(courseId))
					c.setStatus("REJECTED");
				    break;
				
			}
			db.saveToCourseFile(courses);
	}

	
	
}
