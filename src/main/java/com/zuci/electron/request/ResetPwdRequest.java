package com.zuci.electron.request;

public class ResetPwdRequest extends Request{
	private String newpassword;
	private String confirmpassword;
	public ResetPwdRequest() {
		// TODO Auto-generated constructor stub
	}
	public ResetPwdRequest(String newpassword, String confirmpassword) {
		this.newpassword = newpassword;
		this.confirmpassword = confirmpassword;
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
