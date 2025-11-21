import java.util.ArrayList;

public class Question {

	// attributes
	private String question;
	private ArrayList<String> choices;
	private int correctChoice;
	
	// constructor
	public Question(String question, ArrayList<String> choices, int correctChoice) {
	
		this.question = question;
		this.choices = choices;
		this.correctChoice = correctChoice;
	}
 // setters and getters
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public ArrayList<String> getChoices() {
		return choices;
	}

	public void setChoices(ArrayList<String> choices) {
		this.choices = choices;
	}

	public int getCorrectChoice() {
		return correctChoice;
	}

	public void setCorrectChoice(int correctChoice) {
		this.correctChoice = correctChoice;
	}
	

}
