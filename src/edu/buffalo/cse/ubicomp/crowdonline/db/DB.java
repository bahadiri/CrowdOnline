package edu.buffalo.cse.ubicomp.crowdonline.db;

import java.sql.DriverManager;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

public abstract class DB {
	protected Connection conn ;

	public DB() {
		this.conn = DBHandler.getConn();
	}
	
	abstract public boolean add(Object o);
	abstract public ArrayList<Object> getAll();
	abstract public Object getLast();
	abstract public int getID(String data);
}
