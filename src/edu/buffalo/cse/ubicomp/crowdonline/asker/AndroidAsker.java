package edu.buffalo.cse.ubicomp.crowdonline.asker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import edu.buffalo.cse.ubicomp.GameServer.server.ApiKeyInitializer;
import edu.buffalo.cse.ubicomp.GameServer.server.Datastore;
import edu.buffalo.cse.ubicomp.crowdonline.db.DBHandler;

public class AndroidAsker extends Asker {

	private static final int MULTICAST_SIZE = 1000;
	private Sender sender;

	private static final Executor threadPool = Executors.newFixedThreadPool(5);
	protected final Logger logger = Logger.getLogger(getClass().getName());

	public AndroidAsker() {
		sender = new Sender(ApiKeyInitializer.getAttributeAccessKey());
	}

	@Override
	public void ask(Question q) {
		List<String> devices = Datastore.getDevices();
		String status;
		if (devices.isEmpty()) {
			status = "Message ignored as there is no device registered!";
		} else {
			// NOTE: check below is for demonstration purposes; a real
			// application
			// could always send a multicast, even for just one recipient
			if (devices.size() == 1) {
				// send a single message using plain post
				String registrationId = devices.get(0);
				Message message = new Message.Builder()
					.addData("Q", q.getQuestion())
					.addData("A", q.getChoiceA())
					.addData("B", q.getChoiceB())
					.addData("C", q.getChoiceC())
					.addData("D", q.getChoiceD())
					.addData("no", ""+q.getNo())
					.addData("questionId", ""+DBHandler.getQuestionDB().getLastID())
					.build();
				Result result;
				try {
					result = sender.send(message, registrationId, 5);
					status = "Sent message to one device: " + result;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				// send a multicast message using JSON
				// must split in chunks of 1000 devices (GCM limit)
				int total = devices.size();
				List<String> partialDevices = new ArrayList<String>(total);
				int counter = 0;
				int tasks = 0;
				for (String device : devices) {
					counter++;
					partialDevices.add(device);
					int partialSize = partialDevices.size();
					if (partialSize == MULTICAST_SIZE || counter == total) {
						asyncSend(partialDevices,q);
						partialDevices.clear();
						tasks++;
					}
				}
				status = "Asynchronously sending " + tasks
						+ " multicast messages to " + total + " devices";
			}
		}

	}

	private void asyncSend(List<String> partialDevices, final Question q) {
		// make a copy
		final List<String> devices = new ArrayList<String>(partialDevices);
		threadPool.execute(new Runnable() {

			public void run() {
				Message message = new Message.Builder()
				.addData("Q", q.getQuestion())
				.addData("A", q.getChoiceA())
				.addData("B", q.getChoiceB())
				.addData("C", q.getChoiceC())
				.addData("D", q.getChoiceD())
				.addData("no", ""+q.getNo())
				.addData("questionId", ""+DBHandler.getQuestionDB().getLastID())
				.build();
				MulticastResult multicastResult;
				try {
					multicastResult = sender.send(message, devices, 5);
				} catch (IOException e) {
					logger.log(Level.SEVERE, "Error posting messages", e);
					return;
				}
				List<Result> results = multicastResult.getResults();
				// analyze the results
				for (int i = 0; i < devices.size(); i++) {
					String regId = devices.get(i);
					Result result = results.get(i);
					String messageId = result.getMessageId();
					if (messageId != null) {
						logger.fine("Succesfully sent message to device: "
								+ regId + "; messageId = " + messageId);
						String canonicalRegId = result
								.getCanonicalRegistrationId();
						if (canonicalRegId != null) {
							// same device has more than on registration id:
							// update it
							logger.info("canonicalRegId " + canonicalRegId);
							Datastore.updateRegistration(regId, canonicalRegId);
						}
					} else {
						String error = result.getErrorCodeName();
						if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
							// application has been removed from device -
							// unregister it
							logger.info("Unregistered device: " + regId);
							Datastore.unregister(regId);
						} else {
							logger.severe("Error sending message to " + regId
									+ ": " + error);
						}
					}
				}
			}
		});
	}

}
