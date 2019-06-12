
package forms;

public class SigningForm {

	private Double	price;
	private String	mandatoryComment;
	private Double	offeredClause;
	private int		playerId;
	private int		id;
	private int		version;


	public Double getPrice() {
		return this.price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	public String getMandatoryComment() {
		return this.mandatoryComment;
	}

	public void setMandatoryComment(final String mandatoryComment) {
		this.mandatoryComment = mandatoryComment;
	}

	public Double getOfferedClause() {
		return this.offeredClause;
	}

	public void setOfferedClause(final Double offeredClause) {
		this.offeredClause = offeredClause;
	}

	public int getPlayerId() {
		return this.playerId;
	}

	public void setPlayerId(final int playerId) {
		this.playerId = playerId;
	}

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

}
