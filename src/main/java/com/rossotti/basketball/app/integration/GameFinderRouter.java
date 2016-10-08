package com.rossotti.basketball.app.integration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rossotti.basketball.dao.model.Game;

public class GameFinderRouter {
	private final Logger logger = LoggerFactory.getLogger(GameFinderRouter.class);
	public String routeGame(List<Game> games) {
		if (games.size() > 0) {
			logger.info("gameCount: " + games.size() + ": route to gameSplitterChannel");
			return "gameSplitterChannel";
		}
		else {
			logger.info("gameCount: " + games.size() + ": route to gameActivatorChannel");			
			return "gameActivatorChannel";
		}
	}
}