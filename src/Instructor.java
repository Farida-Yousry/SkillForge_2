import java.util.ArrayList;

public class Instructor extends User {
	private ArrayList<Course> createdCourses;

	

	public Instructor(String userName, String password, String email, String userId, String role, int age) {
		super(userName, password, email, userId, role, age);
		this.createdCourses = new ArrayList<>();
	}
	public ArrayList<Course> getCreatedCourses() {
		return createdCourses;
	}
	public void setCreatedCourses(ArrayList<Course> createdCourses) {
		this.createdCourses = createdCourses;
	}
	public void addCourse(Course course) {
		if(!createdCourses.contains(course)) {
			createdCourses.add(course);
		}
	}
	  public boolean reomveCourse(String courseId){
	        for(int i = 0; i < createdCourses.size(); i++){
	            if(createdCourses.get(i).getCourseId().equals(courseId)){
	            	createdCourses.remove(i);  
	                
	                return true;
	            }
	        }
	        return false;
	
	  }
	  }

