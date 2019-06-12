
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class History extends DomainEntity {

	private Player						player;
	private PersonalData				personalData;
	private Collection<PlayerRecord>	playerRecords;
	private Collection<SportRecord>		sportRecords;


	@Valid
	@OneToOne(optional = false)
	public Player getPlayer() {
		return this.player;
	}

	public void setPlayer(final Player player) {
		this.player = player;
	}

	@Valid
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	public PersonalData getPersonalData() {
		return this.personalData;
	}

	public void setPersonalData(final PersonalData personalData) {
		this.personalData = personalData;
	}

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<PlayerRecord> getPlayerRecords() {
		return this.playerRecords;
	}

	public void setPlayerRecords(final Collection<PlayerRecord> playerRecords) {
		this.playerRecords = playerRecords;
	}

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<SportRecord> getSportRecords() {
		return this.sportRecords;
	}

	public void setSportRecords(final Collection<SportRecord> sportRecords) {
		this.sportRecords = sportRecords;
	}

}
