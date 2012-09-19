package edu.buffalo.cse.ubicomp.crowdonline.db;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import com.mysql.jdbc.PreparedStatement;

import edu.buffalo.cse.ubicomp.crowdonline.user.FacebookUser;

public class FacebookUserDB extends UserDB {

	public FacebookUserDB() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean add(Object o) {
		try {
			String sql = "insert into user(contact_type,contact,date) values(?,?,?)";
			PreparedStatement pStmt = (PreparedStatement) conn.prepareStatement(sql);
			pStmt.setString(1, "F");
			pStmt.setString(2, ((FacebookUser)o).getContact());
			pStmt.setTimestamp(3, new Timestamp(Calendar.getInstance().getTime().getTime()));
			pStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
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

}
