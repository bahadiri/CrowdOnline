package edu.buffalo.cse.ubicomp.crowdonline.db;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import com.mysql.jdbc.PreparedStatement;

import edu.buffalo.cse.ubicomp.crowdonline.asker.Question;
import edu.buffalo.cse.ubicomp.crowdonline.collector.Answer;

public class AnswerDB extends DB {

	public AnswerDB() {
		super();
	}

	@Override
	public boolean add(Object o) {
		return add((Answer)o);
	}
	
	public boolean add(Answer a) {
		try{
			/*
			String sql = "update answer set choice = ?, time = ? where user_id = ? and question_id = ?;";
			PreparedStatement pStmt = (PreparedStatement) conn.prepareStatement(sql);
			pStmt.setString(1, ""+a.getChoice());
			pStmt.setTimestamp(2, a.getTime());
			pStmt.setInt(3, a.getUserId());
			pStmt.setInt(4, a.getQuestionId());
			if(pStmt.executeUpdate() == 0)
			{*/
				String sql = "insert into answer(user_id,question_id,choice,time) values(?,?,?,?)";
				PreparedStatement pStmt2 = (PreparedStatement) conn.prepareStatement(sql);
				pStmt2.setInt(1, a.getUserId());
				pStmt2.setInt(2, a.getQuestionId());
				pStmt2.setString(3, ""+a.getChoice());
				pStmt2.setTimestamp(4, a.getTime());
				pStmt2.execute();
			//}
			return true;
		}catch(SQLException e){
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
	public int getID(String data) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLastID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
