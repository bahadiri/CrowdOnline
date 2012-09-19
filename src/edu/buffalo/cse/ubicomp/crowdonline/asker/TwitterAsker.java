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
	int index = 0;
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
	public boolean update(String status){
		try {
			twitter.updateStatus(status);
			return true;
		} catch (TwitterException te) {
			te.printStackTrace();
			return false;
		} 
	}

	@Override
	public void ask(Question q) {
		// Ask central
		ArrayList<String> packages = pack(q);
		for(int i = packages.size()-1;i>=0;i--){
			update(packages.get(i));
		}
		
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
		String temp, question = "#kmoi"+(index++)+" "+q.getQuestion(), choices = q.writeChoices();

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
