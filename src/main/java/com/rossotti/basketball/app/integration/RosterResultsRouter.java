package com.rossotti.basketball.app.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rossotti.basketball.dao.model.AppGame;

public class RosterResultsRouter {
	private final Logger logger = LoggerFactory.getLogger(RosterResultsRouter.class);
	public String routeResults(AppGame appGame) {
		if (appGame.isAppServerError()) {
			logger.info("Game " + appGame.getGame().getBoxScoreAway().getTeam().getAbbr() + " at " + 
					appGame.getGame().getBoxScoreHome().getTeam().getAbbr() + " " + 
					appGame.getAppStatus() + 
					": route to gameRouterChannel"
			);
			return "gameRouterChannel";
		}
		else {
			logger.info("Game " + appGame.getGame().getBoxScoreAway().getTeam().getAbbr() + " at " + 
					appGame.getGame().getBoxScoreHome().getTeam().getAbbr() + " " + 
					appGame.getAppStatus() + 
					": route to gameScheduledChannel"
			);
			return "gameScheduledChannel";
		}
	}
}