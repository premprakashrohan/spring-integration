package com.ps.demo;

import java.util.Comparator;

import org.springframework.messaging.Message;

public class CustomMessageComparator implements Comparator<Message<String>> {

	@Override
	public int compare(Message<String> message1, Message<String> message2) {
		String payload = message1.getPayload();
		String payload1 =  message1.getPayload();
		boolean isPayloadEven = Integer.valueOf(payload.substring(payload.length()-1)) % 2 ==0;
		boolean isPayload1Even = Integer.valueOf(payload1.substring(payload1.length()-1)) % 2 ==0;
			if((isPayloadEven && isPayload1Even) || (!isPayloadEven && !isPayload1Even)) {
				return 0;
			}else if(isPayloadEven) {
				return -1;
			}else {
				return 1;
			}
	}

}
