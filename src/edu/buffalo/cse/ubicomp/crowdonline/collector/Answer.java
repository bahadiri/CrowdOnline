package edu.buffalo.cse.ubicomp.crowdonline.collector;

import java.sql.Timestamp;
import java.util.Calendar;

import edu.buffalo.cse.ubicomp.crowdonline.db.DBHandler;

public class Answer {
	protected int userId;
	protected int questionId;
	protected Timestamp time;
	protected char choice;
	
	public Answer(int userId, int questionId, char choice) {
		super();
		this.time = new Timestamp(Calendar.getInstance().getTime().getTime());
		this.userId = userId;
		this.questionId = questionId;
		this.choice = choice;
	}
	
	public Answer(String contact,  int questionId, char choice) {
		super();
		this.time = new Timestamp(Calendar.getInstance().getTime().getTime());
		this.userId = DBHandler.getUserDB().getID(contact);
		this.questionId = questionId;
		this.choice = choice;
	}
	
	public Answer(int userId, String question, char choice) {
		super();
		this.time = new Timestamp(Calendar.getInstance().getTime().getTime());
		this.userId = userId;
		this.questionId = DBHandler.getQuestionDB().getID(question);
		this.choice = choice;
	}
	
	public Answer(String contact, String question, char choice) {
		super();
		this.time = new Timestamp(Calendar.getInstance().getTime().getTime());
		this.userId = DBHandler.getUserDB().getID(contact);
		this.questionId = DBHandler.getQuestionDB().getID(question);
		this.choice = choice;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public char getChoice() {
		return choice;
	}

	public void setChoice(char choice) {
		this.choice = choice;
	}

	public static char parseChoice(String text) {
		String preText = text;
		text = text.toUpperCase();
		// delete all mention strings
		text = text.replaceAll("@.+ ", " ").replaceAll(" @.+", " ");
		// delete all hashtags
		text = text.replaceAll("#.+ ", " ").replaceAll(" #.+", " ");
		// delete hashtags written without #
		text = text.replaceAll("KMOI.* ", " ").replaceAll(" KMOI.*", " ");
		// delete all numbers
		text = text.replaceAll("[0-9]+ ", " ").replaceAll(" [0-9]+", " ");
		// delete all whitespaces
		text = text.replaceAll(" ", "");
		if(text.length() != 1){
			System.out.println("There is a problem with answer \"" + preText + "\"");
			return 'F';
		} else
			return text.charAt(0);
	}

	@Override
	public String toString() {
		return "Answer [userId=" + userId + ", questionId=" + questionId
				+ ", time=" + time + ", choice=" + choice + "]";
	}	
}