package com.rossotti.basketball.dao.repository;

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rossotti.basketball.dao.model.Standing;
import com.rossotti.basketball.dao.model.Team;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class StandingRepositoryTest {

	@Autowired
	private StandingRepository standingRepo;

	@Test
	public void findStandingByTeamAsOfDate_Found() {
		Standing findStanding = standingRepo.findStanding("chicago-zephyr's", new LocalDate("2015-10-30"));
		Assert.assertEquals("1-0", findStanding.getLastFive());
		Assert.assertEquals("Chicago Zephyr's", findStanding.getTeam().getFullName());
		Assert.assertTrue(findStanding.isFound());
	}

	@Test
	public void findStandingByTeamAsOfDate_NotFound_Team() {
		Standing findStanding = standingRepo.findStanding("chicago-zephyr'sss", new LocalDate("2015-10-30"));
		Assert.assertTrue(findStanding.isNotFound());
	}

	@Test
	public void findStandingByTeamAsOfDate_NotFound_BeforeAsOfDate() {
		Standing findStanding = standingRepo.findStanding("chicago-zephyr's", new LocalDate("2015-10-29"));
		Assert.assertTrue(findStanding.isNotFound());
	}

	@Test
	public void findStandingByTeamAsOfDate_NotFound_AfterAsOfDate() {
		Standing findStanding = standingRepo.findStanding("chicago-zephyr's", new LocalDate("2015-11-01"));
		Assert.assertTrue(findStanding.isNotFound());
	}

	@Test
	public void findStandingsByAsOfDate_Found() {
		List<Standing> standings = standingRepo.findStandings(new LocalDate("2015-10-30"));
		Assert.assertEquals(2, standings.size());
	}

	@Test
	public void findStandingsByDateRange_NotFound() {
		List<Standing> standings = standingRepo.findStandings(new LocalDate("1909-10-31"));
		Assert.assertEquals(0, standings.size());
	}

	@Test
	public void createStanding_Created() {
		Standing createStanding = standingRepo.createStanding(createMockStanding("chicago-zephyr's", new LocalDate("2012-07-01")));
		Standing findStanding = standingRepo.findStanding("chicago-zephyr's", new LocalDate("2012-07-01"));
		Assert.assertTrue(createStanding.isCreated());
		Assert.assertTrue(findStanding.getConferenceWins().equals((short)7));
	}

	public void createStanding_OverlappingDates() {
		Standing createStanding = standingRepo.createStanding(createMockStanding("chicago-zephyr's", new LocalDate("2015-10-30")));
		Assert.assertTrue(createStanding.isFound());
	}

	@Test(expected=DataIntegrityViolationException.class)
	public void createStanding_MissingRequiredData() {
		Standing standing = createMockStanding("salinas-cowboys", new LocalDate("2012-07-01"));
		standing.setLastFive(null);
		standingRepo.createStanding(standing);
	}

	@Test
	public void updateStanding() {
		Standing updateStanding = updateMockStanding("st-louis-bomber's", new LocalDate("2015-10-31"), "10th");
		standingRepo.updateStanding(updateStanding);
		Standing findStanding = standingRepo.findStanding("st-louis-bomber's", new LocalDate("2015-10-31"));
		Assert.assertTrue(findStanding.getRank().equals((short)10));
	}

	@Test
	public void updateStanding_NotFound() {
		standingRepo.updateStanding(updateMockStanding("st-louis-bomb's", new LocalDate("2015-10-31"), "10th"));
	}

	@Test(expected=DataIntegrityViolationException.class)
	public void updateStanding_MissingRequiredData() {
		Standing standing = updateMockStanding("st-louis-bomber's", new LocalDate("2015-10-31"), null);
		standingRepo.updateStanding(standing);
	}

	@Test
	public void deleteStanding_Deleted() {
		Standing deleteStanding = standingRepo.deleteStanding("salinas-cowboys", new LocalDate("2015-10-31"));
		Standing findStanding = standingRepo.findStanding("salinas-cowboys", new LocalDate("2015-10-31"));
		Assert.assertTrue(deleteStanding.isDeleted());
		Assert.assertTrue(findStanding.isNotFound());
	}

	@Test
	public void deleteStanding_NotFound() {
		Standing deleteStanding = standingRepo.deleteStanding("chalinas-cowboys", new LocalDate("2015-10-31"));
		Assert.assertTrue(deleteStanding.isNotFound());
	}

	private Standing createMockStanding(String teamKey, LocalDate asOfDate) {
		Standing standing = new Standing();
		standing.setTeam(getMockTeam(teamKey));
		standing.setStandingDate(asOfDate);
		standing.setRank((short)3);
		standing.setOrdinalRank("3rd");
		standing.setGamesWon((short)15);
		standing.setGamesLost((short)25);
		standing.setStreak("L5");
		standing.setStreakType("loss");
		standing.setStreakTotal((short)5);
		standing.setGamesBack((float)3.5);
		standing.setPointsFor((short)1895);
		standing.setPointsAgainst((short)2116);
		standing.setHomeWins((short)10);
		standing.setHomeLosses((short)10);
		standing.setAwayWins((short)5);
		standing.setAwayLosses((short)15);
		standing.setConferenceWins((short)7);
		standing.setConferenceLosses((short)8);
		standing.setLastFive("0-5");
		standing.setLastTen("3-7");
		standing.setGamesPlayed((short)40);
		standing.setPointsScoredPerGame((float)95.5);
		standing.setPointsAllowedPerGame((float)102.5);
		standing.setWinPercentage((float)0.375);
		standing.setPointDifferential((short)221);
		standing.setPointDifferentialPerGame((float)7.0);
		standing.setOpptGamesWon(4);
		standing.setOpptGamesPlayed(5);
		standing.setOpptOpptGamesWon(15);
		standing.setOpptOpptGamesPlayed(20);
		return standing;
	}

	private Team getMockTeam(String teamKey) {
		Team team = new Team();
		team.setId(1L);
		team.setTeamKey(teamKey);
		return team;
	}

	private Standing updateMockStanding(String teamKey, LocalDate asOfDate, String ordinalRank) {
		Standing standing = new Standing();
		standing.setTeam(getMockTeam(teamKey));
		standing.setStandingDate(asOfDate);
		standing.setRank((short)10);
		standing.setOrdinalRank(ordinalRank);
		standing.setGamesWon((short)15);
		standing.setGamesLost((short)25);
		standing.setStreak("L5");
		standing.setStreakType("loss");
		standing.setStreakTotal((short)5);
		standing.setGamesBack((float)3.5);
		standing.setPointsFor((short)1895);
		standing.setPointsAgainst((short)2116);
		standing.setHomeWins((short)10);
		standing.setHomeLosses((short)10);
		standing.setAwayWins((short)5);
		standing.setAwayLosses((short)15);
		standing.setConferenceWins((short)7);
		standing.setConferenceLosses((short)8);
		standing.setLastFive("0-5");
		standing.setLastTen("3-7");
		standing.setGamesPlayed((short)40);
		standing.setPointsScoredPerGame((float)95.5);
		standing.setPointsAllowedPerGame((float)102.5);
		standing.setWinPercentage((float)0.375);
		standing.setPointDifferential((short)221);
		standing.setPointDifferentialPerGame((float)7.0);
		standing.setOpptGamesWon(4);
		standing.setOpptGamesPlayed(5);
		standing.setOpptOpptGamesWon(15);
		standing.setOpptOpptGamesPlayed(20);
		return standing;
	}
}
