package edu.buffalo.cse.ubicomp.GameServer.client;

import edu.buffalo.cse.ubicomp.GameServer.shared.FieldVerifier;
import edu.buffalo.cse.ubicomp.GameServer.shared.QuestionWithoutTime;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GameServer implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	int numOfContests = 1;
	int numOfQuestions = 0;
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		RootPanel.get().setStylePrimaryName("body");
		final FormPanel form = new FormPanel();
		form.setAction(GWT.getModuleBaseURL() + "DemoServlet"); 
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);
		final AbsolutePanel absolutePanel = new AbsolutePanel();
		absolutePanel.setSize("760px", "570px");
		// RootPanel.get().add(absolutePanel, 0, 0);

		final Button askButton = new Button("ASK");
		final Button endProgramButton = new Button("End Program");
		final Button newContestantButton = new Button("New contestant");
		final TextBox questionField = new TextBox();
		final TextBox continueField = new TextBox();
		final Label errorLabel = new Label();
		final NumberLabel<Integer> questionNoLabel = new NumberLabel<Integer>();
		final Label aLabel = new Label("A");
		final Label bLabel = new Label("B");
		final Label cLabel = new Label("C");
		final Label dLabel = new Label("D");
		final TextBox choiceAField = new TextBox();
		final TextBox choiceBField = new TextBox();
		final TextBox choiceCField = new TextBox();
		final TextBox choiceDField = new TextBox();

		questionField.setText("Write the question...");
		questionField.setSize("90%", "16px");
		absolutePanel.add(questionField, 41, 30);

		questionNoLabel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		questionNoLabel.setValue(1);
		questionNoLabel.setSize("15px", "24px");
		absolutePanel.add(questionNoLabel, 10, 30);

		absolutePanel.add(aLabel, 10, 64);
		aLabel.setSize("15px", "24px");
		// aLabel.setSize("7px", "18px");

		absolutePanel.add(bLabel, 10, 94);
		bLabel.setSize("15px", "18px");
		// bLabel.setSize("7px", "18px");

		absolutePanel.add(cLabel, 10, 124);
		cLabel.setSize("15px", "24px");
		// cLabel.setSize("7px", "18px");

		absolutePanel.add(dLabel, 10, 154);
		dLabel.setSize("15px", "24px");
		// dLabel.setSize("7px", "18px");

		absolutePanel.add(choiceAField, 41, 64);
		choiceAField.setText("Write choice A here");
		choiceAField.setFocus(true);
		choiceAField.setSize("40%", "16px");

		absolutePanel.add(choiceBField, 41, 94);
		choiceBField.setText("");
		choiceBField.setFocus(true);
		choiceBField.setSize("40%", "16px");

		absolutePanel.add(choiceCField, 41, 124);
		choiceCField.setText("");
		choiceCField.setFocus(true);
		choiceCField.setSize("40%", "16px");

		absolutePanel.add(choiceDField, 41, 154);
		choiceDField.setText("");
		choiceDField.setFocus(true);
		choiceDField.setSize("40%", "16px");

		// We can add style names to widgets
		absolutePanel.add(askButton, 500, 74);
		askButton.setSize("200px", "80px");

		newContestantButton.setText("New contestant");
		absolutePanel.add(newContestantButton, 500, 164);
		newContestantButton.setSize("200px", "40px");
		
		endProgramButton.setText("The End");
		absolutePanel.add(endProgramButton, 500, 210);
		continueField.setWidth("40px");
		absolutePanel.add(continueField, 600, 210);

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		form.add(absolutePanel);
		RootPanel.get("questionFieldContainer").add(form, 259, 241);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		questionField.setFocus(true);
		questionField.selectAll();

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Asking question to the users:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				askButton.setEnabled(true);
				askButton.setFocus(true);
			}
		});

		// Create a handler for the newContestantButton
		newContestantButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				numOfQuestions += questionNoLabel.getValue();
				questionNoLabel.setValue(1);
				numOfContests++;
			}
		});
		
		// Create a handler for the endGameButton
		endProgramButton.addClickHandler(new ClickHandler() {  
			  public void onClick(ClickEvent event) {  
				greetingService.endProgram(numOfContests, numOfQuestions, new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
					}

					public void onSuccess(String result) {

					}
				});
				numOfContests = 1;
				numOfQuestions = 0;
				questionNoLabel.setValue(1);
			  }  
			});
		continueField.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				questionNoLabel.setValue(Integer.parseInt(continueField.getValue()));
			}
		});
		// Create a handler for the sendButton and nameField
		class QuestionHandler implements ClickHandler, KeyUpHandler {

			// Fired when the user clicks on the sendButton.

			public void onClick(ClickEvent event) {
				sendQuestionToServer();
			}

			// Fired when the user types in the choiceDField.
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
					sendQuestionToServer();
			}

			// Send the question from related fields to the server and wait for
			// a response.
			private void sendQuestionToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				QuestionWithoutTime questionToServer = new QuestionWithoutTime(
						questionNoLabel.getValue().intValue(),
						questionField.getText(), choiceAField.getText(),
						choiceBField.getText(), choiceCField.getText(),
						choiceDField.getText());

				// Then, we send the question to the server.
				askButton.setEnabled(false);
				textToServerLabel.setText(questionToServer.getQuestion());
				serverResponseLabel.setText("");
				greetingService.askFromServer(questionToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox.setText("Question Server Connection Status - Failure");
								serverResponseLabel.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(caught.getMessage());
								dialogBox.center();
								askButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Question Server Connection Status");
								serverResponseLabel.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								askButton.setFocus(true);
								questionNoLabel.setValue(questionNoLabel.getValue()+1);
							}
						});
			//	form.submit();
			}
		}

		// Add a handler to send the question to the server
		QuestionHandler qHandler = new QuestionHandler();
		askButton.addClickHandler(qHandler);
		choiceDField.addKeyUpHandler(qHandler);
		
		form.addSubmitHandler(new SubmitHandler() {
		    public void onSubmit(SubmitEvent event)
		    {
		        // if (something_is_wrong)
		        // {
		        // Take some action
		        // event.setCancelled(true);
		        // }
		    }
		});
		
		form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			public void onSubmitComplete(SubmitCompleteEvent event) {
				// When the form submission is successfully completed, this
				// event is
				// fired. Assuming the service returned a response of type
				// text/html,
				// we can get the result text here (see the FormPanel
				// documentation for
				// further explanation).
				Window.alert(event.getResults());
			}
		});
	}
}
