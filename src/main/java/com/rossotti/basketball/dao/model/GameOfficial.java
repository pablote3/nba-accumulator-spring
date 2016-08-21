package com.rossotti.basketball.dao.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name="gameOfficial")
public class GameOfficial {
	public GameOfficial() {}

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="gameId", referencedColumnName="id", nullable=false)
	@JsonBackReference(value="gameOfficial-to-game")
	private Game game;
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}

	@ManyToOne(cascade= CascadeType.MERGE)
	@JoinColumn(name="officialId", referencedColumnName="id", nullable=false)
	@JsonBackReference(value="gameOfficial-to-official")
	private Official official;
	public Official getOfficial() {
		return official;
	}
	public void setOfficial(Official official) {
		this.official = official;
	}

	public String toString() {
		return new StringBuffer()
			.append("  id: " + this.id + "\n")
			.append("  official: " + this.official.toString() + "\n")
			.toString();
	}
}