package com.rossotti.basketball.app.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rossotti.basketball.app.integration.GatewayService;
import com.rossotti.basketball.app.integration.ServiceProperties;
import com.rossotti.basketball.dao.model.AppStandings;

public class ScoreGameByDate {
	public static void main(String[] args) {
		System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");
		System.setProperty(org.slf4j.impl.SimpleLogger.SHOW_DATE_TIME_KEY, "TRUE");
		System.setProperty(org.slf4j.impl.SimpleLogger.DATE_TIME_FORMAT_KEY, "yyyy-MM-dd HH:mm:ss");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				"classpath:si-config.xml",
				"classpath:applicationContext_MySql.xml"
		});
		System.out.println("\n" + "begin gatewayService");
		GatewayService gatewayService = (GatewayService) context.getBean("gatewayService");
		ServiceProperties properties = new ServiceProperties();
		properties.setGameDate("2012-12-11");
		AppStandings appStandings = gatewayService.processGames(properties);
		System.out.println("end gatewayService, processed " + appStandings.getStandings().size() + " standings" + "\n");
		context.close();
	}
}
