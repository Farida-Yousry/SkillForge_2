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
		ArrayList<Lesson> lesson = course.getLessons();
	   for(Lesson l : lesson) {
	   if(quizManager.passed(studentId,l.getLessonId()))
		   numOfCompleted++;
	   }
	   return numOfCompleted;
		
	}
	public double percentage(String studentId,String courseId) {
		Course course = courseManager.getCourseById(courseId);
		ArrayList<Lesson> lesson = course.getLessons();
		int totalLessons = lesson.size();
		
		return ((double)numberOfCompleatedLessons(studentId,courseId) / totalLessons) *100;
	}
	public double averageScore(String lessonId) {
		ArrayList<TakeQuiz> trials = quizManager.getTrials();
		int score = 0;
		int numOfTrials = 0;
		for(TakeQuiz q : trials) {
			if(q.getLessonId().equals(lessonId)) {
				score+=q.getScore();
				numOfTrials++;
			}
		}
		if(numOfTrials == 0) return 0;
		
		return (double)score/numOfTrials;
	}
	public int highestScore(String lessonId) {
		ArrayList<TakeQuiz> trials = quizManager.getTrials();;
		int highestScore = 0;
		if(trials.size() == 0)return -1;
		for(int i = 0;i<trials.size();i++) {
			if(trials.get(i).getLessonId().equals(lessonId)) {
               if(trials.get(i).getScore() > highestScore)
            	   highestScore = trials.get(i).getScore();
			}
		}
		return highestScore;
		
	}
	public int leastScore(String lessonId) {
		ArrayList<TakeQuiz> trials = quizManager.getTrials();;
		int leastScore = 100;
		if(trials.size() == 0)return -1;
		for(int i = 0;i<trials.size();i++) {
			if(trials.get(i).getLessonId().equals(lessonId)) {
               if(trials.get(i).getScore() < leastScore)
            	   leastScore = trials.get(i).getScore();
			}
		}
		return leastScore;
	}
	public int getNumberOfTrials(String lessonId) {
		ArrayList<TakeQuiz> trials = quizManager.getTrials();;
		int numOfTrials = 0;
		for(TakeQuiz q : trials) {
			if(q.getLessonId().equals(lessonId)) {
			
				numOfTrials++;
			}
		}
		return numOfTrials;
	}
	
	
	
}
