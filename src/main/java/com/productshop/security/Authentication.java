package com.productshop.security;

import com.productshop.models.User;

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
