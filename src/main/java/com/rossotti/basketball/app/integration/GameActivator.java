package com.rossotti.basketball.app.integration;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rossotti.basketball.app.service.GameService;
import com.rossotti.basketball.dao.model.Game;
import com.rossotti.basketball.util.DateTimeUtil;

public class GameActivator {
	@Autowired
	private GameService gameService;
	private final Logger logger = LoggerFactory.getLogger(GameActivator.class);
	public List<Game> processGames(ServiceProperties properties) {
		logger.info("begin processGames");
		List<Game> games = new ArrayList<Game>();
		LocalDate gameDate = DateTimeUtil.getLocalDate(properties.getGameDate());
		if (properties.getGameTeam() == null || properties.getGameTeam().isEmpty()) {
			games = gameService.findByDate(gameDate);
		}
		else {
			Game game = gameService.findByDateTeam(gameDate, properties.getGameTeam());
			games.add(game);
		}
		logger.info("end processGames");
		return games;
	}
}