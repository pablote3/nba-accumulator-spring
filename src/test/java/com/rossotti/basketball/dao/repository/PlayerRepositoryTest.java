package com.rossotti.basketball.dao.repository;

import java.util.List;

import org.hibernate.PropertyValueException;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rossotti.basketball.dao.model.Player;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext_h2.xml"})
public class PlayerRepositoryTest {

	@Autowired
	private PlayerRepository playerRepo;

	@Test
	public void findPlayerByName_Found_MatchBirthdate() {
		Player findPlayer = playerRepo.findPlayer("Puzdrakiew'icz", "Luke", new LocalDate("2002-02-20"));
		Assert.assertEquals("Luke Puzdrakiew'icz", findPlayer.getDisplayName());
		Assert.assertEquals(2, findPlayer.getRosterPlayers().size());
		Assert.assertTrue(findPlayer.isFound());
	}

	@Test
	public void findPlayerByName_NotFound_LastName() {
		Player findPlayer = playerRepo.findPlayer("Puzdrakiew''icz", "Luke", new LocalDate("2002-02-20"));
		Assert.assertTrue(findPlayer.isNotFound());
	}

	@Test
	public void findPlayerByName_NotFound_FirstName() {
		Player findPlayer = playerRepo.findPlayer("Puzdrakiew'icz", "Luk", new LocalDate("2002-02-20"));
		Assert.assertTrue(findPlayer.isNotFound());
	}

	@Test
	public void findPlayerByName_NotFound_Birthdate() {
		Player findPlayer = playerRepo.findPlayer("Puzdrakiew'icz", "Luke", new LocalDate("2002-02-21"));
		Assert.assertTrue(findPlayer.isNotFound());
	}

	@Test
	public void findPlayerByName_Found_UTF_8() {
		Player findPlayer = playerRepo.findPlayer("Valančiūnas", "Jonas", new LocalDate("1992-05-06"));
		Assert.assertEquals("Jonas Valančiūnas", findPlayer.getDisplayName());
		Assert.assertEquals("Utėnai, Lithuania", findPlayer.getBirthplace());
		Assert.assertTrue(findPlayer.isFound());
	}

	@Test
	public void findPlayersByName_Found() {
		List<Player> findPlayers = playerRepo.findPlayers("Puzdrakiew'icz","Luke");
		Assert.assertEquals(1, findPlayers.size());
	}

	@Test
	public void findPlayersByName_NotFound() {
		List<Player> findPlayers = playerRepo.findPlayers("Puzdrakiewicz", "Thady");
		Assert.assertEquals(0, findPlayers.size());
	}

	@Test
	public void createPlayer_Created_UniqueName() {
		Player createPlayer = playerRepo.createPlayer(createMockPlayer("Puzdrakiewicz", "Fred", new LocalDate("1968-11-08"), "Fred Puzdrakiewicz"));
		Player findPlayer = playerRepo.findPlayer("Puzdrakiewicz", "Fred", new LocalDate("1968-11-08"));
		Assert.assertTrue(createPlayer.isCreated());
		Assert.assertEquals("Fred Puzdrakiewicz", findPlayer.getDisplayName());
	}

	@Test
	public void createPlayer_Created_UniqueBirthdate() {
		Player createPlayer = playerRepo.createPlayer(createMockPlayer("Puzdrakiewicz", "Michelle", new LocalDate("1969-09-09"), "Michelle Puzdrakiewicz2"));
		Player findPlayer = playerRepo.findPlayer("Puzdrakiewicz", "Michelle", new LocalDate("1969-09-09"));
		Assert.assertTrue(createPlayer.isCreated());
		Assert.assertEquals("Michelle Puzdrakiewicz2", findPlayer.getDisplayName());
	}

	public void createPlayer_Duplicate_IdenticalBirthdate() {
		Player createPlayer = playerRepo.createPlayer(createMockPlayer("Puzdrakiewicz", "Michelle", new LocalDate("1969-09-08"), "Michelle Puzdrakiewicz"));
		Assert.assertTrue(createPlayer.isFound());
	}

	@Test(expected=PropertyValueException.class)
	public void createPlayer_Exception_MissingRequiredData() {
		Player createPlayer = new Player();
		createPlayer.setLastName("missing-required-data");
		createPlayer.setFirstName("missing-required-data");
		playerRepo.createPlayer(createPlayer);
	}

	@Test
	public void updatePlayer_Updated() {
		Player updatePlayer = playerRepo.updatePlayer(updateMockPlayer("Puzdrakiewicz", "Thad", new LocalDate("1966-06-02"), "Thad Puzdrakiewicz"));
		Player findPlayer = playerRepo.findPlayer("Puzdrakiewicz", "Thad", new LocalDate("1966-06-02"));
		Assert.assertTrue(updatePlayer.isUpdated());
		Assert.assertEquals((short)215, findPlayer.getWeight().shortValue());
	}

	@Test
	public void updatePlayer_NotFound() {
		Player updatePlayer = playerRepo.updatePlayer(updateMockPlayer("Puzdrakiewicz", "Thad", new LocalDate("2009-06-21"), "Thad Puzdrakiewicz"));
		Assert.assertTrue(updatePlayer.isNotFound());
	}

	@Test(expected=DataIntegrityViolationException.class)
	public void updatePlayer_Exception_MissingRequiredData() {
		Player updatePlayer = updateMockPlayer("Puzdrakiewicz", "Thad", new LocalDate("1966-06-02"), null);
		playerRepo.updatePlayer(updatePlayer);
	}

	@Test
	public void deletePlayer_Deleted() {
		Player deletePlayer = playerRepo.deletePlayer("Puzdrakiewicz", "Tuey", new LocalDate("1966-06-07"));
		Player findPlayer = playerRepo.findPlayer("Puzdrakiewicz", "Tuey", new LocalDate("1966-06-07"));
		Assert.assertTrue(deletePlayer.isDeleted());
		Assert.assertTrue(findPlayer.isNotFound());
	}

	@Test
	public void deletePlayer_NotFound() {
		Player deletePlayer = playerRepo.deletePlayer("Puzdrakiewicz", "Tooey", new LocalDate("1966-06-10"));
		Assert.assertTrue(deletePlayer.isNotFound());
	}

	private Player createMockPlayer(String lastName, String firstName, LocalDate birthdate, String displayName) {
		Player player = new Player();
		player.setLastName(lastName);
		player.setFirstName(firstName);
		player.setBirthdate(birthdate);
		player.setDisplayName(displayName);
		player.setHeight((short)79);
		player.setWeight((short)195);
		player.setBirthplace("Monroe, Louisiana, USA");
		return player;
	}

	private Player updateMockPlayer(String lastName, String firstName, LocalDate birthdate, String displayName) {
		Player player = new Player();
		player.setLastName(lastName);
		player.setFirstName(firstName);
		player.setBirthdate(birthdate);
		player.setDisplayName(displayName);
		player.setHeight((short)79);
		player.setWeight((short)215);
		player.setBirthplace("Monroe, Louisiana, USA");
		return player;
	}
}
