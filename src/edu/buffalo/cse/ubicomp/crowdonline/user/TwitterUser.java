package edu.buffalo.cse.ubicomp.crowdonline.user;

import edu.buffalo.cse.ubicomp.crowdonline.db.*;
import twitter4j.TwitterException;

public class TwitterUser extends User {

	public TwitterUser(String contact) {
		super(contact);
		// TODO Auto-generated constructor stub
	}

	public boolean sendMessage(String message){
		try {
			TwitterUserDB.getTwitter().sendDirectMessage(this.contact, message);
			return true;
		} catch (TwitterException te) {
			te.printStackTrace();
			return false;
		} 
	}

	public boolean follow(){
		// MISSING: Don't send duplicate follow requests to secured users even if they don't accept
		try {
			TwitterUserDB.getTwitter().createFriendship(this.contact);
			return true;
		} catch (TwitterException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean unfollow(){
		try {
			TwitterUserDB.getTwitter().destroyFriendship(this.contact);
			return true;
		} catch (TwitterException e) {
			e.printStackTrace();
			return false;
		}
	}
}
