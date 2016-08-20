package com.rossotti.basketball.dao.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rossotti.basketball.dao.model.Player;
import com.rossotti.basketball.dao.model.StatusCodeDAO;

@Repository
@Transactional
public class PlayerRepository {
	@Autowired
	private SessionFactory sessionFactory;

	public Player findPlayer(String lastName, String firstName, LocalDate birthdate) {
		String sql = new StringBuilder()
				.append("from Player ")
				.append("where lastName = :lastName ")
				.append("and firstName = :firstName ")
				.append("and birthdate = :birthdate")
				.toString();
		Query query = getSession().createQuery(sql);
		query.setParameter("lastName", lastName);
		query.setParameter("firstName", firstName);
		query.setParameter("birthdate", birthdate);

		Player player = (Player)query.uniqueResult();
		if (player == null) {
			player = new Player(StatusCodeDAO.NotFound);
		}
		else {
			player.setStatusCode(StatusCodeDAO.Found);
		}
		return player;
	}

	@SuppressWarnings("unchecked")
	public List<Player> findPlayers(String lastName, String firstName) {
		String sql = new StringBuilder()
				.append("from Player ")
				.append("where lastName = :lastName ")
				.append("and firstName = :firstName ")
				.toString();
		Query query = getSession().createQuery(sql);
		query.setParameter("lastName", lastName);
		query.setParameter("firstName", firstName);

		List<Player> players = query.list();
		if (players == null) {
			players = new ArrayList<Player>();
		}
		return players;
	}

	public Player createPlayer(Player createPlayer) {
		Player player = findPlayer(createPlayer.getLastName(), createPlayer.getFirstName(), createPlayer.getBirthdate());
		if (player.isNotFound()) {
			getSession().persist(createPlayer);
			createPlayer.setStatusCode(StatusCodeDAO.Created);
		}
		else {
			return player;
		}
		return createPlayer;
	}

	public Player updatePlayer(Player updatePlayer) {
		Player player = findPlayer(updatePlayer.getLastName(), updatePlayer.getFirstName(), updatePlayer.getBirthdate());
		if (player.isFound()) {
			player.setLastName(updatePlayer.getLastName());
			player.setFirstName(updatePlayer.getFirstName());
			player.setBirthdate(updatePlayer.getBirthdate());
			player.setDisplayName(updatePlayer.getDisplayName());
			player.setHeight(updatePlayer.getHeight());
			player.setWeight(updatePlayer.getWeight());
			player.setBirthplace(updatePlayer.getBirthplace());
			player.setStatusCode(StatusCodeDAO.Updated);
			getSession().saveOrUpdate(player);
		}
		return player;
	}

	public Player deletePlayer(String lastName, String firstName, LocalDate birthdate) {
		Player player = findPlayer(lastName, firstName, birthdate);
		if (player.isFound()) {
			getSession().delete(player);
			player = new Player(StatusCodeDAO.Deleted);
		}
		return player;
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}
