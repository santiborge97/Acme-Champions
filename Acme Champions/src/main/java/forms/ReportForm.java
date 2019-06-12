
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class ReportForm {

	private String	description;

	private int		playerId;


	@SafeHtml
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	public int getPlayerId() {
		return this.playerId;
	}

	public void setPlayerId(final int playerId) {
		this.playerId = playerId;
	}

}
