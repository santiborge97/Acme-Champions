
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	//Atributos-----------------------------------------------------------------------
	private String				keyWord;
	private String				position;
	private Date				lastUpdate;

	private President			president;
	private Collection<Player>	players;
	private Collection<Manager>	managers;


	@SafeHtml
	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	@SafeHtml
	public String getPosition() {
		return this.position;
	}

	public void setPosition(final String position) {
		this.position = position;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@ManyToMany
	@Valid
	public Collection<Player> getPlayers() {
		return this.players;
	}

	public void setPlayers(final Collection<Player> players) {
		this.players = players;
	}

	@OneToOne(optional = false)
	@Valid
	public President getPresident() {
		return this.president;
	}

	public void setPresident(final President president) {
		this.president = president;
	}

	@ManyToMany
	@Valid
	public Collection<Manager> getManagers() {
		return this.managers;
	}

	public void setManagers(final Collection<Manager> managers) {
		this.managers = managers;
	}

}
