
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class SportRecord extends DomainEntity {

	private String	sportName;
	private Date	startDate;
	private Date	endDate;
	private Boolean	teamSport;


	@SafeHtml
	@NotBlank
	public String getSportName() {
		return this.sportName;
	}

	public void setSportName(final String sportName) {
		this.sportName = sportName;
	}

	@Past
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@Past
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@NotNull
	public Boolean getTeamSport() {
		return this.teamSport;
	}

	public void setTeamSport(final Boolean teamSport) {
		this.teamSport = teamSport;
	}

}
