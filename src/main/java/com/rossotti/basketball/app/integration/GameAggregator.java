package com.rossotti.basketball.app.integration;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.rossotti.basketball.dao.model.AppGame;
import com.rossotti.basketball.dao.model.AppStatus;
import com.rossotti.basketball.dao.model.Game;

public class GameAggregator {
	private final Logger logger = LoggerFactory.getLogger(GameAggregator.class);
	public List<Game> aggregate(Collection<Message<?>> games) {
		logger.info("begin gameAggregator");
		List<Game> gameList = new ArrayList<Game>();
		for (Message<?> msg : games) {
			logger.info("msg.correlationId = " + msg.getHeaders().get("correlationId"));
			logger.info("msg.sequenceNumber = " + msg.getHeaders().get("sequenceNumber"));
			logger.info("msg.sequenceSize = " + msg.getHeaders().get("sequenceSize"));
			
			AppGame appGame = (AppGame)msg.getPayload();
			if (appGame.getAppStatus().equals(AppStatus.ServerError) || appGame.getAppStatus().equals(AppStatus.ClientError)) {
				gameList.add(null);
			}
			else {
				logger.info("appgame  = " + appGame.getGame().getBoxScoreAway().getTeam().getAbbr() + " at " + appGame.getGame().getBoxScoreHome().getTeam().getAbbr());
				gameList.add(appGame.getGame());
			}
		}
		logger.info("gameList.size() = " + gameList.size());
		logger.info("end gameAggregator");
		return gameList;
	}
}