package edu.buffalo.cse.ubicomp.crowdonline.user;

import java.util.ArrayList;

import edu.buffalo.cse.ubicomp.crowdonline.collector.Answer;
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
		ArrayList<Answer> answers = DBHandler.getUserDB().getAnswers(this);
		for(Answer a: answers)
			System.out.println(a);
		System.out.println("==== Today's answers ====");
		answers = DBHandler.getUserDB().getProgramAnswers(this);
		for(Answer a: answers)
			System.out.println(a);
		return 0;
	}
//	public void setContact(String contact) {
//		this.contact = contact;
//	}
}
