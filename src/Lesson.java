import java.util.ArrayList;

public class Lesson {
	
		// Attributes
		private String title;
		private String lessonId;
		private String content;
		private ArrayList<String> resources; 
		
	  // constructor
		public Lesson(String lessonId,String title,String content,ArrayList<String> resources) {
		
			this.lessonId=lessonId;
			this.title=title;
			this.content=content;
			if(resources==null) {
				this.resources=new ArrayList<>();
			}
			else
			this.resources=new ArrayList<>(resources);
   }
	// setters
		public void setContent(String content) {
			this.content=content;
		}
		public void  setTitle(String title) {
			this.title=title;
		}
		public void setResources(ArrayList<String> resources) {
			if(resources==null) {
				this.resources=new ArrayList<>();
			}
			else
			this.resources=new ArrayList<>(resources);
		}
		
	// getters
		public String getLessonId() {
			return lessonId;
		}
		public String getTitle() {
			return title;
		}
		public String getContent() {
			return content;
		}
		public ArrayList<String> getResources() {
			return resources;
		}
		
		public String toString() {
			return lessonId+"-"+title;
		}
}
		// methods
		// course mangement
	/*	public void addLesson(Lesson lesson) {
			records.add(lesson);
			saveToFile();	
		}
		public boolean deleteLesson(Lesson lesson) {
			for(int i=0;i<records.size();i++) {
			if(records.get(i).getlessonId().equals(lesson.getlessonId())) {
			records.remove(i);
			saveToFile();
			return true;
			}}
			return false;
		}
		public boolean editLesson(Lesson updatedLesson) {
			for(int i=0;i<records.size();i++) {
				if(records.get(i).getlessonId().equals(updatedLesson.getlessonId())) {
				records.set(i,updatedLesson);
				saveToFile();
				return true;
				}}
				return false;
		}
		public ArrayList<Lesson> fetchLesson(){
			return lessons;
		}
		public Lesson getLessonById(String lessonId) {
			for(int i=0;i<records.size();i++) {
				if(records.get(i).getlessonId().equals(getlessonId())){
					return records.get(i);
				}
			}
			return null;	
		}
		
		// student mangement
		public void markLessonCompleted(String lessonId) {
			if(!progress.contains(lessonId))
				progress.add(lessonId);
		}
		public boolean checkIfLessonCompleted(String lessonId) {
			return progress.contains(lessonId);
		}
		public ArrayList<String> getprogress(){
			return progress;
		}
*/
