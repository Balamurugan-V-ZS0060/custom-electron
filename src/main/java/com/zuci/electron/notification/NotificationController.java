package com.zuci.electron.notification;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuci.electron.response.DefaultSuccessResponse;
import com.zuci.electron.response.Response;
import com.zuci.electron.websocket.InputMessage;
import com.zuci.electron.websocket.OutputMessage;

@RestController
public class NotificationController {
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@PostMapping("/broadcast")
	public ResponseEntity<Response> braodcast(@RequestBody InputMessage msg, Principal user)
			throws Exception {
		System.out.println("NotificationController.braodcast()");
		OutputMessage out = new OutputMessage(user.getName(), msg.getText(),
				new SimpleDateFormat("HH:mm").format(new Date()));
		//ObjectMapper mapper = new Object
		//JsonNode node = mapper.convertValue(fromValue, JsonNode.class);
		simpMessagingTemplate.setMessageConverter(new MappingJackson2MessageConverter());
		simpMessagingTemplate.convertAndSend("/topic/broadcast", out);
		return ResponseEntity.ok(new DefaultSuccessResponse("0000", "Success"));
	}
	
	@MessageMapping("/broadcastbyuser")
	public void broadcastbyuser(@Payload InputMessage msg)
			throws Exception {
		System.out.println("NotificationController.broadcastbyuser() : received");
		OutputMessage out = new OutputMessage("", msg.getText(),
				new SimpleDateFormat("HH:mm").format(new Date()));
		simpMessagingTemplate.convertAndSend("/topic/broadcast", out);
	}
}
