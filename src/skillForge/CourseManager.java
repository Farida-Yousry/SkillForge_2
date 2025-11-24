package skillForge;
import java.util.ArrayList;

public class CourseManager{
	 private static ArrayList<Course> courses;
	 private Database db;

	
	 
	 
	public CourseManager(Database db) {
		this.db = db;
		this.courses = db.loadCourses();
		if(this.courses==null) {
			this.courses=new ArrayList<>();
			}
		}
    public String generateNewId() {
	String id=String.format("C%05d",System.currentTimeMillis()%100000);
	 return id;
	
}
	public void createCourse(String title,String description,String instructorId) {
		courses.add(new Course(generateNewId(),title,description,instructorId,"PENDING"));
		db.saveToCourseFile(courses);
	}
	
	 public boolean editCourse(String courseId,String title,String description) {
			for(int i=0;i<courses.size();i++) {
				if(courses.get(i).getCourseId().equals(courseId)) {
				courses.get(i).setTitle(title);
				courses.get(i).setDescription(description);
				db.saveToCourseFile(courses);
				return true;
				
				}}
			return false;	
		}
	    public boolean removeCourse(String courseId){
	        for(int i = 0; i < courses.size(); i++){
	            if(courses.get(i).getCourseId().equals(courseId)){
	                courses.remove(i);  
	                db.saveToCourseFile(courses);
	                return true;
	            }
	        }
	        return false;
	    }
	    public static Course getCourseById(String courseId) {
	        for(Course c : courses){
	            if(c.getCourseId().equals(courseId)){
	            	return c;
	            }
	    }
	        return null;
}
	    public ArrayList<Course> getCourseByInstructor(String instructorId) {
	    	ArrayList<Course> instCourses=new ArrayList<>();
	        for(int i = 0; i < courses.size(); i++){
	            if(courses.get(i).getInstructorId().equals(instructorId)){
	            	instCourses.add(courses.get(i));
	            }
	    }
	        return instCourses;
}
	    public ArrayList<Course> getAllCourses(){
	    	return courses;
	    }
	    public boolean deleteLessonFromCourse(String courseId,String lessonId) {
	    	Course c= getCourseById(courseId);
	    	if(c== null)return false;
	    	
	    		boolean isDeleted=c.deleteLesson(lessonId);
	    		if(isDeleted) {
	    			db.saveToCourseFile(courses);
	    		}
	    	
	    	return isDeleted;
	    }
	    public boolean addLessonToCourse(String courseId,Lesson lesson) {
	    	if(lesson==null||lesson.getLessonId()==null)return false;
	    	Course c= getCourseById(courseId);
	    	if(c == null)return false;
	    	
	    		boolean isAdded=c.getLessonById(lesson.getLessonId())== null;
	    		if(isAdded) {
	    			c.addLesson(lesson);
	    			db.saveToCourseFile(courses);
	    		}
	    	
	    	return isAdded;
	    }
	    public boolean enrollStudent(String courseId,String studentId) {
	    	Course c= getCourseById(courseId);
	    	if(c== null)return false;
	    	boolean enrolled=false;
	    	if(!c.getEnrolledStudents().contains(studentId) && c.getStatus().equals("APPROVED")) {
	    	c.enrollStudent(studentId);
	    	
			db.saveToCourseFile(courses);
			enrolled =true;
	    }
	    	return enrolled;
	    }
	    public ArrayList<User> getEnrolledStudents(String courseId){
	    	Course c = getCourseById(courseId);
	    	ArrayList<User> enrolled = new ArrayList<>();
	    	for(String studentId : c.getEnrolledStudents()) {
	    		User u = db.getUserById(studentId);
	    		if(u != null)
	    			enrolled.add(u);
	    	}
	    	return enrolled;
	    }
	    public ArrayList<Course> viewApprovedCourses(){
	    	ArrayList<Course> approvedCourses = new ArrayList<>();
	    	for(Course c : courses) {
	    		if(c.getStatus().equals("APPROVED")) {
	    			approvedCourses.add(c);}
	    	}
	    	return approvedCourses;
	    }
	    public ArrayList<Course> viewPendingCourses(){
	    	ArrayList<Course> pendingCourses = new ArrayList<>();
	    	ArrayList<Course> allCourses = getAllCourses();
	    	for(Course c : allCourses) {
	    		if(c!=null && c.getStatus() !=null && c.getStatus().equals("PENDING"))
	    		
	    			pendingCourses.add(c);
	    	}
	    	return pendingCourses;
	    }
	    public boolean addTrials(String courseId,TakeQuiz quiz) {
	    	Course c = getCourseById(courseId);
	    	if(c == null) return false;
	    	c.addTrial(quiz);
	    	db.saveToCourseFile(courses);
	    	return true;
	    }
	    public Course getCourseByLesson(String lessonId) {
	    if(lessonId == null) return null;
	    	for(Course c : courses) {
	    		if(c.getLessonById(lessonId) == null)
	    			continue;
	    	for(Lesson l : c.getLessons()) {
	    		if(l!=null && l.getLessonId().equals(lessonId))
	    			return c;
	    	}
	    		
	    	}
	    	return null;
	    }
		 public void addCertificateToCourse(Certificate certificate,String courseId) {
			Course c =  getCourseById(courseId);
			if(c == null)
				return ;
			c.addCertificate(certificate);
			db.saveToCourseFile(courses);
			
		 }
		 public boolean canAccess(Student student,String courseId,String lessonId) {
			Course course=getCourseById(courseId);
			if(course==null)return false;
			ArrayList<Lesson> lessons=course.getLessons();
			for(int i=0;i<lessons.size();i++) {
				Lesson l=lessons.get(i);
				if(l.getLessonId().equals(lessonId)) {
					if(i==0)return true;
					Lesson previous=lessons.get(i-1);
					return student.checkIfLessonCompleted(courseId, previous.getLessonId());
				}
			}
			 
			 
			 
			 return false;
			 
		 }
	  
}