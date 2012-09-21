package edu.buffalo.cse.ubicomp.crowdonline.db;

import java.sql.DriverManager;

import com.mysql.jdbc.Connection;

public class DBHandler {
	// GRANT ALL PRIVILEGES ON *.* TO 'bahadir'@'67.20.215.226' IDENTIFIED BY 'ben1o13SBy';
	private static String connStr = "jdbc:mysql://localhost:3306/crowdonline_db?characterEncoding=UTF-8";
	//private static String connStr = "jdbc:mysql://107.21.123.31:3306/crowdonline_db?characterEncoding=UTF-8";
	protected static Connection conn = connectToDB("bahadir","ben1o13SBy");
	protected static TwitterUserDB tudb = new TwitterUserDB();
	protected static FacebookUserDB fudb = new FacebookUserDB();
	protected static QuestionDB qdb = new QuestionDB();
	protected static AnswerDB adb = new AnswerDB();
	protected static TweetDB tdb = new TweetDB();
	
	public static Connection connectToDB(String userName,String password) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = (Connection) DriverManager.getConnection(connStr,userName, password);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return conn;
	}

	public static Connection getConn() {
		return conn;
	}

	public static TwitterUserDB getTwitterUserDB() {
		return tudb;
	}

	public static FacebookUserDB getFacebookUserDB() {
		return fudb;
	}

	public static QuestionDB getQuestionDB() {
		return qdb;
	}
	
	public static AnswerDB getAnswerDB() {
		return adb;
	}
	
	public static TweetDB getTweetDB() {
		return tdb;
	}
}
