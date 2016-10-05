package com.rossotti.basketball.app.integration;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rossotti.basketball.dao.model.Game;

public class StandingsRouter {
	private final Logger logger = LoggerFactory.getLogger(StandingsRouter.class);
	public String routeStandings(List<Game> games) {
		logger.info("begin standingsRouter: game count = " + games.size());
		if (games.size() > 0) {
			for (int i = 0; i < games.size(); i++) {
				Game game = games.get(i);
				if (game != null) {
					if (!game.isCompleted()) {
						logger.info("end standingsRouter - gatewayRequestChannel - incomplete game");
						return "gatewayRequestChannel";
					}
				}
				else {
					logger.info("end standingsRouter - gatewayRequestChannel - null game");
					return "gatewayRequestChannel";
				}
			}
		}
		else {
			logger.info("end standingsRouter - gatewayRequestChannel - no games routed");
			return "gatewayRequestChannel";
		}
		logger.info("end standingsRouter - standingsCompletedChannel");
		return "standingsCompletedChannel";
	}
}