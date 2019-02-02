package com.ps.demo;

import org.springframework.messaging.Message;

public class UppercasePrintService {
	public void print(Message<String> message) {
		//throw new RuntimeException("This is an error!");
		 System.out.println(message.getPayload().toUpperCase());
	}
}