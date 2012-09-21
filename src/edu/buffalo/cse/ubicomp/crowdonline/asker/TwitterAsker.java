package edu.buffalo.cse.ubicomp.crowdonline.asker;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import twitter4j.*;
import twitter4j.auth.*;
import edu.buffalo.cse.ubicomp.crowdonline.db.*;
import edu.buffalo.cse.ubicomp.crowdonline.user.*;
import edu.buffalo.cse.ubicomp.crowdonline.user.User;

public class TwitterAsker extends Asker {
	int messageLength = 140;
	private static DB tudb;
	private Twitter twitter;
	public TwitterAsker() {
		tudb = new TwitterUserDB();
		twitter = TwitterUserDB.getTwitter();
	}
	public TwitterAsker(int index) {
		tudb = new TwitterUserDB();
		twitter = TwitterUserDB.getTwitter();
		this.index = index;
	}
	public long update(String status, int questionID){
		try {
			Status updatedStatus = twitter.updateStatus(status);
			DBHandler.getTweetDB().add(updatedStatus, questionID);
			return updatedStatus.getId();
		} catch (TwitterException te) {
			te.printStackTrace();
			return -1;
		} 
	}
	
	public long update(String status){
		// Enter 0 to question id for non-question tweets
		return update(status,0);
	}

	@Override
	public void ask(Question q) {
		// Ask central
		ArrayList<String> packages = pack(q);

		DBHandler.getQuestionDB().getLastID();
		for(int i = packages.size()-1;i>=0;i--){
			update(packages.get(i),DBHandler.getQuestionDB().getLastID());
		}
		
		//DBHandler.getQuestionDB().setTwitterIDS(twitterIDS);
		// Ask all peers - Won't use this
//		ArrayList twitterUsers = DBHandler.getTwitterUserDB().getAll();
//		for(String message:packages)
//			for(Object tu:twitterUsers){
//				((TwitterUser)tu).sendMessage(message);
//			}
	}
	
	private ArrayList<String> pack(Question q) {
		ArrayList<String> packages = new ArrayList<String>();

		int positionOfSpace,choiceIndex=0;
		String temp, question = "#kmoi"+((index++)+1)+" "+q.getQuestion(), choices = q.writeChoices();

		while(question.length()>messageLength){
			positionOfSpace = messageLength+1;
			while(question.charAt(--positionOfSpace) != ' ');
			packages.add(question.substring(0,positionOfSpace));
			question = question.substring(positionOfSpace);
		}

		if(question.length()<=messageLength && choices.length()<=messageLength && question.length() + choices.length() >messageLength){
			packages.add(question);
			packages.add(choices);
		} else{
			temp = question;
			while(choiceIndex != numOfChoices){
				while(temp.length() + q.choices.get(choiceIndex).length() <messageLength-3){
					temp += "\n" + ((char)(65+choiceIndex)) + ") " +q.choices.get(choiceIndex++);
					if(choiceIndex == numOfChoices) break; 
				}
				packages.add(temp);
				temp = "";
			}
		}

		return packages;
	}
}
