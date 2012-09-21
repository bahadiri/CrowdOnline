package edu.buffalo.cse.ubicomp.crowdonline.collector;

import java.util.List;

import edu.buffalo.cse.ubicomp.crowdonline.db.*;
import twitter4j.*;

public class TwitterCollector extends Collector {

	Twitter twitter;
	DB adb;
	public TwitterCollector() {
		super();
		twitter = TwitterUserDB.getTwitter();
		adb = DBHandler.getAnswerDB();
	}

	public void stream(){
		
	}
	@Override
	public String collect(int index) throws TwitterException{
//		List<DirectMessage> messages = twitter.getDirectMessages();
		List<Status> mentions = twitter.getMentions();
		if(mentions.isEmpty())
			return "Alamadım mentionları";
		//DirectMessage message = messages.get(index);
		Status mention = mentions.get(0);
//		return "From: @" + message.getSenderScreenName() + " " + message.getText();
		DBHandler.getTweetDB().add(mention);
		return "From: @" + mention.getUser().getScreenName()+ " " + mention.getText() + " sent by " + mention.getSource() + " as a reply to " + twitter.showStatus(mention.getInReplyToStatusId()).getText() + " " + mention.getInReplyToStatusId();
	}
	
	@Override
	public void run() {
		renewQuestionId();
		//		int counter = 0;
//		while(!stop){
		while(true){
			//			if(counter%1000==0){
			//				counter = 0;
			try {
				//            Paging paging = new Paging(1); === Cok fazla mesaji handle etmek icin paging gerekiyor olabilir
				List<DirectMessage> messages = twitter.getDirectMessages();
				
				for (DirectMessage message : messages) {
					adb.add(new Answer(message.getSenderScreenName(),findQuestionId(message.getText()),findChoice(message.getText())));
					twitter.destroyDirectMessage(message.getId());
				}
				//	return true;
			} catch (TwitterException te) {
				te.printStackTrace();
			//	return false;
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//			} else {
			//				counter++;
			//			}
		}
//		System.out.println("Ha Ha");
//		stop = false;
	//	return true;
	}

	@Override
	public char findChoice(String messageText) {
		// Should be more sophisticated
		return messageText.charAt(0);
	}

	@Override
	public int findQuestionId(String messageText) {
		// Should be more sophisticated
		return getLastQuestionId();
	}
}
