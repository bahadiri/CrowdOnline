package edu.buffalo.cse.ubicomp.crowdonline.user;
public abstract class User {
	String contact;
	
	public User(String contact){
		this.contact = contact;
	}
	
	abstract public boolean sendMessage(String message);

	public String getContact() {
		return contact;
	}

//	public void setContact(String contact) {
//		this.contact = contact;
//	}
}
