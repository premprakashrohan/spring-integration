package com.ps.demo;

import java.util.Map.Entry;

import javax.management.RuntimeErrorException;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

public class LowercasePrintService {
	public void print(Message<String> message) {
		//throw new RuntimeException("This is an error!");
		 System.out.println(message.getPayload().toLowerCase());
	}
}