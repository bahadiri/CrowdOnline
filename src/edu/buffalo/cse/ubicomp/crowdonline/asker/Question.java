package edu.buffalo.cse.ubicomp.crowdonline.asker;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import edu.buffalo.cse.ubicomp.crowdonline.asker.QuestionWithoutTime;

public class Question {
	protected int no;
	protected String question;
	protected Timestamp time;
	protected ArrayList<String> choices = new ArrayList<String>(4);
	protected char correctChoice = 'F';
	
	public Question(int no, String question) {
		super();
		this.time = new Timestamp(Calendar.getInstance().getTime().getTime());
		this.no = no;
		this.question = question;
	}
	
	public Question( int no, String question, String choiceA, String choiceB, String choiceC, String choiceD, char correctChoice) {
		super();
		this.time = new Timestamp(Calendar.getInstance().getTime().getTime());
		this.no = no;
		this.question = question;
		this.correctChoice = correctChoice;
		this.choices = new ArrayList<String>(4);
		choices.add(choiceA);
		choices.add(choiceB);
		choices.add(choiceC);
		choices.add(choiceD);
	}
	
	public Question( int no, String question, String choiceA, String choiceB, String choiceC, String choiceD) {
		super();
		this.time = new Timestamp(Calendar.getInstance().getTime().getTime());
		this.no = no;
		this.question = question;
		this.choices = new ArrayList<String>(4);
		choices.add(choiceA);
		choices.add(choiceB);
		choices.add(choiceC);
		choices.add(choiceD);
	}
	public Question( int no, String question,ArrayList<String> choices, char correctChoice) {
		super();
		this.time = new Timestamp(Calendar.getInstance().getTime().getTime());
		this.no = no;
		this.question = question;
		this.choices = choices;
		this.correctChoice = correctChoice;
	}

	public Question( int no, String question, Timestamp time, String choiceA, String choiceB, String choiceC, String choiceD, char correctChoice) {
		super();
		this.time = time;
		this.no = no;
		this.question = question;
		this.correctChoice = correctChoice;
		this.choices = new ArrayList<String>(4);
		choices.add(choiceA);
		choices.add(choiceB);
		choices.add(choiceC);
		choices.add(choiceD);
	}
	
	public Question( int no, String question, Timestamp time, ArrayList<String> choices, char correctChoice) {
		super();
		this.time = time;
		this.no = no;
		this.question = question;
		this.choices = choices;
		this.correctChoice = correctChoice;
	}

	public Question(QuestionWithoutTime qwt) {
		super();
		this.time = new Timestamp(Calendar.getInstance().getTime().getTime());
		this.no = qwt.getNo();
		this.question = qwt.getQuestion();
		this.choices = qwt.getChoices();
		this.correctChoice = qwt.getCorrectChoice();
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

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

	public String getChoiceA() {
		return choices.get(0);
	}
	
	public String getChoiceB() {
		return choices.get(1);
	}
	
	public String getChoiceC() {
		return choices.get(2);
	}
	
	public String getChoiceD() {
		return choices.get(3);
	}
	public char getCorrectChoice() {
		return correctChoice;
	}

	public void setCorrectChoice(char correctChoice) {
		this.correctChoice = correctChoice;
	}
	
	public String writeChoices(){
		char current = 'A';
		String result="";
		for(String s:choices)
			result+=current+++")\t"+s+"\n";
		
		return result;
	}
	
	public String writeChoicesSelectCorrect(){
		char current = 'A';
		String result="";
		for(String s:choices){
			if(current == getCorrectChoice())
				result+="(";
			result+=current+++")\t"+s+"\n";
		}
		return result;
	}
	
	public String writeCorrectChoice(){
		return correctChoice+") "+choices.get(correctChoice-65);
	}
	
	public String toString(){
			return getQuestion()+"\n"+writeChoices();
	}
	
	public void addChoice(String choice) {
		choices.add(choice);
	}
	
	public void addChoices(Collection<String> choices) {
		choices.addAll(choices);
		
	}
	
	public void changeChoice(char choiceIndex, String choice) {
		choices.set(choiceIndex-65, choice);
	}
}