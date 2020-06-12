package com.zuci.electron.websocket;

public class OutputMessage {

    private String from;
    private String text;
    private String time;

    public OutputMessage() {
		// TODO Auto-generated constructor stub
	}
    
    public OutputMessage(final String from, final String text, final String time) {

        this.from = from;
        this.text = text;
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public String getFrom() {
        return from;
    }

	public void setFrom(String from) {
		this.from = from;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTime(String time) {
		this.time = time;
	}
}