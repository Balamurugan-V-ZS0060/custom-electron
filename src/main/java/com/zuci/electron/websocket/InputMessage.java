package com.zuci.electron.websocket;

public class InputMessage {

	private String to;
	private String text;

	public String getText() {
		return text;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setText(String text) {
		this.text = text;
	}
}