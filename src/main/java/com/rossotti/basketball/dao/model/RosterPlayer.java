package com.rossotti.basketball.dao.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="rosterPlayer")
public class RosterPlayer {
	public RosterPlayer() {
		setStatusCode(StatusCodeDAO.Found);
	}
	public RosterPlayer(StatusCodeDAO statusCode) {
		setStatusCode(statusCode);
	}

	@Enumerated(EnumType.STRING)
	@Transient
	private StatusCodeDAO statusCode;
	public void setStatusCode(StatusCodeDAO statusCode) {
		this.statusCode = statusCode;
	}
	public Boolean isFound() {
		return statusCode == StatusCodeDAO.Found;
	}
	public Boolean isNotFound() {
		return statusCode == StatusCodeDAO.NotFound;
	}
	public Boolean isUpdated() {
		return statusCode == StatusCodeDAO.Updated;
	}
	public Boolean isCreated() {
		return statusCode == StatusCodeDAO.Created;
	}
	public Boolean isDeleted() {
		return statusCode == StatusCodeDAO.Deleted;
	}

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(cascade= CascadeType.MERGE)
	@JoinColumn(name="teamId", referencedColumnName="id", nullable=false)
	@JsonBackReference(value="rosterPlayer-to-team")
	private Team team;
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}

	@ManyToOne(cascade= CascadeType.MERGE)
	@JoinColumn(name="playerId", referencedColumnName="id", nullable=false)
	@JsonBackReference(value="rosterPlayer-to-player")
	private Player player;
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}

	@OneToMany(mappedBy="rosterPlayer", fetch = FetchType.LAZY, cascade= CascadeType.ALL)
	private List<BoxScorePlayer> boxScorePlayers = new ArrayList<BoxScorePlayer>();
	public List<BoxScorePlayer> getBoxScorePlayers()  {
		return boxScorePlayers;
	}
	@JsonManagedReference(value="boxScorePlayer-to-rosterPlayer")
	public void setBoxScorePlayers(List<BoxScorePlayer> boxScorePlayers)  {
		this.boxScorePlayers = boxScorePlayers;
	}
	public void addBoxScorePlayer(BoxScorePlayer boxScorePlayer)  {
		this.getBoxScorePlayers().add(boxScorePlayer);
	}
	public void removeBoxScorePlayer(BoxScorePlayer boxScorePlayer)  {
		this.getBoxScorePlayers().remove(boxScorePlayer);
	}

	@Column(name="fromDate", nullable=false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate fromDate;
	public LocalDate getFromDate()  {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	@Column(name="toDate", nullable=false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate toDate;
	public LocalDate getToDate()  {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	@Column(name="number", length=3, nullable=false)
	private String number;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="position", length=5, nullable=false)
	private Position position;
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}

	public String toString() {
		return ("\r" + "  id: " + this.id + "\n") +
				"  lastName: " + player.getLastName() + "\n" +
				"  firstName: " + player.getFirstName() + "\n" +
				"  birthDate: " + player.getBirthdate() + "\n" +
				"  teamKey: " + team.getTeamKey() + "\n" +
				"  fromDate: " + this.getFromDate() + "\n" +
				"  toDate: " + this.getToDate() + "\n" +
				"  statusCode: " + this.statusCode;
	}
}