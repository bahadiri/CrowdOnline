package edu.buffalo.cse.ubicomp.crowdonline.collector;
import twitter4j.TwitterException;
import edu.buffalo.cse.ubicomp.crowdonline.asker.Question;
import edu.buffalo.cse.ubicomp.crowdonline.db.DBHandler;

public abstract class Collector implements Runnable{
	protected static int lastQuestionId = DBHandler.getQuestionDB().getLastID();
	protected static boolean stop = false;
//	protected static CollectorThread thread;
//	public Collector() {
//		thread = new CollectorThread();
//	}
//	
//	public static CollectorThread getThread() {
//		return thread;
//	}
	public static int getLastQuestionId() {
		return lastQuestionId;
	}
	public static void setLastQuestionId(int newLastQuestionId) {
		lastQuestionId = newLastQuestionId;
	}
	public static int renewQuestionId(){
		return (lastQuestionId = DBHandler.getQuestionDB().getLastID());
	}
//	public static int incrementQuestionId() {
//		return lastQuestionId++;
//	}
//	public static int resetQuestionId() {
//		return (lastQuestionId = 1);
//	}
//	abstract public boolean collect();
	abstract public char findChoice(String data);
	abstract public int findQuestionId(String data);
//	public void stopCollecting() {
//	//	this.notify();
//		stop = true;
//	}
	public String collect(int index) throws TwitterException {
		// TODO Auto-generated method stub
		return null;
	}
}

//class CollectorThread implements Runnable {
//    CollectorThread() {
//    }
//    public void run() {
//        // compute primes larger than minPrime
//    }
//}
