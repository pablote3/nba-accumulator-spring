package com.rossotti.basketball.app.business;

import com.rossotti.basketball.client.dto.ClientSource;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rossotti.basketball.app.exception.PropertyException;
import com.rossotti.basketball.app.service.GameService;
import com.rossotti.basketball.app.service.OfficialService;
import com.rossotti.basketball.app.service.PropertyService;
import com.rossotti.basketball.app.service.RosterPlayerService;
import com.rossotti.basketball.app.service.TeamService;
import com.rossotti.basketball.client.dto.GameDTO;
import com.rossotti.basketball.client.service.FileStatsService;
import com.rossotti.basketball.client.service.RestStatsService;
import com.rossotti.basketball.dao.exception.NoSuchEntityException;
import com.rossotti.basketball.dao.model.AppGame;
import com.rossotti.basketball.dao.model.AppStatus;
import com.rossotti.basketball.dao.model.BoxScore;
import com.rossotti.basketball.dao.model.BoxScore.Result;
import com.rossotti.basketball.dao.model.Game;
import com.rossotti.basketball.dao.model.GameStatus;
import com.rossotti.basketball.dao.model.Official;
import com.rossotti.basketball.dao.model.RosterPlayer;
import com.rossotti.basketball.util.DateTimeUtil;

@Service
public class GameBusiness {
	@Autowired
	private PropertyService propertyService;

	@Autowired
	private RestStatsService restStatsService;

	@Autowired
	private FileStatsService fileStatsService;

	@Autowired
	private OfficialService officialService;

	@Autowired
	private RosterPlayerService rosterPlayerService;

	@Autowired
	private TeamService teamService;
	
	@Autowired
	private GameService gameService;
	
	private final Logger logger = LoggerFactory.getLogger(GameBusiness.class);
	
	public AppGame scoreGame(Game game) {
		AppGame appGame = new AppGame();
		try {
			BoxScore awayBoxScore = game.getBoxScoreAway();
			BoxScore homeBoxScore = game.getBoxScoreHome();
			String awayTeamKey = awayBoxScore.getTeam().getTeamKey();
			String homeTeamKey = homeBoxScore.getTeam().getTeamKey();
			LocalDateTime gameDateTime = game.getGameDateTime();
			LocalDate gameDate = DateTimeUtil.getLocalDate(gameDateTime);

			String event = DateTimeUtil.getStringDateNaked(gameDateTime) + "-" + awayTeamKey + "-at-" + homeTeamKey;

			if (game.isScheduled()) {
				logger.info('\n' + "Scheduled game ready to be scored: " + event);

				GameDTO gameDTO;
				ClientSource clientSource = propertyService.getProperty_ClientSource("accumulator.source.boxScore");
				if (clientSource == ClientSource.File) {
					gameDTO = fileStatsService.retrieveBoxScore(event);
				}
				else if (clientSource == ClientSource.Api) {
					gameDTO = restStatsService.retrieveBoxScore(event);
				}
				else {
					throw new PropertyException("Unknown");
				}

				if (gameDTO.isFound()) {
					awayBoxScore.updateTotals(gameDTO.away_totals);
					homeBoxScore.updateTotals(gameDTO.home_totals);
					awayBoxScore.updatePeriodScores(gameDTO.away_period_scores);
					homeBoxScore.updatePeriodScores(gameDTO.home_period_scores);
					awayBoxScore.setBoxScorePlayers(rosterPlayerService.getBoxScorePlayers(gameDTO.away_stats, gameDate, awayTeamKey));
					homeBoxScore.setBoxScorePlayers(rosterPlayerService.getBoxScorePlayers(gameDTO.home_stats, gameDate, homeTeamKey));
					game.setGameOfficials(officialService.getGameOfficials(gameDTO.officials, gameDate));
					awayBoxScore.setTeam(teamService.findTeam(awayTeamKey, gameDate));
					homeBoxScore.setTeam(teamService.findTeam(homeTeamKey, gameDate));

					if (gameDTO.away_totals.getPoints() > gameDTO.home_totals.getPoints()) {
						awayBoxScore.setResult(Result.Win);
						homeBoxScore.setResult(Result.Loss);
					}
					else {
						awayBoxScore.setResult(Result.Loss);
						homeBoxScore.setResult(Result.Win);
					}

					awayBoxScore.setDaysOff((short)DateTimeUtil.getDaysBetweenTwoDateTimes(gameService.findPreviousGameDateTime(gameDate, awayTeamKey), gameDateTime));
					homeBoxScore.setDaysOff((short)DateTimeUtil.getDaysBetweenTwoDateTimes(gameService.findPreviousGameDateTime(gameDate, homeTeamKey), gameDateTime));
					game.setStatus(GameStatus.Completed);
					Game updatedGame = gameService.updateGame(game);
					if (updatedGame.isUpdated()) {
						logger.info("Game Scored " + awayTeamKey +  " " + awayBoxScore.getPoints() + " " + homeTeamKey +  " " + homeBoxScore.getPoints());
						appGame.setGame(gameService.findByDateTeam(gameDate, awayTeamKey));
						appGame.setAppStatus(AppStatus.Completed);
					}
					else if (updatedGame.isNotFound()) {
						logger.info("Unable to find game for update - " + updatedGame.getStatus());
						appGame.setAppStatus(AppStatus.ServerError);
					}
				}
				else if (gameDTO.isNotFound()) {
					logger.info('\n' + "" + " unable to find game");
					appGame.setAppStatus(AppStatus.ClientError);
				}
				else if (gameDTO.isClientException()) {
					logger.info('\n' + "" + " client exception");
					appGame.setAppStatus(AppStatus.ClientError);
				}
			}
			else {
				logger.info('\n' + "" + game.getStatus() + " game not eligible to be scored: " + event.toString());
				appGame.setAppStatus(AppStatus.ServerError);
			}
		}
		catch (NoSuchEntityException nse) {
			if (nse.getEntityClass().equals(Official.class)) {
				logger.info("Official not found - need to add official");
			}
			else if (nse.getEntityClass().equals(RosterPlayer.class)) {
				logger.info("Roster Player not found - need to rebuild active roster");
			}
			appGame.setAppStatus(AppStatus.ClientError);
		}
		catch (PropertyException pe) {
			logger.info("property exception = " + pe);
			appGame.setAppStatus(AppStatus.ServerError);
		}
		catch (Exception e) {
			logger.info("unexpected exception = " + e);
			appGame.setAppStatus(AppStatus.ServerError);
		}
		return appGame;
	}
	
	public AppGame completeGame(Game game) {
		AppGame appGame = new AppGame();
		appGame.setGame(game);
		appGame.setAppStatus(AppStatus.Completed);
		return appGame;
	}
}
