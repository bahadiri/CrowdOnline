package edu.buffalo.cse.ubicomp.GameServer.client;

import edu.buffalo.cse.ubicomp.GameServer.shared.QuestionWithoutTime;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The client side stub for the RPC service.
 */
public interface GreetingServiceAsync {
	void greetServer(String name, AsyncCallback<String> callback);

	void askFromServer(QuestionWithoutTime q, AsyncCallback<String> callback);

	void endProgram(int numOfContests, int numOfQuestions, AsyncCallback<String> callback);
}
