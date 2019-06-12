
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class StatisticalData extends DomainEntity {

	private Integer	yellowCards;
	private Integer	redCards;
	private Integer	goals;
	private Integer	matchsPlayed;
	private Integer	accumulatedYellowCard;
	private Player	player;


	@NotNull
	@Min(0)
	public Integer getYellowCards() {
		return this.yellowCards;
	}

	public void setYellowCards(final Integer yellowCards) {
		this.yellowCards = yellowCards;
	}

	@NotNull
	@Min(0)
	public Integer getRedCards() {
		return this.redCards;
	}

	public void setRedCards(final Integer redCards) {
		this.redCards = redCards;
	}

	@NotNull
	@Min(0)
	public Integer getGoals() {
		return this.goals;
	}

	public void setGoals(final Integer goals) {
		this.goals = goals;
	}

	@NotNull
	@Min(0)
	public Integer getMatchsPlayed() {
		return this.matchsPlayed;
	}

	public void setMatchsPlayed(final Integer matchsPlayed) {
		this.matchsPlayed = matchsPlayed;
	}

	@NotNull
	@Min(0)
	@Max(4)
	public Integer getAccumulatedYellowCard() {
		return this.accumulatedYellowCard;
	}

	public void setAccumulatedYellowCard(final Integer accumulatedYellowCard) {
		this.accumulatedYellowCard = accumulatedYellowCard;
	}

	@OneToOne(optional = false)
	@Valid
	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(final Player player) {
		this.player = player;
	}

}
