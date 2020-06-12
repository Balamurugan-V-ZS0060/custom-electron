package com.zuci.electron.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Receiver1 {

	@RabbitListener(queues = "#{queue1.name}")
	public void receive1(String in) {
		System.out.println("Receiver1.receive1() : "+in);
	}
	
	@RabbitListener(queues = "#{queue2.name}")
	public void receive2(String in) {
		System.out.println("Receiver1.receive2() : "+in);
	}
}
