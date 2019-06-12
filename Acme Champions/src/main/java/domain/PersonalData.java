
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class PersonalData extends DomainEntity {

	private Collection<String>	photos;
	private String				socialNetworkProfilelink;


	@ElementCollection
	public Collection<String> getPhotos() {
		return this.photos;
	}

	public void setPhotos(final Collection<String> photos) {
		this.photos = photos;
	}

	@URL
	@SafeHtml
	@NotBlank
	public String getSocialNetworkProfilelink() {
		return this.socialNetworkProfilelink;
	}

	public void setSocialNetworkProfilelink(final String socialNetworkProfilelink) {
		this.socialNetworkProfilelink = socialNetworkProfilelink;
	}

}
