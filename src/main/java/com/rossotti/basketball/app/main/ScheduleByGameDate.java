package com.rossotti.basketball.app.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rossotti.basketball.app.gateway.GatewayService;
import com.rossotti.basketball.dao.model.Game;

public class ScheduleByGameDate {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"classpath:si-config.xml",
				"classpath:applicationContext_MySql.xml"
		});
		System.out.println("begin gatewayService");
		GatewayService gatewayService = (GatewayService) context.getBean("gatewayService");
		Game game = gatewayService.processGames("2015-11-06");
		System.out.println("end gatewayService " + game.getBoxScoreHome().getTeam().getTeamKey());
		context.close();
	}
}
