package com.rossotti.basketball.app.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

import com.rossotti.basketball.app.service.GameService;
import com.rossotti.basketball.app.service.TeamService;

import com.rossotti.basketball.dao.model.BoxScore;
import com.rossotti.basketball.dao.model.Game;
import com.rossotti.basketball.dao.model.GameStatus;
import com.rossotti.basketball.dao.model.Team;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LoadSchedule {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		TeamService teamService = (TeamService) context.getBean("teamService");
		GameService gameService = (GameService) context.getBean("gameService");

		Path path =  Paths.get(System.getProperty("config.load")).resolve("Schedule_2014-2015.csv");
		File file = path.toFile();

		BufferedReader bufRdr = null;
		try {
			bufRdr = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;
		Game game;
		LocalDate gameLocalDate;
		LocalDateTime gameLocalDateTime;
		BoxScore boxScoreHome;
		BoxScore boxScoreAway;
		Team teamHome;
		Team teamAway;

		DateTime date;
		int i = 0;

		//read each line of text file
		try {
			assert bufRdr != null;
			bufRdr.readLine();								            //jump over header line
			while((line = bufRdr.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, ",");
				String gameDate = st.nextToken();                        //start date
				String gameDateTime = gameDate + " " + st.nextToken();    //start time (ET)
				String awayTeam = st.nextToken().trim();
				String homeTeam = st.nextToken().trim();

				gameLocalDate = new LocalDate(gameDate);
				gameLocalDateTime = new LocalDateTime(gameDateTime);

				teamHome = teamService.findTeam(homeTeam, gameLocalDate);
				teamAway = teamService.findTeam(awayTeam, gameLocalDate);

				game = new Game();
				game.setGameDateTime(gameLocalDateTime);
				game.setStatus(GameStatus.Scheduled);
				game.setSeasonType(Game.SeasonType.Regular);

				boxScoreAway = new BoxScore();
				boxScoreAway.setLocation(BoxScore.Location.Away);
				boxScoreAway.setTeam(teamAway);
				game.addBoxScore(boxScoreAway);

				boxScoreHome = new BoxScore();
				boxScoreHome.setLocation(BoxScore.Location.Home);
				boxScoreHome.setTeam(teamHome);
				game.addBoxScore(boxScoreHome);

//				gameService.create(game, ProcessingType.batch);
				System.out.println("i = " + i++ + " " + teamAway.getFullName() + " " + teamHome.getFullName());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//close the file
		try {
			bufRdr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}