
package forms;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

public class RegisterPlayerForm {

	private String	name;
	private String	surnames;
	private String	photo;
	private String	email;
	private String	phone;
	private String	address;

	private String	position;
	private Integer	squadNumber;
	private String	squadName;

	private String	username;
	private String	password;
	private String	confirmPassword;

	private Boolean	checkbox;


	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml
	public String getSurnames() {
		return this.surnames;
	}

	public void setSurnames(final String surnames) {
		this.surnames = surnames;
	}

	@URL
	@SafeHtml
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@SafeHtml
	@NotBlank
	@Pattern(regexp = "^[\\w]+@((?:[a-zA-Z0-9-]+\\.)+[a-zA-Z0-9]+){0,1}|(([\\w]\\s)*[\\w])+<\\w+@((?:[a-zA-Z0-9-]+\\.)+[a-zA-Z0-9]+){0,1}>")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@SafeHtml
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@SafeHtml
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@NotBlank
	@SafeHtml
	@Pattern(regexp = "\\AGOALKEEPER\\z|\\ADEFENDER\\z|\\AMIDFIELDER\\z|\\ASTRIKER\\z|\\APORTERO\\z|\\ADEFENSA\\z|\\ACENTROCAMPISTA\\z|\\ADELANTERO\\z")
	public String getPosition() {
		return this.position;
	}

	public void setPosition(final String position) {
		this.position = position;
	}

	@NotNull
	@Range(min = 1, max = 99)
	public Integer getSquadNumber() {
		return this.squadNumber;
	}

	public void setSquadNumber(final Integer squadNumber) {
		this.squadNumber = squadNumber;
	}

	@SafeHtml
	@NotBlank
	public String getSquadName() {
		return this.squadName;
	}

	public void setSquadName(final String squadName) {
		this.squadName = squadName;
	}

	@Size(min = 5, max = 32)
	@Column(unique = true)
	@SafeHtml
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	@SafeHtml
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@SafeHtml
	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@NotNull
	public Boolean getCheckbox() {
		return this.checkbox;
	}

	public void setCheckbox(final Boolean checkbox) {
		this.checkbox = checkbox;
	}

	//Business metohds--------------------------------------------
	public Boolean checkPassword() {
		Boolean res;

		if (this.password.equals(this.confirmPassword))
			res = true;
		else
			res = false;

		return res;
	}
}
