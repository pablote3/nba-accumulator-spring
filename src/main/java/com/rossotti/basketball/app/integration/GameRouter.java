package com.rossotti.basketball.app.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rossotti.basketball.dao.model.AppGame;
import com.rossotti.basketball.dao.model.Game;

public class GameRouter {
	private final Logger logger = LoggerFactory.getLogger(GameRouter.class);
	@SuppressWarnings("SameReturnValue")
	public String routeGame(Game game) {
		logger.info("Game " + game.getBoxScoreAway().getTeam().getAbbr() + " at " + 
					game.getBoxScoreHome().getTeam().getAbbr() + " " + 
					game.getStatus() + 
					": route to gameScoreChannel"
		);
		return "gameScoreChannel";
	}
	public String routeGame(AppGame appGame) {
		if (appGame.isAppRosterUpdate()) {
			logger.info("AppGame " + appGame.getGame().getBoxScoreAway().getTeam().getAbbr() + " at " + 
						appGame.getGame().getBoxScoreHome().getTeam().getAbbr() + " " +  
						appGame.getAppStatus() + 
						": route to rosterLoadChannel"
			);
			return "rosterLoadChannel";
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