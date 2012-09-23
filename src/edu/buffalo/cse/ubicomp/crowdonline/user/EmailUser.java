package edu.buffalo.cse.ubicomp.crowdonline.user;

import edu.buffalo.cse.ubicomp.crowdonline.db.DBHandler;

public class EmailUser extends User {

	public EmailUser(String contact) {
		super(contact);
	//	id = DBHandler.getEmailDB().getID(contact);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean sendMessage(String message) {
		// TODO Auto-generated method stub
		return false;
	}
}
