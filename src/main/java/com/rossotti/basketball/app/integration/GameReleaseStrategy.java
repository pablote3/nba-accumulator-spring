package com.rossotti.basketball.app.integration;

import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;

public class GameReleaseStrategy implements ReleaseStrategy {

	public boolean canRelease(MessageGroup messageGroup) {
		for (Message<?> msg : messageGroup.getMessages()) {
			if ((Boolean)msg.getHeaders().get(GameCorrelationStrategy.LAST_KEY)) {
				return true;
			}
		}
		return false;
	}
}