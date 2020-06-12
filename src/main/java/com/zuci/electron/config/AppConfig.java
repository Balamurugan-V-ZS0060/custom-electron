package com.zuci.electron.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
	
	String smtpusername;
	String smtppassword;
	public String getSmtpusername() {
		return smtpusername;
	}
	public void setSmtpusername(String smtpusername) {
		this.smtpusername = smtpusername;
	}
	public String getSmtppassword() {
		return smtppassword;
	}
	public void setSmtppassword(String smtppassword) {
		this.smtppassword = smtppassword;
	}
}
