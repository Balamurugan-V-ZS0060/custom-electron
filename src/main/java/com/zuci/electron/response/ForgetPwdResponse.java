package com.zuci.electron.response;

public class ForgetPwdResponse extends Response{

	private String message;
	
	public ForgetPwdResponse(String statusCode, String statusDesc) {
		super(statusCode, statusDesc);
		// TODO Auto-generated constructor stub
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
