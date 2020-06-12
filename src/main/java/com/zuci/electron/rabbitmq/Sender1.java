package com.zuci.electron.rabbitmq;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class Sender1 {

	@Autowired
	RabbitTemplate template;
	
	@Autowired
	TopicExchange topic;
	
	@Scheduled(fixedDelay = 1000 , initialDelay = 500)
	public void send() {
		System.out.println("Sender1.send() - 1");
		template.convertAndSend(topic.getName(), "a.orange.b", "Orange Test Message");
		System.out.println("Sender1.send() - 2");
	}
	
	
	@Scheduled(fixedDelay = 1000, initialDelay = 600)
	public void send1() {
		template.convertAndSend(topic.getName(), "black", "Black Test Message");
	}
	 
}
