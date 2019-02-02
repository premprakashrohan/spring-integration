package com.ps.demo;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

public class QueueChanelPrintService {
	public Message<?> print(Message<String> message) {
		 System.out.println(message.getPayload());
		 int messageNumber = (int)message.getHeaders().get("messageNumber");
		return MessageBuilder.withPayload("Returning New Message : "+messageNumber).build();
	}
}