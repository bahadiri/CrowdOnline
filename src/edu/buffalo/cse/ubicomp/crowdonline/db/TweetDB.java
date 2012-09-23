package edu.buffalo.cse.ubicomp.crowdonline.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.mysql.jdbc.PreparedStatement;

import edu.buffalo.cse.ubicomp.crowdonline.asker.Question;

public class TweetDB extends DB {

	LinkedHashMap<Long, Integer> knownQNos = new LinkedHashMap<Long,Integer>();

	Twitter twitter;
	public TweetDB() {
		super();
		twitter = TwitterUserDB.getTwitter();
	}

	@Override
	public boolean add(Object o) {
		return add((Status)o);
	}
	
	// this is called when adding replies to RB
	public boolean add(Status s) {
		try{
			String sql = "insert into tweet(status_id,contact,text,related_question_id) values(?,?,?,?)";
			PreparedStatement pStmt = (PreparedStatement) conn.prepareStatement(sql);
			pStmt.setLong(1,s.getId());
			pStmt.setString(2,s.getUser().getScreenName());
			pStmt.setString(3,s.getText());
			pStmt.setInt(4,getRelatedQuestion(s));
			pStmt.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println();
			e.printStackTrace();
			return false;
		}
	}
	
	// this is called when adding own tweets to DB
	public boolean add(Status s, int questionID) {
		String sql = "insert into tweet(status_id,contact,text,related_question_id) values(?,?,?,?)";
		PreparedStatement pStmt;
		try {
			pStmt = (PreparedStatement) conn.prepareStatement(sql);
			pStmt.setLong(1,s.getId());
			pStmt.setString(2,TwitterUserDB.getOwnScreenName());
			pStmt.setString(3,s.getText());
			pStmt.setInt(4,questionID);
			pStmt.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
}

	@Override
	public ArrayList<Object> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLastID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getID(String data) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	// returns 0 if not found a related question
	public int getRelatedQuestion(Status tweet) {
		long statusID = tweet.getInReplyToStatusId();
		String text = tweet.getText();
		
		if( statusID == -1)
		{
			// If was a reply without @, we need to do text parsing to decide question no
			if(text.toLowerCase().contains("kmoi")){
				//if includes a tag or a tag without #
				text = text.substring(text.toLowerCase().indexOf("kmoi")+4);
				return Integer.parseInt(text.substring(0, text.indexOf(" ")));
			} else if(text.toLowerCase().contains("kmoi ")){
				text = text.substring(text.toLowerCase().indexOf("kmoi ")+5);
				return Integer.parseInt(text.substring(0, text.indexOf(" ")));
			} else if(text.matches(".*\\d\\d\\d.*")){
				//if just includes the question no or misspelled tag-finding the number
				Pattern intsOnly = Pattern.compile("\\d\\d\\d+");
				Matcher makeMatch = intsOnly.matcher(text);
				makeMatch.find();
				return Integer.parseInt(makeMatch.group());
			}
			else return 0;
		}
		
		if(knownQNos.containsKey(statusID))
			return ((Integer) knownQNos.get(statusID)).intValue();
		
		try {
			String sql = "select related_question_id from tweet where status_id=" + statusID;
			Statement s = conn.createStatement();	
			ResultSet result = s.executeQuery(sql);
			result.last();
			if(result.getRow() == 0){
				//Probably never reach here but in any case, text parsing to decide question no
				if(text.toLowerCase().contains("kmoi")){
					//if includes a tag or a tag without #
					text = text.substring(text.toLowerCase().indexOf("kmoi")+4);
					return Integer.parseInt(text.substring(0, text.indexOf(" ")));
				} else if(text.toLowerCase().contains("kmoi ")){
					text = text.substring(text.toLowerCase().indexOf("kmoi ")+5);
					return Integer.parseInt(text.substring(0, text.indexOf(" ")));
				} else if(text.matches(".*\\d\\d\\d.*")){
					//if just includes the question no or misspelled tag-finding the number
					Pattern intsOnly = Pattern.compile("\\d\\d\\d+");
					Matcher makeMatch = intsOnly.matcher(text);
					makeMatch.find();
					return Integer.parseInt(makeMatch.group());
				}
				else return 0;
			}
			else {
				knownQNos.put(statusID, result.getInt(1));
				return result.getInt(1);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return -1;
		}
	}
}