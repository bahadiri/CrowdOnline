package edu.buffalo.cse.ubicomp.crowdonline.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import edu.buffalo.cse.ubicomp.crowdonline.collector.Answer;
import edu.buffalo.cse.ubicomp.crowdonline.collector.AnswerComparator;
import edu.buffalo.cse.ubicomp.crowdonline.user.TwitterUser;
import edu.buffalo.cse.ubicomp.crowdonline.user.User;

public abstract class UserDB extends DB {

	public UserDB() {
		super();
	}

	abstract public boolean add(Object o);
	abstract public ArrayList<Object> getAll();
	abstract public Object getLast();
	
	@Override
	public int getID(String contact) {
		try {
			String sql = "select user_id from user where contact='" + contact + "'";
			Statement s = conn.createStatement();			
			ResultSet result = s.executeQuery(sql);
			result.last();
			if(result.getRow() == 0)
				return 0;
			else
				return result.getInt(1);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return -1;
		}
	}
	
	public ArrayList<Answer> getAnswers(User u) {
		ArrayList<Answer> list = new ArrayList<Answer>();
		try {
			String sql = "select * from answer where user_id = " + getID(u.getContact()) ;
			Statement s = conn.createStatement();
			ResultSet result = s.executeQuery(sql);
			if(result.wasNull()) return null;
			while (result.next()) {
				Answer a = new Answer(result.getInt(2),result.getInt(3),result.getString(4).charAt(0));
				a.setTime(result.getTimestamp(5));
				list.add(a);
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		} 
		return list;
	}

	public ArrayList<Answer> getProgramAnswers(User u) {
		ArrayList<Answer> list = new ArrayList<Answer>();
		try {
			// group by question_id is for getting only the first answer for the same question, and sorted too
			String sql = "select * from answer where user_id = " + getID(u.getContact()) + " and question_id > " + DBHandler.getProgramDB().getQuestionIndex() + " group by question_id";
			Statement s = conn.createStatement();
			ResultSet result = s.executeQuery(sql);
			if(result.wasNull()) return null;
			while (result.next()) {
				Answer a = new Answer(result.getInt(2),result.getInt(3),result.getString(4).charAt(0));
				a.setTime(result.getTimestamp(5));
				list.add(a);
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		} 
		//Answers should be coming in order because of group by in sql, so below line can be neglected
		Collections.sort(list,new AnswerComparator());
		return list;
	}
	
	public boolean activate(String contact) {
		try {
			String sql = "update user set active=1 where contact='" + contact + "'";
			Statement s = conn.createStatement();
			s.execute(sql);
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	public boolean deactivate(String contact) {
		try {
			String sql = "update user set active=0 where contact='" + contact + "'";
			Statement s = conn.createStatement();
			s.execute(sql);
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	
}
