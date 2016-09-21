package com.rossotti.basketball.app.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rossotti.basketball.dao.model.AppGame;

public class RosterResultsRouter {
	private final Logger logger = LoggerFactory.getLogger(RosterResultsRouter.class);
	public String routeResults(AppGame game) {
		logger.info("begin rosterResultsRouter: game status = " + game.getAppStatus());
		return (game.isAppServerError()) ? "gameRouteChannel" : "gameScheduledChannel";
	}
}