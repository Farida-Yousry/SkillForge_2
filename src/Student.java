import java.util.List;
import java.util.ArrayList;

    public class Student extends User{
        private ArrayList<Course> enrolledCourses;
        private  ArrayList<Progress> progress;
     
       
        
  
       public Student(String userName, String password, String email, String userId, String role, int age) {
			super(userName, password, email, userId, role, age);
			this.enrolledCourses = enrolledCourses;
			this.progress = progress;
			 this.enrolledCourses = new ArrayList<>();
			 this.progress = new ArrayList<>();
		}

	   public boolean enrollCourse(Course course) {
    	for(Course e:enrolledCourses) {
    	if(e.getCourseId().equals(course.getCourseId())) {
    		return false;
    	 }
    	}
    	enrolledCourses.add(course);
    	return true;
       }
       public void markLessonCompleted(String courseId,String lessonId) {
			for(Progress e:progress) {
		    	if(e.getLessonId().equals(lessonId)&&e.getCourseId().equals(courseId)) {
		    		return ;
		    	}}
		    	progress.add(new Progress(courseId,lessonId,true));
		    	
		}
		public boolean checkIfLessonCompleted(String courseId,String lessonId) {
			for(Progress e:progress) {
		    	if(e.getLessonId().equals(lessonId)&&e.getCourseId().equals(courseId)) {
		    		return e.isCompleted();
		    	}}
		    	return false;
		}
		public ArrayList<Course> getEnrolledCourses() {
			return enrolledCourses;
		}
		public void setEnrolledCourses(ArrayList<Course> enrolledCourses) {
			this.enrolledCourses = enrolledCourses;
		}
		public ArrayList<Progress> getProgress() {
			return progress;
		}
		public void setProgress(ArrayList<Progress> progress) {
			this.progress = progress;
		}
    
}