package edu.buffalo.cse.ubicomp.crowdonline.db;

import java.sql.SQLException;
import java.util.ArrayList;

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
			String sql = "insert into answer(user_id,question_id,choice,time) values(?,?,?,?)";
			PreparedStatement pStmt = (PreparedStatement) conn.prepareStatement(sql);
			pStmt.setInt(1, a.getUserId());
			pStmt.setInt(2, a.getQuestionId());
			pStmt.setString(3, ""+a.getChoice());
			pStmt.setTimestamp(4, a.getTime());
			pStmt.execute();
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

}
