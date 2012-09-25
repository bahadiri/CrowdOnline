package edu.buffalo.cse.ubicomp.crowdonline.user;

import java.util.ArrayList;
import java.util.SortedMap;

import edu.buffalo.cse.ubicomp.crowdonline.collector.Answer;
import edu.buffalo.cse.ubicomp.crowdonline.collector.CorrectAnswer;
import edu.buffalo.cse.ubicomp.crowdonline.db.DBHandler;

public abstract class User {
	String contact;
	
	public User(String contact){
		this.contact = contact;
	}
	
	abstract public boolean sendMessage(String message);
	
	public String getContact() {
		return contact;
	}

	public int calculatePrize() {
//		System.out.println("==== Today's answers ====");
		//Answers are sorted by question id
		ArrayList<Answer> answers = DBHandler.getUserDB().getProgramAnswers(this);
		if(answers == null)
			return 0;
		SortedMap<Integer,CorrectAnswer> correctAnswers = DBHandler.getQuestionDB().getProgramAnswerKey();
		int lastIndex = 0, totalPrize = 0, level = 0;
		for(Answer a: answers){
			System.out.println("User's answer: " + a);
			System.out.println("Correct answer: " + correctAnswers.get(a.getQuestionId()));
			if(correctAnswers.get(a.getQuestionId()).getNo() == 1)
				lastIndex = a.getQuestionId() - 1;
			if(a.getChoice() == correctAnswers.get(a.getQuestionId()).getChoice() && lastIndex == (a.getQuestionId()-1)){
				level = correctAnswers.get(a.getQuestionId()).getNo();
				lastIndex = a.getQuestionId();
			} else {
				System.out.println(level);
				// To avoid giving a prize twice for 3rd and 5th levels in a situation like TTTFTF
				// He should get only 3rd level prize in a contest with 6 questions like that
				if(level == correctAnswers.get(a.getQuestionId()).getNo()-1)
					totalPrize += prizeFor(level);
				level = 0;
			}
		}
		//Once more for the last question because prizes are post processed
		if(level > 0 )
			totalPrize += prizeFor(level);
		return totalPrize;
	}
	
	private int prizeFor(int level) {
		switch(level){
		case 1:
			return 500;
		case 2:
			return 1000;
		case 3:
			return 2000;
		case 4:
			return 3000;
		case 5: 
			return 5000;
		case 6:
			return 7500;
		case 7: 
			return 15000;
		case 8:
			return 30000;
		case 9:
			return 60000;
		case 10:
			return 125000;
		case 11:
			return 250000;
		case 12:
			return 1000000;
		default:
			return 0;
		}
	}
//	public void setContact(String contact) {
//		this.contact = contact;
//	}
}
