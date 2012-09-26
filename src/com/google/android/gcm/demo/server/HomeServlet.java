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
package com.google.android.gcm.demo.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet that adds display number of devices and button to send a message.
 * <p>
 * This servlet is used just by the browser (i.e., not device) and contains the
 * main page of the demo app.
 */
@SuppressWarnings("serial")
public class HomeServlet extends BaseServlet {

  static final String ATTRIBUTE_STATUS = "status";

  /**
   * Displays the existing messages and offer the option to send a new one.
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    resp.setContentType("text/html");
    PrintWriter out = resp.getWriter();

    out.print("<html><body>");
    out.print("<head>");
    out.print("  <title>CrowdOnline Game Server</title>");
    out.print("  <link rel='icon' href='favicon.png'/>");
    out.print("</head>");
    String status = (String) req.getAttribute(ATTRIBUTE_STATUS);
    if (status != null) {
      out.print(status);
    }
    List<String> devices = Datastore.getDevices();
    if (devices.isEmpty()) {
      out.print("<h2>No devices registered!</h2>");
    } else {
      out.print("<h2>" + devices.size() + " device(s) registered!</h2>");
      out.print("<form name='form' method='POST' action='sendAll'>");
      out.print("<input type='submit' value='Send Message' />");
      out.print("</form>");
    }
    out.print("<form name='form' method='POST' action='demo'>");
    out.print("Enter question: <textarea rows=\"2\" cols=\"1\"   name=\"question\" style=\"width: 585px; height: 38px;\">  </textarea>");
    out.print("A) <textarea rows=\"1\" cols=\"1\"   name=\"choiceA\" style=\"width: 385px; height: 20px;\">  </textarea>");
    out.print("B) <textarea rows=\"1\" cols=\"1\"   name=\"choiceB\" style=\"width: 385px; height: 20px;\">  </textarea>");
    out.print("C) <textarea rows=\"1\" cols=\"1\"   name=\"choiceC\" style=\"width: 385px; height: 20px;\">  </textarea>");
    out.print("D) <textarea rows=\"1\" cols=\"1\"   name=\"choiceD\" style=\"width: 385px; height: 20px;\">  </textarea>");
    out.print("<input type='submit' value='Ask' />");
    out.print("</form>");
    out.print("</body></html>");
    resp.setStatus(HttpServletResponse.SC_OK);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    doGet(req, resp);
  }

}