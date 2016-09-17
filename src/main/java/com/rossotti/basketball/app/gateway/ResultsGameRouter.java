package com.rossotti.basketball.app.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rossotti.basketball.dao.model.AppGame;

public class ResultsGameRouter {
	private final Logger logger = LoggerFactory.getLogger(ResultsGameRouter.class);
	public String routeGame(AppGame game) {
		logger.info("begin evaluateGameRouter: game status = " + game.getAppStatus());
		return (game.isAppRosterError()) ? "rosterUpdateChannel" : "aggregatorChannel";
	}
}