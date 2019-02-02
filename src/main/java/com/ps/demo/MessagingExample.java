package com.ps.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
public class MessagingExample {
	@Autowired
	private DirectChannel inputChannel; // 3
	@Autowired
	private DirectChannel aloneInputChannel;
	@Autowired
	@Qualifier("outputChannel")
	private DirectChannel outputChannel;

	public void doOne() {
		Map<String, Object> map = new HashMap<>();// 1
		map.put("key", "value");// 1

		Message<String> message = new GenericMessage<String>("Hello World!", new MessageHeaders(map));// 1
		PrintService printService = new PrintService();// 1
		printService.print(message);// 1

	}

	public void doTwo() {
		inputChannel.subscribe(new MessageHandler() {

			@Override
			public void handleMessage(Message<?> message) throws MessagingException {
				new PrintService().print((Message<String>) message);

			}
		});
		Map<String, Object> map = new HashMap<>();// 1
		map.put("key", "value");// 1

		Message<String> message = new GenericMessage<String>("Hello World!", new MessageHeaders(map));// 1
		inputChannel.send(message);

	}

	public void doThree() {
		outputChannel.subscribe(new MessageHandler() {
			@Override
			public void handleMessage(Message<?> message) throws MessagingException {
				System.out.println(message.getPayload());

			}
		});
		Message<String> message = MessageBuilder.withPayload("Hello World with message builder")
				.setHeader("NewKey", "newValue").build(); // 2
		inputChannel.send(message); // 3

	}

//MessaaingTemplate
	public void doFour() {
		Message<String> message = MessageBuilder.withPayload("Hello World with message builder")
				.setHeader("NewKey", "newValue").build();
		MessagingTemplate messagingTemplate = new MessagingTemplate();
		Message<?> returnMsg = messagingTemplate.sendAndReceive(aloneInputChannel, message);
		System.out.println(returnMsg.getPayload());

	}

	@Autowired
	private PrinterGateway gateway;

	public void queueChannelExample() {
		List<Future<Message<String>>> futures = new ArrayList<Future<Message<String>>>();
		for (int i = 0; i < 10; i++) {
			Message<String> message = MessageBuilder.withPayload("Hello World with message builder : " + i)
					.setHeader("NewKey for : " + i, "newValue : " + i).setHeader("messageNumber", i).build();
			System.out.println("Sending message... : " + i);
			futures.add(this.gateway.print(message));
		}
		for (Future<Message<String>> future : futures) {
			try {
				System.out.println(future.get().getPayload());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	@Autowired
	private PrinterGateway priorityGateway;

	public void priorityChannelExample() {
		List<Future<Message<String>>> futures = new ArrayList<Future<Message<String>>>();
		for (int i = 0; i < 10; i++) {
			Message<String> message = MessageBuilder.withPayload("Hello World with message builder : " + i)
					.setHeader("messageNumber", i).setPriority(i).build();
			System.out.println("Sending message... : " + i);
			futures.add(this.priorityGateway.print(message));
		}
		for (Future<Message<String>> future : futures) {
			try {
				System.out.println(future.get().getPayload());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

	@Autowired
	private PrinterGateway comparatorPriorityGateway;

	public void priorityChannelWithComparatorExample() {
		List<Future<Message<String>>> futures = new ArrayList<Future<Message<String>>>();
		for (int i = 0; i < 10; i++) {
			Message<String> message = MessageBuilder.withPayload("Hello World with message builder : " + i)
					.setHeader("messageNumber", i).build();
			System.out.println("Sending message... : " + i);
			futures.add(this.comparatorPriorityGateway.print(message));
		}
		for (Future<Message<String>> future : futures) {
			try {
				System.out.println(future.get().getPayload());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

	}

	@Autowired
	private PrinterGateway directChannelGateway;

	public void directChannel() {
		for (int i = 0; i < 10; i++) {
			Message<String> message = MessageBuilder.withPayload("Hello World with message builder : " + i).build();
			directChannelGateway.print(message);
		}

	}
}
