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
	
	public Answer(String screenName,  int questionId, char choice) {
		super();
		this.time = new Timestamp(Calendar.getInstance().getTime().getTime());
		this.userId = DBHandler.getTwitterUserDB().getID(screenName);
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
	
	public Answer(String screenName, String question, char choice) {
		super();
		this.time = new Timestamp(Calendar.getInstance().getTime().getTime());
		this.userId = DBHandler.getTwitterUserDB().getID(screenName);
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
}
