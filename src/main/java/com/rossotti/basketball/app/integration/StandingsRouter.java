package com.rossotti.basketball.app.integration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rossotti.basketball.dao.model.Game;

public class StandingsRouter {
	private final Logger logger = LoggerFactory.getLogger(StandingsRouter.class);
	public String routeStandings(List<Game> games) {
		if (games.size() > 0) {
			for (int i = 0; i < games.size(); i++) {
				Game game = games.get(i);
				if (game != null) {
					if (!game.isCompleted()) {
						logger.info("game " + i + " " + game.getStatus() + ": route to gameActivatorChannel");
						return "gameActivatorChannel";
					}
				}
				else {
					logger.info("game " + i + " null: route to gameActivatorChannel");
					return "gameActivatorChannel";
				}
			}
		}
		else {
			logger.info("no games completed: route to gameActivatorChannel");
			return "gameActivatorChannel";
		}
		logger.info("gameCount: " + games.size() + " Completed: route to standingsRankChannel");
		return "standingsRankChannel";
	}
}