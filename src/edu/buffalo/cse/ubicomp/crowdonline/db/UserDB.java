package edu.buffalo.cse.ubicomp.crowdonline.db;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

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
			System.out.println(sql);
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
