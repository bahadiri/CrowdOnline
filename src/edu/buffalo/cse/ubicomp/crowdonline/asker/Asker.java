package edu.buffalo.cse.ubicomp.crowdonline.asker;

import twitter4j.TwitterException;
import edu.buffalo.cse.ubicomp.crowdonline.collector.Collector;
import edu.buffalo.cse.ubicomp.crowdonline.db.DBHandler;
import edu.buffalo.cse.ubicomp.crowdonline.asker.QuestionWithoutTime;

public abstract class Asker implements Runnable{
	int numOfChoices = 4;
	int index = DBHandler.getQuestionDB().getLastID();
	public void askQuestion(Question q){
		Collector.setLastQuestionId(DBHandler.getQuestionDB().getLastID());
		ask(q);
	}
	abstract public void ask(Question q);
	public void run(){
		
	}
	public void askFromServer(QuestionWithoutTime qwt) {
		DBHandler.getQuestionDB().add(new Question(qwt));
		ask((Question)DBHandler.getQuestionDB().getLast());
	}
}
