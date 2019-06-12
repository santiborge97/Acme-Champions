
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Competition extends DomainEntity {

	private Date				startDate;
	private Date				endDate;
	private String				nameTrophy;
	private Boolean				closed;

	private Federation			federation;
	private Collection<Team>	teams;
	private Collection<Game>	games;
	private Format				format;


	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@SafeHtml
	@NotBlank
	public String getNameTrophy() {
		return this.nameTrophy;
	}

	public void setNameTrophy(final String nameTrophy) {
		this.nameTrophy = nameTrophy;
	}

	@NotNull
	public Boolean getClosed() {
		return this.closed;
	}

	public void setClosed(final Boolean closed) {
		this.closed = closed;
	}

	@Valid
	@ManyToOne(optional = false)
	public Federation getFederation() {
		return this.federation;
	}

	public void setFederation(final Federation federation) {
		this.federation = federation;
	}

	@Valid
	@ManyToMany
	public Collection<Team> getTeams() {
		return this.teams;
	}

	public void setTeams(final Collection<Team> teams) {
		this.teams = teams;
	}

	@Valid
	@ManyToMany
	public Collection<Game> getGames() {
		return this.games;
	}

	public void setGames(final Collection<Game> games) {
		this.games = games;
	}

	@Valid
	@ManyToOne(optional = false)
	public Format getFormat() {
		return this.format;
	}

	public void setFormat(final Format format) {
		this.format = format;
	}

}
