
package forms;

import domain.CreditCard;

public class SponsorshipForm {

	public SponsorshipForm() {
		super();
	}


	private int			id;
	private int			version;
	private String		banner;
	private String		target;
	private CreditCard	creditCard;
	private int			positionId;


	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(final String target) {
		this.target = target;
	}

	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public int getPositionId() {
		return this.positionId;
	}

	public void setPositionId(final int positionId) {
		this.positionId = positionId;
	}

}
