package edu.buffalo.cse.ubicomp.crowdonline.collector;
import twitter4j.TwitterException;
import edu.buffalo.cse.ubicomp.crowdonline.asker.Question;
import edu.buffalo.cse.ubicomp.crowdonline.db.DBHandler;

// NOW doing it single threaded way
// later after decidng GUIS, I can turn it into forking style

//public abstract class Collector implements Runnable{
public abstract class Collector{
	protected static int lastQuestionId = DBHandler.getQuestionDB().getLastID();
	protected static boolean stop = false;
	/*
	protected static CollectorThread thread;
	public Collector() {
		thread = new CollectorThread();
	}
	
	public static CollectorThread getThread() {
		return thread;
	}
	*/
	public static int getLastQuestionId() {
		return lastQuestionId;
	}
	public static void setLastQuestionId(int newLastQuestionId) {
		lastQuestionId = newLastQuestionId;
	}
	public static int renewQuestionId(){
		return (lastQuestionId = DBHandler.getQuestionDB().getLastID());
	}

	abstract public void collect();
	abstract public char findChoice(String data);
	abstract public int findQuestionId(String data);
	public void stopCollecting() {
	//	this.notify();
		stop = true;
	}
}
/*
class CollectorThread implements Runnable {
	CollectorThread() {
	}

	public void run() {
		
	}
}
*/

