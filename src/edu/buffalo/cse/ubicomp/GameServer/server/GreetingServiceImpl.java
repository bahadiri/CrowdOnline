package edu.buffalo.cse.ubicomp.GameServer.server;

import edu.buffalo.cse.ubicomp.GameServer.client.GreetingService;
import edu.buffalo.cse.ubicomp.GameServer.shared.FieldVerifier;
import edu.buffalo.cse.ubicomp.GameServer.shared.QuestionWithoutTime;
import edu.buffalo.cse.ubicomp.crowdonline.asker.AndroidAsker;
import edu.buffalo.cse.ubicomp.crowdonline.asker.Asker;
import edu.buffalo.cse.ubicomp.crowdonline.asker.Question;
import edu.buffalo.cse.ubicomp.crowdonline.asker.TwitterAsker;
import edu.buffalo.cse.ubicomp.crowdonline.db.DBHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	Asker twitterAsker = new TwitterAsker();
	Asker androidAsker = new AndroidAsker();
	private boolean firstQuestion = true;
	
	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	@Override
	public String askFromServer(QuestionWithoutTime qwt) {
		if(firstQuestion){
			DBHandler.getProgramDB().add("");
			firstQuestion = false;
		}
		Question q = new Question(qwt);
		DBHandler.getQuestionDB().add(q);
		twitterAsker.ask(q);
		androidAsker.ask(q);
		return qwt.getQuestion() + " is sent!<br><br>Collecting answers... " ;
	}

	@Override
	public String endProgram(int numOfContests, int numOfQuestions) {
		firstQuestion  = true;
		DBHandler.getProgramDB().endGame(numOfContests, numOfQuestions);
		return null;
	}
}
