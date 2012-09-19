import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;

import twitter4j.TwitterException;

import edu.buffalo.cse.ubicomp.crowdonline.asker.*;
import edu.buffalo.cse.ubicomp.crowdonline.collector.*;
import edu.buffalo.cse.ubicomp.crowdonline.db.*;
import edu.buffalo.cse.ubicomp.crowdonline.decider.*;
import edu.buffalo.cse.ubicomp.crowdonline.user.*;

public class Main {

	public static void main(String[] args) {
		int questionNo = 1;
		String questionPhrase, choiceA, choiceB, choiceC, choiceD;

		Collector collector = new TwitterCollector();
		Asker asker = new TwitterAsker(DBHandler.getQuestionDB().getID("randomStringXqwTewjbA32as5ZZx' or '1'='1"));
		Question q;
		try {
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in, "UTF8"));
			while (true) {
				System.out.println("Enter question phrase please(Q for ending the game, N for new contestant): ");
				questionPhrase = inputReader.readLine();
				if (questionPhrase.toUpperCase().equals("Q")){
					break;
				}
				else if (questionPhrase.toUpperCase().equals("N")){
					questionNo = 1;
					System.out.println("Enter question phrase please: ");
					questionPhrase = inputReader.readLine();
				} else if (questionPhrase.toUpperCase().equals("C")){
					System.out.println(collector.collect(0));
					break;
				}
				System.out.print("Enter choices please");
				System.out.print("\nA) ");
				choiceA = inputReader.readLine();
				System.out.print("\nB) ");
				choiceB = inputReader.readLine();
				System.out.print("\nC) ");
				choiceC = inputReader.readLine();
				System.out.print("\nD) ");
				choiceD = inputReader.readLine();

				q = new Question(questionNo, questionPhrase, choiceA, choiceB,choiceC, choiceD);
				System.out.println(q);
				
				DBHandler.getQuestionDB().add(q);
				asker.ask(q);
				// collector.collect();
				questionNo++;
			}
		} catch (IOException e) {
			System.out.println("IO exception");
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
