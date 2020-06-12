package com.zuci.electron.rabbitmq;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Profile({"tut5","topics"})
@Configuration
public class Config1 {

	@Bean
	public TopicExchange topic() {
		return new TopicExchange("tut.topics");
	}
	
	@Profile("receiver")
	private static class ReceiverConfig{
		
		@Bean
		public Receiver1 receiver() {
	 	 	return new Receiver1();
		}
		
		@Bean
		public Queue queue1() {
			return new Queue("hi-q1");
		}
		
		@Bean
		public Queue queue2() {
			return new Queue("hello-q2");
		}
		
		@Bean
		public Binding binding1(TopicExchange topic, Queue queue1) {
			return BindingBuilder.bind(queue1).to(topic).with("*.orange.*");
		}
		
		@Bean
		public Binding binding2(TopicExchange topic, Queue queue2) {
			return BindingBuilder.bind(queue2).to(topic).with("black.#");
		}
	}
	
	@Profile("sender")
	@Bean
	public Sender1 sender() {
		return new Sender1();
	}
}
