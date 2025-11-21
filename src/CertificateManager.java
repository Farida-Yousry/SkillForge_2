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
	 ArrayList<Lesson> lessons= course.fetchLesson();
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
	 Certificate c=new Certificate(generateNewId(),studentId,courseId,new Date());
	 //db.saveToUsersFile(c);
	 return c;
 }
 public Certificate getCertificate(String courseId,String studentId) {
	 // method to be added in database
	 ArrayList<Certificate> certificates=db.getCertificatesForStudent(studentId);
	 for(Certificate c: certificates) {
		 if(c.getCourseId().equals(courseId)) {
			 return c;
		 }
	 }
	 return null;
 }
	
 

}
