package site.productshop.security;

import site.productshop.entities.User;

public class Authentication {

	private int userID;
	
	public Authentication(User user) {
		this.userID = user.getId();
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
}
