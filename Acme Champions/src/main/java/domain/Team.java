
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Team extends DomainEntity {

	private String		name;
	private String		address;
	private String		stadiumName;
	private String		badgeUrl;
	private Integer		trackRecord;
	private Date		establishmentDate;
	private Boolean		functional;

	private President	president;


	@SafeHtml
	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@SafeHtml
	@NotBlank
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@SafeHtml
	@NotBlank
	public String getStadiumName() {
		return this.stadiumName;
	}

	public void setStadiumName(final String stadiumName) {
		this.stadiumName = stadiumName;
	}

	@SafeHtml
	@URL
	@NotBlank
	public String getBadgeUrl() {
		return this.badgeUrl;
	}

	public void setBadgeUrl(final String badgeUrl) {
		this.badgeUrl = badgeUrl;
	}

	@Min(0)
	@NotNull
	public Integer getTrackRecord() {
		return this.trackRecord;
	}

	public void setTrackRecord(final Integer trackRecord) {
		this.trackRecord = trackRecord;
	}

	@NotNull
	public Boolean getFunctional() {
		return this.functional;
	}

	public void setFunctional(final Boolean functional) {
		this.functional = functional;
	}

	@Past
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getEstablishmentDate() {
		return this.establishmentDate;
	}

	public void setEstablishmentDate(final Date establishmentDate) {
		this.establishmentDate = establishmentDate;
	}

	@Valid
	@OneToOne(optional = false)
	public President getPresident() {
		return this.president;
	}

	public void setPresident(final President president) {
		this.president = president;
	}

}
