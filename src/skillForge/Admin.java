package skillForge;
import java.util.ArrayList;

public class Admin extends User{
	
	 private Database db;



	public Admin(String userName, String password, String email, String userId, String role, int age, Database db) {
		super(userName, password, email, userId, role, age);
		this.db = db;
	}
	public void approveCourses(String courseId,ArrayList<Course> courses) {
		for(Course c : courses) {
			if(c.getCourseId().equals(courseId)) {
				c.setStatus("APPROVED");
			    break;}
			 
		}
		 db.saveToCourseFile(courses);
	}
		public void rejectCourses(String courseId,ArrayList<Course> courses) {
			for(Course c : courses) {
				if(c.getCourseId().equals(courseId)) {
					c.setStatus("REJECTED");
				    break;}
				
			}
			db.saveToCourseFile(courses);
	}
 
	 
	 
}
