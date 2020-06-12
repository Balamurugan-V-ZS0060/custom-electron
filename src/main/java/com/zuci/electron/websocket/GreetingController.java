package com.zuci.electron.websocket;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {

	@Autowired
	private WebSocketMessageBrokerStats webSocketMessageBrokerStats;

	@PostConstruct
	public void init() {
	    webSocketMessageBrokerStats.setLoggingPeriod(30000); // desired time in millis
	}
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greeting greeting(HelloMessage message) throws Exception {
		Thread.sleep(1000); // simulated delay
		return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}
	
	@MessageMapping("/hello1")
	@SendTo("/topic/greetings")
	public Greeting greeting1(String message) throws Exception {
		System.out.println("GreetingController.greeting1() : " + message);
		Thread.sleep(1000); // simulated delay
		return new Greeting("Hello, " +message);
	}

	@RequestMapping("/notifications")
	public String index() {
		return "notifications.html";
	}

	@MessageMapping("/message")
	public void sendSpecific(@Payload InputMessage msg, Principal user)
			throws Exception {
		OutputMessage out = new OutputMessage(user.getName(), msg.getText(),
				new SimpleDateFormat("HH:mm").format(new Date()));
		simpMessagingTemplate.convertAndSendToUser(msg.getTo(), "/queue/message", out);
	}

	@MessageMapping("/hi")
	@SendToUser("/queue/greetings")
	public void reply(@Payload String message, Principal user) {
		this.simpMessagingTemplate.convertAndSendToUser(user.getName(), "/queue/greetings",
				new Greeting("Hello " + user.getName()));
	}
	
	@MessageMapping("/sendgreet")
	public void reply1(@Payload String message, Principal user) {
		this.simpMessagingTemplate.convertAndSend("/queue/greetings",
				new Greeting("Hello " + user.getName()));
		
		
	}
	
	
	@MessageMapping("/hello2")
	@SendTo("/topic/greetings")
	public String greeting2(String message) throws Exception {
		Thread.sleep(1000); // simulated delay
		return message;
	}
	

}
