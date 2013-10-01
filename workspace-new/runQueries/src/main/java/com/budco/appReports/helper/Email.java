package com.budco.appReports.helper;

public class Email {
	private String address;
	private String subject;
	private String body;
	private String from;
	private String fileName;

	public String getAddress() {
		return address;
	}
	public void setAddress(String emailAddress) {
		this.address = emailAddress;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String emailSubject) {
		this.subject = emailSubject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String emailBody) {
		this.body = emailBody;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String emailFrom) {
		this.from = emailFrom;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
