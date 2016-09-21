package com.rossotti.basketball.app.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rossotti.basketball.dao.model.AppGame;

public class GameResultsRouter {
	private final Logger logger = LoggerFactory.getLogger(GameResultsRouter.class);
	public String routeGame(AppGame game) {
		logger.info("begin gameResultsRouter: game status = " + game.getAppStatus());
		return (game.isAppRosterError()) ? "gameRouteChannel" : "gameAggregatorChannel";
	}
}