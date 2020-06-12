package com.zuci.electron.request;

public class ForgetPwdRequest extends Request{
	
	String emailId;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
}
