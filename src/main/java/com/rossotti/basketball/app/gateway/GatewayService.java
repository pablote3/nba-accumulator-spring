package com.rossotti.basketball.app.gateway;

import java.util.List;

import org.springframework.integration.annotation.Gateway;

import com.rossotti.basketball.dao.model.Game;

public interface GatewayService {
	@Gateway(requestChannel = "gatewayRequestChannel")
	List<Game> processGames(ServiceProperties serviceProperties);
}