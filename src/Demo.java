import java.util.ArrayList;

import edu.buffalo.cse.ubicomp.crowdonline.asker.*;
import edu.buffalo.cse.ubicomp.crowdonline.collector.*;
import edu.buffalo.cse.ubicomp.crowdonline.db.*;
import edu.buffalo.cse.ubicomp.crowdonline.decider.*;
import edu.buffalo.cse.ubicomp.crowdonline.user.*;

public class Demo {

	public static void main(String[] args) {
		DB qdb = new QuestionDB();
		Asker asker = new TwitterAsker();
//		Collector collector = new TwitterCollector();

		ArrayList<Question> questions= new ArrayList<Question>(); 

		Question q = new Question(1, "Who is WOZ?");
		q.addChoice("Steve Jobs");
		q.addChoice("Steve Wozniak");
		q.addChoice("Bill Gates");
		q.addChoice("Woza Albert");
		q.setCorrectChoice('B');
		questions.add(q);
		//Trial comments
		//System.out.println(qdb.add(q));
		//System.out.println(qdb.getLast());
		//TwitterAsker ta = new TwitterAsker();
		//ta.update("Sent via Java");
		
		q = new Question(2, "Homeowners buy surge protectors to protect their possessions from unexpected surges of what?","Electric current","Water flow","Air pressure","Buyer's remorse",'A');
		qdb.add(q);
		questions.add(q);
		q = new Question(3, "Which of these U.S. Presidents appeared on the television series \"Laugh-in\"?","Lyndon Johnson","Richard Nixon","Jimmy Carter","Gerald Ford",'B');
		qdb.add(q);
		questions.add(q);
		q = new Question(4, "In an experiment popularized online, what candy creates an explosive geyser when dropped into a 2-liter Diet Coke bottle?","Skittles","Mint Mentos","Atomic Fireballs","Lemon Heads",'B');
		qdb.add(q);
		questions.add(q);
		q = new Question(5, "Hangisi balonun mucididir?","Wright Kardesler","Mongolfier Kardesler","Karamazov Kardesler","Uygur Kardesler",'B');
		qdb.add(q);
		questions.add(q);
		q = new Question(6, "@bahadirismail How are you doing?","Happy as a sunshine","Cool","Eegh...OK","Bad",'B');
		qdb.add(q);
		questions.add(q);
		q = new Question(7, "Painted in 1893, the famous Edvard Munch painting \"The Scream\" depicts an agodized figure holding his hands where?","At each side of his face","Folded across his chest","Above his head","Behind hsi back",'A');
		DBHandler.getQuestionDB().add(q);
		questions.add(q);
		q = new Question(8, "Which of these historical names can be spelled out by writing three U.S. state abbreviations in a row?","STALIN","CAESAR","GANDHI","DARWIN",'C');
		qdb.add(q);
		questions.add(q);
		
		for(Question qu:questions){
		//	asker.ask((Question)DBHandler.getQuestionDB().getLast());
			System.out.println(qu);
			asker.ask(qu);
			try {
				Thread.sleep(45000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		collector.collect();
//		System.out.println(((Question)qdb.getLast()).getChoices());
	}

}
