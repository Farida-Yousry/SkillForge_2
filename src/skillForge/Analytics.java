package skillForge;
import java.util.ArrayList;

public class Analytics {
   
	private QuizManager quizManager;
	private CourseManager courseManager;
	
	public Analytics(QuizManager quizManager, CourseManager courseManager) {
		
		this.quizManager = quizManager;
		this.courseManager = courseManager;
	}

	public int numberOfCompleatedLessons(String studentId,String courseId) {
		 int numOfCompleted = 0;
		Course course = courseManager.getCourseById(courseId);
		if(course==null || course.getLessons() == null)return 0;
		
	   for(Lesson l : course.getLessons()) {
	   if(quizManager.passed(studentId,l.getLessonId()))
		   numOfCompleted++;
	   }
	   return numOfCompleted;
		
	}
	public boolean isLessonPassed(String studentId,String lessonId) {
		return quizManager.passed(studentId, lessonId);
	}
	
	public double percentage(String studentId,String courseId) {
		Course course = courseManager.getCourseById(courseId);
		if(course == null || course.getLessons() == null || course.getLessons().isEmpty())return 0.0;
		
		int totalLessons = course.getLessons().size();
		
		return ((double)numberOfCompleatedLessons(studentId,courseId) / totalLessons) *100;
	}
	public double averageScore(String lessonId) {
		Course c = courseManager.getCourseByLesson(lessonId);
		if(c==null || c.getTrials()==null)return 0.0;
		
		Lesson lesson = null;
		for(Lesson l :c.getLessons()) {
			if(l.getLessonId().equals(lessonId)) {
				lesson = l;
				break;
			}
		}
		int score = 0;
		if(lesson !=null && lesson.getQuiz()!=null && lesson.getQuiz().getQuestions()!=null) {
			score = lesson.getQuiz().getQuestions().size();
		}
		if(score == 0)return 0.0;
		
		int numOfTrials = 0;
		int value = 0;
		for(TakeQuiz q : c.getTrials()) {
			String trialLid = q.getLessonId(); 
			if(trialLid!=null && trialLid.equals(lessonId)) {
				value+=q.getScore();
				numOfTrials++;
			}
		}
		double data = numOfTrials == 0?0.0:((double)value/numOfTrials);
		double averagepercent = data *(100.0/score);
		
		return Math.min(100.0, averagepercent);
	}
	public int highestScore(String lessonId) {
		Course c = courseManager.getCourseByLesson(lessonId);
		if(c==null || c.getTrials() == null)return -1;
		
		int highestScore = -1;
		
		for(TakeQuiz q :c.getTrials()) {
			String trialLid = q.getLessonId();
			if(trialLid!=null && trialLid.equals(lessonId)) {
               if(q.getScore() > highestScore)
            	   highestScore = q.getScore();
			}
		}
		return highestScore;
		
	}
	public int leastScore(String lessonId) {
		Course c = courseManager.getCourseByLesson(lessonId);
		if(c==null || c.getTrials() == null)return -1;
		
		int leastScore = 6;
		
		for(TakeQuiz q :c.getTrials()) {
			String trialLid = q.getLessonId();
			if(trialLid!=null && trialLid.equals(lessonId)) {
               if(q.getScore() < leastScore)
            	   leastScore = q.getScore();
			}
		}
		return leastScore==6?-1:leastScore;
		
	}
	public int getNumberOfTrials(String lessonId) {
		Course c = courseManager.getCourseByLesson(lessonId);
		if(c==null || c.getTrials()== null)return 0;
		
		int numOfTrials = 0;
		for(TakeQuiz q : c.getTrials()) {
			if(q.getLessonId()!= null &&  q.getLessonId().equals(lessonId)) {
			
				numOfTrials++;
			}
		}
		return numOfTrials;
	}
	
	
	
}
