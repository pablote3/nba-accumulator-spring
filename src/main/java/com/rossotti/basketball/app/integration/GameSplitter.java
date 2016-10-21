package com.rossotti.basketball.app.integration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.splitter.AbstractMessageSplitter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import com.rossotti.basketball.dao.model.Game;

public class GameSplitter extends AbstractMessageSplitter {
	private final Logger logger = LoggerFactory.getLogger(GameSplitter.class);
	private Map<String, List<Message<?>>> splittedMessages = new HashMap<String, List<Message<?>>>();

	@Override
	protected Object splitMessage(Message<?> message) { 
		Collection<Message<?>> messages = new ArrayList<Message<?>>();
		@SuppressWarnings("unchecked")
		List<Game> games = (List<Game>) message.getPayload();
		for (int i = 0; i < games.size(); i++) {
			Game game = games.get(i);
			Message<?> msg = MessageBuilder
				.withPayload(game)
				.setReplyChannel((MessageChannel)message.getHeaders().getReplyChannel())
				.setCorrelationId(game.getGameDateTime())
				.setSequenceNumber(i)
				.setSequenceSize(games.size())
				.build();
			messages.add(msg);
			addMessage(""+ games.size(), msg);
		}
		logger.info("gameCount: " + games.size());
		return messages;
	}

	public Map<String, List<Message<?>>> getSplittedMessages() {
		return this.splittedMessages;
	}

	public List<Message<?>> getSplittedMessagesByKey(String key) {
		if (!getSplittedMessages().containsKey(key)) {
			addListOfSplittedMessages(key);
		}
		return getSplittedMessages().get(key);
	}

	private void addMessage(String key, Message<?> message) {
		getSplittedMessagesByKey(key).add(message);
	}

	private void addListOfSplittedMessages(String key) {
		getSplittedMessages().put(key, (new ArrayList<Message<?>>()));
	}
}