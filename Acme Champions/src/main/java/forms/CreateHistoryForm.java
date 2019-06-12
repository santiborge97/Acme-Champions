
package forms;

import java.util.Collection;

import javax.persistence.ElementCollection;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

public class CreateHistoryForm {

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
