package com.rossotti.basketball.app.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rossotti.basketball.dao.model.AppGame;
import com.rossotti.basketball.dao.model.Game;

public class GameRouter {
	private final Logger logger = LoggerFactory.getLogger(GameRouter.class);
	public String routeGame(Game game) {
		logger.info("begin gameRouter for game: gameStatus = " + game.getStatus());
		if (game.isScheduled()) {
			logger.info("end gameRouter: route to gameScheduledChannel");
			return "gameScheduledChannel";
		}
		else {
			logger.info("end gameRouter: route to gameCompletedChannel");
			return "gameCompletedChannel";
		}
	}
	public String routeGame(AppGame appGame) {
		logger.info("begin gameRouter for appGame: appStatus = " + appGame.getAppStatus());
		if (appGame.isAppRosterError()) {
			logger.info("end gameRouter: route to gameRosterChannel");
			return "gameRosterChannel";
		}
		else {
			logger.info("end gameRouter: route to gameAggregatorChannel");
			return "gameAggregatorChannel";
		}
	}
}