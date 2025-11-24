package skillForge;
import java.util.List;
import java.util.ArrayList;

    public class Student extends User{
        private ArrayList<String> enrolledCourses;
        private  ArrayList<Progress> progress;
        private  ArrayList<Certificate> certificates;
        private String lessonStatus;
     
       
        
  
       public Student(String userName, String password, String email, String userId, String role, int age) {
			super(userName, password, email, userId, role, age);
		
			 this.enrolledCourses = new ArrayList<>();
			 this.progress = new ArrayList<>();
			 this.certificates = new ArrayList<>();
			 this.lessonStatus= "Not Completed";
		}


	   public boolean enrollCourse(String courseId) {
		   //Course course=CourseManager.getCourseById(courseId);
    	for(String e:enrolledCourses) {
    	if(e.equals(courseId)) {
    		return false;
    	 }
    	}
    	//Course course=CourseManager.getCourseById(courseId);
    	enrolledCourses.add(courseId);
    	return true;
       }
       public void markLessonCompleted(String courseId,String lessonId) {
		//	Lesson lesson = enrolledCourses.getLessonById(lessonId);
    	   int i=0;
    	   for(Progress e:progress) {
		    	if(e.getLessonId().equals(lessonId)&&e.getCourseId().equals(courseId)) {
		    		
		    		e.setCompleted(true);
		    		 this.lessonStatus= "Completed";
		    		progress.set(i,e);
		    	}
		    	i++;
		    	}
    	 
    	   saveStudentToDb();
		    	//progress.add(new Progress(courseId,lessonId,true));
		    	
		}
		public boolean checkIfLessonCompleted(String courseId,String lessonId) {
			for(Progress e:progress) {
		    	if(e.getLessonId().equals(lessonId)&&e.getCourseId().equals(courseId)) {
		    		return e.isCompleted();
		    	}}
		    	return false;
		}
		public ArrayList<String> getEnrolledCourses() {
			if(this.enrolledCourses == null) this.enrolledCourses = new ArrayList<>();
			return enrolledCourses;
		}
		public void setEnrolledCourses(ArrayList<String> enrolledCourses) {
			this.enrolledCourses = enrolledCourses;
		}
		public ArrayList<Progress> getProgress() {
			if(this.progress == null) this.progress = new ArrayList<>();
			return progress;
		}
		public void setProgress(ArrayList<Progress> progress) {
			this.progress = progress;
		}

		 public ArrayList<Certificate> getCertificate() {
			 if(this.certificates == null)
				 this.certificates = new ArrayList<>();
				return this.certificates;
			}
		 public void setCertificate(ArrayList<Certificate> certificate) {
				   this.certificates = certificate;
			   }
		 public void addCertificate(Certificate certificate) {
			 if(this.certificates == null)
			this.certificates = new ArrayList<>();
			 if(certificate == null) return;
			this.certificates.add(certificate);
		 }
		 public void saveStudentToDb() {
		        ArrayList<User> users = Database.loadUsers();
		        boolean replaced = false;
		        for (int i = 0; i < users.size(); i++) {
		            if (users.get(i).getUserId().equals(this.getUserId())) {
		            
		                users.set(i, this);
		                replaced = true;
		                break;
		            }
		        }
		        if (!replaced) {
		          
		            users.add(this);
		        }
		        Database.saveToUsersFile(users);
		    }
public void addProgress(Course c) {
	ArrayList<Lesson> l=c.getLessons();
	for(Lesson e:l) {
	progress.add(new Progress(c.getCourseId(),e.getLessonId(),false));}
	
}
public Certificate getCertificateById(String certificateId) {
   for(int i = 0; i < certificates.size(); i++){
        if(certificates.get(i).getCertificateId().equals(certificateId)){
        	return certificates.get(i);
        }
}
    return null;
}
}