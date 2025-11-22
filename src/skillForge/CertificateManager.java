package skillForge;
import java.util.ArrayList;
import java.util.Date;

public class CertificateManager {
	// attributes
 private QuizManager qManager;
 private CourseManager cManager;
 private Database db;
 
//constructor
 public CertificateManager(QuizManager qManager, CourseManager cManager, Database db) {
	this.qManager = qManager;
	this.cManager = cManager;
	this.db = db;
 }
 public String generateNewId() {
	String id=String.format("CR%05d",System.currentTimeMillis()%100000);
	 return id;
	
}
 public boolean isCourseCompleted(String courseId,String studentId) {
	Course course= cManager.getCourseById(courseId);
	 ArrayList<Lesson> lessons= course.getLessons();
	 boolean isPassed = true;
	 for(Lesson l : lessons) {
		 if(!qManager.passed(studentId,l.getLessonId())){
				 isPassed= false;
		         break;}
	 }
	 return isPassed;
 }
 public Certificate generateCertificate(String courseId,String studentId) {
	 if(!isCourseCompleted(courseId,studentId))return null;
	 if(getCertificate(courseId,studentId) != null) return null;
	 Certificate c=new Certificate(generateNewId(),studentId,courseId,new Date());
	 ArrayList<User> users = db.loadUsers();
	
     boolean findUser = false;
	 for(User u : users) {
		 if(u.getUserId().equals(studentId)) {
			u.addCertificate(c);
			findUser = true;
			 break;
			 }
	     }
	 if(!findUser)
		 return null;
	 ArrayList<Course> course = db.loadCourses();
	 for(Course cs : course) {
		 if(cs.getCourseId().equals(courseId)) {
			 cs.addCertificate(c);
			 break;
		 }
	 }
		
	 db.saveToUsersFile(users);
	 db.saveToCourseFile(course);
	 
	 Course core = cManager.getCourseById(courseId);
	 if(core != null)
		 core.addCertificate(c);
	 
	 return c;
 }
 
 public Certificate getCertificate(String courseId,String studentId) {
	 ArrayList<User> users = db.loadUsers();
	 for(User u : users) {
		 if(u.getUserId().equals(studentId)) {
	 for(Certificate c: u.getCertificate()) {
		 if(c.getCourseId().equals(courseId)) {
			 return c;
		 }
	 }
		 }
	 }
	 return null;
 }
	
 

}
