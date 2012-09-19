package edu.buffalo.cse.ubicomp.crowdonline.asker;
/*
 * This class is needed because edu.buffalo.cse.ubicomp.crowdonline.asker.Question class is actually in server side,
 * and this is instead is used as a template to create questions on the client side.
 */
import java.io.Serializable;
import java.util.ArrayList;

public class QuestionWithoutTime implements Serializable{
	protected int no;
	protected String question;
//	protected Timestamp time;
	protected ArrayList<String> choices = new ArrayList<String>(4);
	protected char correctChoice = 'F';
	
	@SuppressWarnings("unused")
    @Deprecated
    private QuestionWithoutTime() {

    }
	public QuestionWithoutTime(int no, String question) {
		super();
//		this.time = new Timestamp(Calendar.getInstance().getTime().getTime());
		this.no = no;
		this.question = question;
	}
	
	public QuestionWithoutTime( int no, String question, String choiceA, String choiceB, String choiceC, String choiceD, char correctChoice) {
		super();
//		this.time = new Timestamp(Calendar.getInstance().getTime().getTime());
		this.no = no;
		this.question = question;
		this.correctChoice = correctChoice;
		this.choices = new ArrayList<String>(4);
		choices.add(choiceA);
		choices.add(choiceB);
		choices.add(choiceC);
		choices.add(choiceD);
	}
	
	public QuestionWithoutTime( int no, String question, String choiceA, String choiceB, String choiceC, String choiceD) {
		super();
//		this.time = new Timestamp(Calendar.getInstance().getTime().getTime());
		this.no = no;
		this.question = question;
		this.choices = new ArrayList<String>(4);
		choices.add(choiceA);
		choices.add(choiceB);
		choices.add(choiceC);
		choices.add(choiceD);
	}
	public QuestionWithoutTime( int no, String question,ArrayList<String> choices, char correctChoice) {
		super();
//		this.time = new Timestamp(Calendar.getInstance().getTime().getTime());
		this.no = no;
		this.question = question;
		this.choices = choices;
		this.correctChoice = correctChoice;
	}

	public int getNo() {
		return no;
	}

	public String getQuestion() {
		return question;
	}

	public ArrayList<String> getChoices() {
		return choices;
	}

	public char getCorrectChoice() {
		return correctChoice;
	}
}