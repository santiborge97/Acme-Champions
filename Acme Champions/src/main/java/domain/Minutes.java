
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Minutes extends DomainEntity {

	private Integer				homeScore;
	private Integer				visitorScore;

	private Collection<Player>	playersScore;
	private Collection<Player>	playersYellow;
	private Collection<Player>	playersRed;
	private Game				game;
	private Team				winner;
	private Boolean				closed;


	@NotNull
	public Boolean getClosed() {
		return this.closed;
	}

	public void setClosed(final Boolean closed) {
		this.closed = closed;
	}

	@NotNull
	@Min(0)
	public Integer getHomeScore() {
		return this.homeScore;
	}

	public void setHomeScore(final Integer homeScore) {
		this.homeScore = homeScore;
	}

	@NotNull
	@Min(0)
	public Integer getVisitorScore() {
		return this.visitorScore;
	}

	public void setVisitorScore(final Integer visitorScore) {
		this.visitorScore = visitorScore;
	}

	@Valid
	@ManyToMany
	public Collection<Player> getPlayersScore() {
		return this.playersScore;
	}

	public void setPlayersScore(final Collection<Player> playersScore) {
		this.playersScore = playersScore;
	}

	@Valid
	@ManyToMany
	public Collection<Player> getPlayersYellow() {
		return this.playersYellow;
	}

	public void setPlayersYellow(final Collection<Player> playersYellow) {
		this.playersYellow = playersYellow;
	}

	@Valid
	@ManyToMany
	public Collection<Player> getPlayersRed() {
		return this.playersRed;
	}

	public void setPlayersRed(final Collection<Player> playersRed) {
		this.playersRed = playersRed;
	}

	@Valid
	@OneToOne(optional = false)
	public Game getGame() {
		return this.game;
	}

	public void setGame(final Game game) {
		this.game = game;
	}

	@Valid
	@ManyToOne(optional = true)
	public Team getWinner() {
		return this.winner;
	}

	public void setWinner(final Team winner) {
		this.winner = winner;
	}

}
