/**
 * 
 */
package edu.buffalo.cse.ubicomp.crowdonline.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import com.mysql.jdbc.PreparedStatement;

/**
 * @author newadmin
 *
 */
public class ProgramDB extends DB {

	/**
	 * 
	 */
	private int programID = 0, questionIndex = DBHandler.getQuestionDB().getLastID();
	
	public int getQuestionIndex() {
		//return questionIndex;
		return 133;
	}

	public int getProgramID() {
		return programID;
	}

	public ProgramDB() {
		super();
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.ubicomp.crowdonline.db.DB#add(java.lang.Object)
	 */
	@Override
	public boolean add(Object o) {
		try{
			String sql = "insert into program(start_time) value(?)";
			PreparedStatement pStmt = (PreparedStatement) conn.prepareStatement(sql);
			pStmt.setTimestamp(1, new Timestamp(Calendar.getInstance().getTime().getTime()));
			pStmt.execute();
			
			// Update question and answer program_id default values
			sql = "select * from program order by program_id desc limit 1";
			Statement s = conn.createStatement();			
			ResultSet result = s.executeQuery(sql);
			result.last();
			programID = result.getInt(1);
			
			sql = "ALTER TABLE question CHANGE COLUMN program_id program_id SMALLINT(6) NOT NULL DEFAULT " + programID + "  ;";
			Statement s3 = conn.createStatement();
			s3.executeUpdate(sql);
			/*
			sql = "ALTER TABLE answer CHANGE COLUMN program_id program_id SMALLINT(6) NOT NULL DEFAULT " + programID + "  ;";
			Statement s4 = conn.createStatement();
			s4.executeUpdate(sql);
			*/
			return true;
		}catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean endGame(int numOfContests, int numOfQuestions) {
		try {
			String sql = "update program set end_time = ?, num_of_contests = ?, num_of_questions = ? order by program_id desc limit 1;";
			PreparedStatement pStmt = (PreparedStatement) conn.prepareStatement(sql);
			pStmt.setTimestamp(1, new Timestamp(Calendar.getInstance().getTime().getTime()));
			pStmt.setInt(2, numOfContests);
			pStmt.setInt(3, numOfQuestions);
			pStmt.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see edu.buffalo.cse.ubicomp.crowdonline.db.DB#getAll()
	 */
	@Override
	public ArrayList<Object> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.ubicomp.crowdonline.db.DB#getLast()
	 */
	@Override
	public Object getLast() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.ubicomp.crowdonline.db.DB#getLastID()
	 */
	@Override
	public int getLastID() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see edu.buffalo.cse.ubicomp.crowdonline.db.DB#getID(java.lang.String)
	 */
	@Override
	public int getID(String data) {
		// TODO Auto-generated method stub
		return 0;
	}

}
