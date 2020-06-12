package com.zuci.electron.request;

public class RecreatePwdRequest extends Request {
	private String emailid;
	private String newpassword;
	private String confirmpassword;
	
	public RecreatePwdRequest() {
		// TODO Auto-generated constructor stub
	}
	
	public RecreatePwdRequest(String emailid , String newpassword, String confirmpassword) {
		this.emailid = emailid;
		this.newpassword = newpassword;
		this.confirmpassword = confirmpassword;
	}
	
	
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getConfirmpassword() {
		return confirmpassword;
	}
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
}
