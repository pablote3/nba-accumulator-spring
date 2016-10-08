package com.rossotti.basketball.app.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rossotti.basketball.dao.model.AppGame;
import com.rossotti.basketball.dao.model.Game;

public class GameRouter {
	private final Logger logger = LoggerFactory.getLogger(GameRouter.class);
	public String routeGame(Game game) {
		if (game.isScheduled()) {
			logger.info("Game " + game.getBoxScoreAway().getTeam().getAbbr() + " at " + 
						game.getBoxScoreHome().getTeam().getAbbr() + " " + 
						game.getStatus() + 
						": route to gameScheduledChannel"
			);
			return "gameScheduledChannel";
		}
		else {
			logger.info("Game " + game.getBoxScoreAway().getTeam().getAbbr() + " at " + 
						game.getBoxScoreHome().getTeam().getAbbr() + " " + 
						game.getStatus() + 
						": route to gameCompletedChannel"
			);
			return "gameCompletedChannel";
		}
	}
	public String routeGame(AppGame appGame) {
		if (appGame.isAppRosterUpdate()) {
			logger.info("AppGame " + appGame.getGame().getBoxScoreAway().getTeam().getAbbr() + " at " + 
						appGame.getGame().getBoxScoreHome().getTeam().getAbbr() + " " +  
						appGame.getAppStatus() + 
						": route to gameRosterChannel"
			);
			return "gameRosterChannel";
		}
		else {
			logger.info("AppGame " + appGame.getGame().getBoxScoreAway().getTeam().getAbbr() + " at " + 
						appGame.getGame().getBoxScoreHome().getTeam().getAbbr() + " " +  
						appGame.getAppStatus() + 
						": route to gameAggregatorChannel"
			);
			return "gameAggregatorChannel";
		}
	}
}