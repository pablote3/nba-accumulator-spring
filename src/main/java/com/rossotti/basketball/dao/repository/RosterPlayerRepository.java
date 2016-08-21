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

import com.rossotti.basketball.dao.model.RosterPlayer;
import com.rossotti.basketball.dao.model.StatusCodeDAO;

@Repository
@Transactional
public class RosterPlayerRepository {
	@Autowired
	private SessionFactory sessionFactory;

	public RosterPlayer findRosterPlayer(String lastName, String firstName, LocalDate birthdate, LocalDate asOfDate) {
		String sql = new StringBuilder()
				.append("select rp from RosterPlayer rp ")
				.append("inner join rp.player p ")
				.append("where p.lastName = :lastName ")
				.append("and p.firstName = :firstName ")
				.append("and p.birthdate = :birthdate ")
				.append("and rp.fromDate <= :asOfDate ")
				.append("and rp.toDate >= :asOfDate")
				.toString();
		Query query = getSession().createQuery(sql);
		query.setParameter("lastName", lastName);
		query.setParameter("firstName", firstName);
		query.setParameter("birthdate", birthdate);
		query.setParameter("asOfDate", asOfDate);

		RosterPlayer rosterPlayer = (RosterPlayer)query.uniqueResult();
		if (rosterPlayer == null) {
			rosterPlayer = new RosterPlayer(StatusCodeDAO.NotFound);
		}
		else {
			rosterPlayer.setStatusCode(StatusCodeDAO.Found);
		}
		return rosterPlayer;
	}

	public RosterPlayer findRosterPlayer(String lastName, String firstName, String teamKey, LocalDate asOfDate) {
		String sql = new StringBuilder()
				.append("select rp from RosterPlayer rp ")
				.append("inner join rp.player p ")
				.append("inner join rp.team t ")
				.append("where p.lastName = :lastName ")
				.append("and p.firstName = :firstName ")
				.append("and t.teamKey = :teamKey ")
				.append("and rp.fromDate <= :asOfDate ")
				.append("and rp.toDate >= :asOfDate")
				.toString();
		Query query = getSession().createQuery(sql);
		query.setParameter("lastName", lastName);
		query.setParameter("firstName", firstName);
		query.setParameter("teamKey", teamKey);
		query.setParameter("asOfDate", asOfDate);

		RosterPlayer rosterPlayer = (RosterPlayer)query.uniqueResult();
		if (rosterPlayer == null) {
			rosterPlayer = new RosterPlayer(StatusCodeDAO.NotFound);
		}
		else {
			rosterPlayer.setStatusCode(StatusCodeDAO.Found);
		}
		return rosterPlayer;
	}

	@SuppressWarnings("unchecked")
	public List<RosterPlayer> findRosterPlayers(String lastName, String firstName, LocalDate birthdate) {
		String sql = new StringBuilder()
				.append("select rp from RosterPlayer rp ")
				.append("inner join rp.player p ")
				.append("where p.lastName = :lastName ")
				.append("and p.firstName = :firstName ")
				.append("and p.birthdate = :birthdate ")
				.toString();
		Query query = getSession().createQuery(sql);
		query.setParameter("lastName", lastName);
		query.setParameter("firstName", firstName);
		query.setParameter("birthdate", birthdate);

		List<RosterPlayer> rosterPlayers = (List<RosterPlayer>)query.list();
		if (rosterPlayers == null) {
			rosterPlayers = new ArrayList<RosterPlayer>();
		}
		return rosterPlayers;
	}

	@SuppressWarnings("unchecked")
	public List<RosterPlayer> findRosterPlayers(String teamKey, LocalDate asOfDate) {
		String sql = new StringBuilder()
				.append("select rp from RosterPlayer rp ")
				.append("inner join rp.player p ")
				.append("inner join rp.team t ")
				.append("where t.teamKey = :teamKey ")
				.append("and rp.fromDate <= :asOfDate ")
				.append("and rp.toDate >= :asOfDate")
				.toString();
		Query query = getSession().createQuery(sql);
		query.setParameter("teamKey", teamKey);
		query.setParameter("asOfDate", asOfDate);

		List<RosterPlayer> rosterPlayers = (List<RosterPlayer>)query.list();
		if (rosterPlayers == null) {
			rosterPlayers = new ArrayList<RosterPlayer>();
		}
		return rosterPlayers;
	}

	public RosterPlayer createRosterPlayer(RosterPlayer createRosterPlayer) {
		RosterPlayer findRosterPlayer = findRosterPlayer(createRosterPlayer.getPlayer().getLastName(), createRosterPlayer.getPlayer().getFirstName(), createRosterPlayer.getPlayer().getBirthdate(), createRosterPlayer.getFromDate());
		if (findRosterPlayer.isNotFound()) {
			getSession().save(createRosterPlayer);
			createRosterPlayer.setStatusCode(StatusCodeDAO.Created);
			return createRosterPlayer;
		}
		else {
			return findRosterPlayer;
		}
	}

	public RosterPlayer updateRosterPlayer(RosterPlayer rp) {
		RosterPlayer rosterPlayer = findRosterPlayer(rp.getPlayer().getLastName(), rp.getPlayer().getFirstName(), rp.getPlayer().getBirthdate(), rp.getFromDate());
		if (rosterPlayer.isFound()) {
			rosterPlayer.setFromDate(rp.getFromDate());
			rosterPlayer.setToDate(rp.getToDate());
			rosterPlayer.setNumber(rp.getNumber());
			rosterPlayer.setPosition(rp.getPosition());
			rosterPlayer.setStatusCode(StatusCodeDAO.Updated);
			getSession().saveOrUpdate(rosterPlayer);
		}
		return rosterPlayer;
	}

	public RosterPlayer deleteRosterPlayer(String lastName, String firstName, LocalDate birthdate, LocalDate asOfDate) {
		RosterPlayer rosterPlayer = findRosterPlayer(lastName, firstName, birthdate, asOfDate);
		if (rosterPlayer.isFound()) {
			getSession().delete(rosterPlayer);
			rosterPlayer = new RosterPlayer(StatusCodeDAO.Deleted);
		}
		return rosterPlayer;
	}

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}
