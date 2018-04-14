package com.productshop.models;

import java.util.Date;

public class Message {
	
	private int id;
	private String senderName;
	private String senderEmail;
	private String text;
	private Date messageDate;
	
	public Message(String name, String email, String text) {
		this.setSenderName(name);
		this.setSenderEmail(email);
		this.setText(text);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public Date getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
