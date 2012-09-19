package edu.buffalo.cse.ubicomp.crowdonline.db;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import edu.buffalo.cse.ubicomp.crowdonline.asker.*;

public class QuestionDB extends DB {

	public QuestionDB() {
		super();
	}
	
	public boolean add(Question q){
		try{
			String sql = "insert into question(no,question,time,choiceA,choiceB,choiceC,choiceD,correctChoice) values(?,?,?,?,?,?,?,?)";
			PreparedStatement pStmt = (PreparedStatement) conn.prepareStatement(sql);
			pStmt.setInt(1, q.getNo());
			pStmt.setString(2, q.getQuestion());
			pStmt.setTimestamp(3,q.getTime());
			pStmt.setString(4,q.getChoiceA());
			pStmt.setString(5,q.getChoiceB());
			pStmt.setString(6,q.getChoiceC());
			pStmt.setString(7,q.getChoiceD());
			pStmt.setString(8,""+q.getCorrectChoice());
			pStmt.execute();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean add(Object o) {
		return add((Question)o);
	}

	@Override
	public Object getLast() {
		Question q = null;
		try {
			String sql = "select * from question order by question_id desc limit 1";
			Statement s = conn.createStatement();			
			ResultSet result = s.executeQuery(sql);
			result.last();
			q = new Question(result.getInt(2), result.getString(3), result.getTimestamp(4), result.getString(5), result.getString(6), result.getString(7),  result.getString(8), result.getString(9).charAt(0));
		} catch(SQLException e){
			e.printStackTrace();
		}
		return q;
	}

	@Override
	public ArrayList<Object> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getID(String question) {
		try {
			String sql = "select question_id from question where question='" + question + "'";
			Statement s = conn.createStatement();			
			ResultSet result = s.executeQuery(sql);
			result.last();
			if(result.getRow() == 0){
				return 0;
			}
			else
				return result.getInt(1);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return -1;
		}
	}

}
