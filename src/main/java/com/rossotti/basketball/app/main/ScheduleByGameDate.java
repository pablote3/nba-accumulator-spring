package com.rossotti.basketball.app.main;

import com.rossotti.basketball.app.gateway.GameActivator;
import com.rossotti.basketball.app.gateway.GatewayService;
import com.rossotti.basketball.dao.model.Game;
import com.rossotti.basketball.dao.model.GameDay;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ScheduleByGameDate {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"classpath:si-config.xml",
				"classpath:applicationContext.xml"
		});
		System.out.println("begin gatewayService");
		GatewayService gatewayService = (GatewayService) context.getBean("gatewayService");
		Game game = gatewayService.processGames("2015-01-07");
		System.out.println("end gatewayService " + game.getBoxScoreHome().getTeam().getTeamKey());
		context.close();
	}
}
