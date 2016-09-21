package com.rossotti.basketball.app.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rossotti.basketball.dao.model.AppGame;
import com.rossotti.basketball.dao.model.Game;

public class GameRouter {
	private final Logger logger = LoggerFactory.getLogger(GameRouter.class);
	public String routeGame(Game game) {
		logger.info("begin gameRouter: game status = " + game.getStatus());
		return (game.isScheduled()) ? "gameScheduledChannel" : "gameCompletedChannel";
	}
	public String routeGame(AppGame appGame) {
		logger.info("begin gameRouter: appGame appStatus = " + appGame.getAppStatus());
		return (appGame.isAppRosterError()) ? "gameRosterChannel" : "gameAggregatorChannel";
	}
}