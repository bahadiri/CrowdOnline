package edu.buffalo.cse.ubicomp.crowdonline.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import com.mysql.jdbc.PreparedStatement;

import edu.buffalo.cse.ubicomp.crowdonline.user.AndroidUser;;

public class AndroidUserDB extends UserDB {

	public AndroidUserDB() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean add(Object o) {
		try {
			String sql = "insert into user(contact_type,contact,date) values(?,?,?)";
			PreparedStatement pStmt = (PreparedStatement) conn.prepareStatement(sql);
			pStmt.setString(1, "A");
			pStmt.setString(2, ((AndroidUser)o).getContact());
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
			String sql = "select * from user where contact_type= 'A' AND active = 1";
			Statement s = conn.createStatement();
			ResultSet result = s.executeQuery(sql);
			while (result.next()) {
				list.add(new AndroidUser(result.getString(3)));
			}
			System.out.println("Successfully get all active Android users");
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

	@Override
	public int getLastID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
