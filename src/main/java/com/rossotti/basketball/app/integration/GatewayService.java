package com.rossotti.basketball.app.integration;

import java.util.List;

import org.springframework.integration.annotation.Gateway;

import com.rossotti.basketball.dao.model.Game;

public interface GatewayService {
	@Gateway(requestChannel = "gameActivatorChannel")
	List<Game> processGames(ServiceProperties serviceProperties);
}