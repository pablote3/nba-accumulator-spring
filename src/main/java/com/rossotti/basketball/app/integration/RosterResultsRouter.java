package com.rossotti.basketball.app.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rossotti.basketball.dao.model.AppGame;

public class RosterResultsRouter {
	private final Logger logger = LoggerFactory.getLogger(RosterResultsRouter.class);
	public String routeResults(AppGame game) {
		logger.info("begin rosterResultsRouter: game status = " + game.getAppStatus());
		if (game.isAppServerError()) {
			logger.info("end rosterResultsRouter: route to gameRouterChannel");
			return "gameRouterChannel";
		}
		else {
			logger.info("end rosterResultsRouter: route to gameScheduledChannel");
			return "gameScheduledChannel";
		}
	}
}