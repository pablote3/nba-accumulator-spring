package com.rossotti.basketball.app.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rossotti.basketball.dao.model.AppGame;

public class RatingsRouter {
	private final Logger logger = LoggerFactory.getLogger(RatingsRouter.class);
	public String routeRatings(AppGame game) {
		logger.info("begin ratingsRouter: game status = " + game.getAppStatus());
		return (game.isAppServerError()) ? "aggregatorChannel" : "scheduledGameChannel";
	}
}