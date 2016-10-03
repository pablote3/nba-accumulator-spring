package com.rossotti.basketball.app.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rossotti.basketball.dao.model.AppGame;

public class GameResultsRouter {
	private final Logger logger = LoggerFactory.getLogger(GameResultsRouter.class);
	public String routeGame(AppGame game) {
		logger.info("begin gameResultsRouter: game status = " + game.getAppStatus());
		if (game.isAppRosterError()) {
			logger.info("end gameResultsRouter: route to gameRouterChannel");
			return "gameRouterChannel";
		}
		else {
			logger.info("end gameResultsRouter: route to gameAggregatorChannel");
			return "gameAggregatorChannel";
		}
	}
}