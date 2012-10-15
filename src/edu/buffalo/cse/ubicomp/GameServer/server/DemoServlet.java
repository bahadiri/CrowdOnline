/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.buffalo.cse.ubicomp.GameServer.server;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import edu.buffalo.cse.ubicomp.crowdonline.asker.Asker;
import edu.buffalo.cse.ubicomp.crowdonline.asker.Question;
import edu.buffalo.cse.ubicomp.crowdonline.asker.TwitterAsker;
import edu.buffalo.cse.ubicomp.crowdonline.db.DBHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that adds a new message to all registered devices.
 * <p>
 * This servlet is used just by the browser (i.e., not device).
 */
@SuppressWarnings("serial")
public class DemoServlet extends BaseServlet {

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
	  String status = "Collecting aswers";
    /*
	Asker twitterAsker = new TwitterAsker();  
    
    String question = req.getParameter("question"); 
    String choiceA = req.getParameter("choiceA"); 
    String choiceB = req.getParameter("choiceB"); 
    String choiceC = req.getParameter("choiceC"); 
    String choiceD = req.getParameter("choiceD"); 
    Question q = new Question(1, question, choiceA, choiceB, choiceC, choiceD);
	
	// It's crucial to first add to DB then to ask
	DBHandler.getQuestionDB().add(q);
	twitterAsker.ask(q);
	 
	 */
    Demo.runDemo();
    req.setAttribute(HomeServlet.ATTRIBUTE_STATUS, status.toString());
    getServletContext().getRequestDispatcher("/home").forward(req, resp);
  }

}
