package edu.buffalo.cse.ubicomp.crowdonline.collector;

import java.util.List;

import edu.buffalo.cse.ubicomp.crowdonline.db.*;
import twitter4j.*;

public class TwitterCollector extends Collector {

	Twitter twitter;
	DB adb;
	public TwitterCollector() {
		super();
		twitter = TwitterUserDB.getTwitter();
		adb = DBHandler.getAnswerDB();
	}

	@Override
	public void collect (){
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.setOAuthConsumer(DBHandler.getTwitterUserDB().getConsumerKey(), DBHandler.getTwitterUserDB().getConsumerSecret());
        twitterStream.setOAuthAccessToken(DBHandler.getTwitterUserDB().getAccessToken());
    	long preMyID = 0;
        try {
			preMyID= twitterStream.getId();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final long myID = preMyID;
        UserStreamListener listener = new UserStreamListener() {
        	boolean verbose = false;
            @Override
            public void onStatus(Status status) {
            	if(status.getUser().getId() != myID){
            		DBHandler.getTweetDB().add(status);
            		DBHandler.getAnswerDB().add(new Answer(status.getUser().getScreenName(), DBHandler.getTweetDB().getRelatedQuestion(status), Answer.parseChoice(status.getText())));
            		
            		System.out.println("Status: "+ status.getId() + ", reply to: " + status.getInReplyToStatusId() + " for question no: " + DBHandler.getTweetDB().getRelatedQuestion(status));
            	}
            		
                if(verbose) System.out.println("onStatus @" + status.getUser().getScreenName() + " - " + status.getText());
            }
            
            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            	if(verbose) System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onDeletionNotice(long directMessageId, long userId) {
            	if(verbose) System.out.println("Got a direct message deletion notice id:" + directMessageId);
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            	if(verbose) System.out.println("Got a track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
            	if(verbose) System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onFriendList(long[] friendIds) {
            	if(verbose) { 
            		System.out.print("onFriendList");
	                for (long friendId : friendIds) {
	                    System.out.print(" " + friendId);
	                }
	                System.out.println();
            	}
            }

            public void onFavorite(User source, User target, Status favoritedStatus) {
            	if(verbose) System.out.println("onFavorite source:@"
                        + source.getScreenName() + " target:@"
                        + target.getScreenName() + " @"
                        + favoritedStatus.getUser().getScreenName() + " - "
                        + favoritedStatus.getText());
            }

            public void onUnfavorite(User source, User target, Status unfavoritedStatus) {
            	if(verbose) System.out.println("onUnFavorite source:@"
                        + source.getScreenName() + " target:@"
                        + target.getScreenName() + " @"
                        + unfavoritedStatus.getUser().getScreenName()
                        + " - " + unfavoritedStatus.getText());
            }

            public void onFollow(User source, User followedUser) {
            	if(verbose) System.out.println("onFollow source:@"
                        + source.getScreenName() + " target:@"
                        + followedUser.getScreenName());
            }

            public void onRetweet(User source, User target, Status retweetedStatus) {
            	if(verbose) System.out.println("onRetweet @"
                        + retweetedStatus.getUser().getScreenName() + " - "
                        + retweetedStatus.getText());
            }

            public void onDirectMessage(DirectMessage directMessage) {
            	if(verbose) System.out.println("onDirectMessage text:"
                        + directMessage.getText());
            }

            public void onUserListMemberAddition(User addedMember, User listOwner, UserList list) {
            	if(verbose) System.out.println("onUserListMemberAddition added member:@"
                        + addedMember.getScreenName()
                        + " listOwner:@" + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            public void onUserListMemberDeletion(User deletedMember, User listOwner, UserList list) {
            	if(verbose) System.out.println("onUserListMemberDeleted deleted member:@"
                        + deletedMember.getScreenName()
                        + " listOwner:@" + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            public void onUserListSubscription(User subscriber, User listOwner, UserList list) {
            	if(verbose) System.out.println("onUserListSubscribed subscriber:@"
                        + subscriber.getScreenName()
                        + " listOwner:@" + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            public void onUserListUnsubscription(User subscriber, User listOwner, UserList list) {
            	if(verbose) System.out.println("onUserListUnsubscribed subscriber:@"
                        + subscriber.getScreenName()
                        + " listOwner:@" + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            public void onUserListCreation(User listOwner, UserList list) {
            	if(verbose) System.out.println("onUserListCreated  listOwner:@"
                        + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            public void onUserListUpdate(User listOwner, UserList list) {
            	if(verbose) System.out.println("onUserListUpdated  listOwner:@"
                        + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            public void onUserListDeletion(User listOwner, UserList list) {
            	if(verbose) System.out.println("onUserListDestroyed  listOwner:@"
                        + listOwner.getScreenName()
                        + " list:" + list.getName());
            }

            public void onUserProfileUpdate(User updatedUser) {
            	if(verbose) System.out.println("onUserProfileUpdated user:@" + updatedUser.getScreenName());
            }

            public void onBlock(User source, User blockedUser) {
            	if(verbose) System.out.println("onBlock source:@" + source.getScreenName()
                        + " target:@" + blockedUser.getScreenName());
            }

            public void onUnblock(User source, User unblockedUser) {
            	if(verbose) System.out.println("onUnblock source:@" + source.getScreenName()
                        + " target:@" + unblockedUser.getScreenName());
            }

            public void onException(Exception ex) {
            	if(verbose) {
            		ex.printStackTrace();
            		System.out.println("onException:" + ex.getMessage());
            	}
            }
            
        };
        twitterStream.addListener(listener);
        // user() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
        twitterStream.user();
    }
 
	@Override
	public char findChoice(String messageText) {
		// Should be more sophisticated
		return messageText.charAt(0);
	}

	@Override
	public int findQuestionId(String messageText) {
		// Should be more sophisticated
		return getLastQuestionId();
	}
	/*
	// Gerek kalmadi
		@Override
	public String collect(int index) throws TwitterException{
//		List<DirectMessage> messages = twitter.getDirectMessages();
		List<Status> mentions = twitter.getMentions();
		if(mentions.isEmpty())
			return "Alamadım mentionları";
		//DirectMessage message = messages.get(index);
		Status mention = mentions.get(0);
//		return "From: @" + message.getSenderScreenName() + " " + message.getText();
		DBHandler.getTweetDB().add(mention);
		return "From: @" + mention.getUser().getScreenName()+ " " + mention.getText() + " sent by " + mention.getSource() + " as a reply to " + twitter.showStatus(mention.getInReplyToStatusId()).getText() + " " + mention.getInReplyToStatusId();
	}
	@Override
	public void run() {
		renewQuestionId();
		//		int counter = 0;
//		while(!stop){
		while(true){
			//			if(counter%1000==0){
			//				counter = 0;
			try {
				//            Paging paging = new Paging(1); === Cok fazla mesaji handle etmek icin paging gerekiyor olabilir
				List<DirectMessage> messages = twitter.getDirectMessages();
				
				for (DirectMessage message : messages) {
					adb.add(new Answer(message.getSenderScreenName(),findQuestionId(message.getText()),findChoice(message.getText())));
					twitter.destroyDirectMessage(message.getId());
				}
				//	return true;
			} catch (TwitterException te) {
				te.printStackTrace();
			//	return false;
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//			} else {
			//				counter++;
			//			}
		}
//		System.out.println("Ha Ha");
//		stop = false;
	//	return true;
	}
	*/
/*
	@Override
	public void run() {
		collect();
	}
*/
}
