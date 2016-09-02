package com.rossotti.basketball.app.gateway;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.rossotti.basketball.dao.model.AppGame;
import com.rossotti.basketball.dao.model.Game;
import com.rossotti.basketball.dao.model.GameDay;
import com.rossotti.basketball.dao.model.GameDay.StatusCode;

public class GameAggregator {
	private final Logger logger = LoggerFactory.getLogger(GameAggregator.class);
	public GameDay aggregate(Collection<Message<?>> games) {
		logger.info("begin gameAggregator");
		GameDay gameDay = new GameDay();
		for (Message<?> msg : games) {
			AppGame appGame = (AppGame)msg.getPayload();
			logger.info("appgame away team = " + appGame.getGame().getBoxScoreAway().getTeam().getAbbr());
			List<Game> gameList = new ArrayList<Game>();
			gameList.add(appGame.getGame());
			gameDay.setGames(gameList);
			logger.info("appgame correlation key = " + GameCorrelationStrategy.CORRELATION_KEY);
			gameDay.setGameDate((LocalDate) msg.getHeaders().get(GameCorrelationStrategy.CORRELATION_KEY));
		}
		gameDay.setStatusCode(StatusCode.Complete);
		logger.info("end gameAggregator");
		return gameDay;
	}
}