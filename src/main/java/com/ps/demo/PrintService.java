package com.ps.demo;

import java.util.Map.Entry;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

public class PrintService {
	public Message<?> print(Message<String> message) {
//		for (Entry<String, Object> entry : message.getHeaders().entrySet()) {
//			System.out.println(entry.getKey() + "  ::  " + entry.getValue());
//		}
		System.out.println(message.getPayload());
		return MessageBuilder.withPayload("Returning New Message").build();
	}
}