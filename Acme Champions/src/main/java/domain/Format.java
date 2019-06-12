
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Format extends DomainEntity {

	private String	type;
	private Integer	maximumTeams;
	private Integer	minimumTeams;
	
	private Federation federation;


	@SafeHtml
	@NotBlank
	@Pattern(regexp = "\\ATOURNAMENT\\z|\\ALEAGUE\\z")
	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	@Min(2)
	@NotNull
	public Integer getMaximumTeams() {
		return this.maximumTeams;
	}

	public void setMaximumTeams(final Integer maximumTeams) {
		this.maximumTeams = maximumTeams;
	}

	@Min(2)
	@NotNull
	public Integer getMinimumTeams() {
		return this.minimumTeams;
	}

	public void setMinimumTeams(final Integer minimumTeams) {
		this.minimumTeams = minimumTeams;
	}
	
	@Valid
	@ManyToOne(optional = false)
	public Federation getFederation() {
		return this.federation;
	}
	
	public void setFederation(Federation federation) {
		this.federation = federation;
	}

}
