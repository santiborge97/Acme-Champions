
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Player extends Actor {

	private String	positionEnglish;
	private String	positionSpanish;
	private Boolean	injured;
	private Boolean	punished;
	private Double	buyoutClause;
	private Integer	squadNumber;
	private String	squadName;
	private Team	team;


	@NotBlank
	@SafeHtml
	@Pattern(regexp = "\\AGOALKEEPER\\z|\\ADEFENDER\\z|\\AMIDFIELDER\\z|\\ASTRIKER\\z")
	public String getPositionEnglish() {
		return this.positionEnglish;
	}

	public void setPositionEnglish(final String positionEnglish) {
		this.positionEnglish = positionEnglish;
	}

	@NotBlank
	@SafeHtml
	@Pattern(regexp = "\\APORTERO\\z|\\ADEFENSA\\z|\\ACENTROCAMPISTA\\z|\\ADELANTERO\\z")
	public String getPositionSpanish() {
		return this.positionSpanish;
	}

	public void setPositionSpanish(final String positionSpanish) {
		this.positionSpanish = positionSpanish;
	}

	@NotNull
	public Boolean getInjured() {
		return this.injured;
	}

	public void setInjured(final Boolean injured) {
		this.injured = injured;
	}

	@NotNull
	public Boolean getPunished() {
		return this.punished;
	}

	public void setPunished(final Boolean punished) {
		this.punished = punished;
	}

	@DecimalMin(value = "0.0")
	public Double getBuyoutClause() {
		return this.buyoutClause;
	}

	public void setBuyoutClause(final Double buyoutClause) {
		this.buyoutClause = buyoutClause;
	}

	@NotNull
	@Range(min = 1, max = 99)
	public Integer getSquadNumber() {
		return this.squadNumber;
	}

	public void setSquadNumber(final Integer squadNumber) {
		this.squadNumber = squadNumber;
	}

	@SafeHtml
	@NotBlank
	public String getSquadName() {
		return this.squadName;
	}

	public void setSquadName(final String squadName) {
		this.squadName = squadName;
	}

	@Valid
	@ManyToOne(optional = true)
	public Team getTeam() {
		return this.team;
	}

	public void setTeam(final Team team) {
		this.team = team;
	}

}
