package com.rossotti.basketball.app.integration;

import java.util.List;

import com.rossotti.basketball.dao.model.Game;

public interface GatewayService {
	List<Game> processGames(ServiceProperties serviceProperties);
}