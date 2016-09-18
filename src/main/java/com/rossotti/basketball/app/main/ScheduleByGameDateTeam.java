package com.rossotti.basketball.app.main;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rossotti.basketball.app.gateway.GatewayService;
import com.rossotti.basketball.app.gateway.ServiceProperties;
import com.rossotti.basketball.dao.model.Game;

public class ScheduleByGameDateTeam {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"classpath:si-config.xml",
				"classpath:applicationContext_MySql.xml"
		});
		System.out.println("begin gatewayService");
		GatewayService gatewayService = (GatewayService) context.getBean("gatewayService");
		ServiceProperties properties = new ServiceProperties();
		properties.setGameDate("2015-11-06");
		properties.setGameTeam("new-york-knicks");
		List<Game> games = gatewayService.processGames(properties);
		System.out.println("end gatewayService, processed " + games.size() + " games");
		context.close();
	}
}
