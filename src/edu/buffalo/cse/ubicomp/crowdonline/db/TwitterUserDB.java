package edu.buffalo.cse.ubicomp.crowdonline.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;

import twitter4j.*;
import twitter4j.User;
import twitter4j.auth.*;
import edu.buffalo.cse.ubicomp.crowdonline.user.*;

public class TwitterUserDB extends UserDB {

	private static String consumerKey = "VSu9z0Bgf1HFtPyUKJTzUw";
	private static String consumerSecret = "2V55ZDMWUjxrOQjDzPhvN9ifPm63tQ7N7oZVyuFhuHI";
	private static String accessTokenStr = "282403196-XScSAQ08ou4vj94N7GcUcBkitujvo8E259EUH1Yb";
	private static String accessTokenSecret = "S3R22k1QuNGXDClzrDR1AFIO6AunUwsmi9y8x4BdzHM";
	private static Twitter twitter = setTwitter();
	private static AccessToken accessToken;
	
	public TwitterUserDB() {
		super();
		this.updateUsers();
	}

	private static Twitter setTwitter(){
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
		accessToken = new AccessToken(accessTokenStr,accessTokenSecret);
		twitter.setOAuthAccessToken(accessToken);
		return twitter;
	}
	public static Twitter getTwitter() {
		return twitter;
	}

	public static AccessToken getAccessToken() {
		return accessToken;
	}

	public void updateUsers() {
		// Get user list from Twitter
		ArrayList<twitter4j.User> TwitterList = new ArrayList<twitter4j.User>();
		ArrayList DBList;
		ArrayList<TwitterUser> newUsers= new ArrayList<TwitterUser>(); 
		ArrayList<TwitterUser> oldUsers= new ArrayList<TwitterUser>(); 
		int indexDB = 0, indexTwitter = 0, sizeDB, sizeTwitter, comparison;
		long cursor = -1;
		IDs ids;

		try {
			do {
				ids = twitter.getFollowersIDs(cursor);
				TwitterList.addAll(twitter.lookupUsers(ids.getIDs()));
				//				for (twitter4j.User tu : TwitterList) {
				//					System.out.println(tu);
				//				//	twitter.sendDirectMessage(tu.getScreenName(), "Hello "+tu.getName());
				//				}
			} while ((cursor = ids.getNextCursor()) != 0);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		// Get users from DB
		DBList = this.getAll(); 
		// Compare users in DB and Twitter
		// Create lists for new followers, old(was following but not following anymore) followers, deactivated followers
		//		for(Object dbu:DBList)
		//			DBContactList.add(((TwitterUser)dbu).getContact());
		//		for(twitter4j.User tu:TwitterList)
		//			TwitterContactList.add(tu.getScreenName());
		Collections.sort(DBList, new DBComparator());
		Collections.sort(TwitterList, new TwitterComparator());
		sizeDB = DBList.size();
		sizeTwitter = TwitterList.size();
		while(indexDB<sizeDB){
			if(indexTwitter == sizeTwitter){
				//Twitter List finished while still there are some records in DB
				oldUsers.addAll(DBList.subList(indexDB, sizeDB));
				break;
			}
			comparison = TwitterList.get(indexTwitter).getScreenName().compareTo(((TwitterUser)(DBList.get(indexDB))).getContact());
			if(comparison == 0){ // A record which is already in DB
				indexDB++;
				indexTwitter++;
			} else if(comparison<0){
				// a new user from Twitter not in DB -- so add it to new users list to process later
				newUsers.add(new TwitterUser(TwitterList.get(indexTwitter).getScreenName()));
				indexTwitter++;
			} else { //comparison > 0
				// a user in DB not in Twitter anymore --  so add it to old users list to process later
				oldUsers.add((TwitterUser)(DBList.get(indexDB)));
				indexDB++;
			}
		}
		for(;indexTwitter<sizeTwitter;indexTwitter++)
			newUsers.add(new TwitterUser(TwitterList.get(indexTwitter).getScreenName()));

		// Add new followers or Activate deactivated new followers
		if(!newUsers.isEmpty()){
			ArrayList<String> deactivatedUserContacts = getDeactivatedUserContacts(); 
			for(TwitterUser nu:newUsers){
				nu.follow();
				if(deactivatedUserContacts.contains(nu.getContact()))
					activate(nu.getContact());
				else
					add(nu);
			}
		}
		// Deactivate old followers
		if(!oldUsers.isEmpty()){
			for(TwitterUser ou:oldUsers){
				ou.unfollow();
				deactivate(ou.getContact());
			}
		}
	}

	@Override
	public boolean add(Object o) {
		try {
			String sql = "insert into user(contact_type,contact,date) values(?,?,?)";
			PreparedStatement pStmt = (PreparedStatement) conn.prepareStatement(sql);
			pStmt.setString(1, "T");
			pStmt.setString(2, ((TwitterUser)o).getContact());
			pStmt.setTimestamp(3, new Timestamp(Calendar.getInstance().getTime().getTime()));
			pStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Actually get all active ones
	@Override
	public ArrayList<Object> getAll() {
		ArrayList list = new ArrayList();
		try {
			String sql = "select * from user where contact_type= 'T' AND active = 1";
			Statement s = conn.createStatement();
			ResultSet result = s.executeQuery(sql);
			while (result.next()) {
				list.add(new TwitterUser(result.getString(3)));
			}
			System.out.println("Successfully get all active twitter users");
		}  catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}

	public ArrayList<Object> getAllIncludingDeactivated() {
		ArrayList list = new ArrayList();
		try {
			String sql = "select * from user where contact_type= 'T'";
			Statement s = conn.createStatement();
			ResultSet result = s.executeQuery(sql);
			while (result.next()) {
				list.add(new TwitterUser(result.getString(3)));
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}

	public ArrayList<TwitterUser> getDeactivatedUsers() {
		ArrayList<TwitterUser> list = new ArrayList<TwitterUser>();
		try {
			String sql = "select * from user where contact_type= 'T' AND active = 0";
			Statement s = conn.createStatement();
			ResultSet result = s.executeQuery(sql);
			while (result.next()) {
				list.add(new TwitterUser(result.getString(3)));
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}

	public ArrayList<String> getDeactivatedUserContacts() {
		ArrayList<String> list = new ArrayList<String>();
		try {
			String sql = "select * from user where contact_type= 'T' AND active = 0";
			Statement s = conn.createStatement();
			ResultSet result = s.executeQuery(sql);
			while (result.next()) {
				list.add(result.getString(3));
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}

	@Override
	public Object getLast() {
		// TODO Auto-generated method stub
		return null;
	}
}

class TwitterComparator implements Comparator<twitter4j.User>{
	@Override
	public int compare(twitter4j.User user0, twitter4j.User user1) {
		return user0.getScreenName().compareTo(user1.getScreenName());
	}
}

class DBComparator implements Comparator<Object>{
	@Override
	public int compare(Object user0, Object user1) {
		return ((TwitterUser)user0).getContact().compareTo( ((TwitterUser)user1).getContact());
	}
}