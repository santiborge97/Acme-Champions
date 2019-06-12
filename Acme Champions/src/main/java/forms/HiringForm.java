
package forms;

public class HiringForm {

	private Double	price;
	private String	mandatoryComment;
	private int		managerId;
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

	public int getManagerId() {
		return this.managerId;
	}

	public void setManagerId(final int managerId) {
		this.managerId = managerId;
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
