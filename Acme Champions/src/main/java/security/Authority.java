/*
 * Authority.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package security;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;

@Embeddable
@Access(AccessType.PROPERTY)
public class Authority implements GrantedAuthority {

	// Constructors -----------------------------------------------------------

	private static final long	serialVersionUID	= 1L;


	public Authority() {
		super();
	}


	// Values -----------------------------------------------------------------

	public static final String	ADMIN		= "ADMIN";
	public static final String	FEDERATION	= "FEDERATION";
	public static final String	MANAGER		= "MANAGER";
	public static final String	PLAYER		= "PLAYER";
	public static final String	PRESIDENT	= "PRESIDENT";
	public static final String	REFEREE		= "REFEREE";
	public static final String	SPONSOR		= "SPONSOR";

	// Attributes -------------------------------------------------------------

	private String				authority;


	@NotBlank
	@Pattern(regexp = "^" + Authority.ADMIN + "|" + Authority.FEDERATION + "|" + Authority.MANAGER + "|" + Authority.PLAYER + "|" + Authority.PRESIDENT + "|" + Authority.REFEREE + "|" + Authority.SPONSOR + "$")
	@Override
	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(final String authority) {
		this.authority = authority;
	}

	public static Collection<Authority> listAuthorities() {
		Collection<Authority> result;
		Authority authority;

		result = new ArrayList<Authority>();

		authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		result.add(authority);

		authority = new Authority();
		authority.setAuthority(Authority.FEDERATION);
		result.add(authority);

		authority = new Authority();
		authority.setAuthority(Authority.MANAGER);
		result.add(authority);

		authority = new Authority();
		authority.setAuthority(Authority.PLAYER);
		result.add(authority);

		authority = new Authority();
		authority.setAuthority(Authority.PRESIDENT);
		result.add(authority);

		authority = new Authority();
		authority.setAuthority(Authority.REFEREE);
		result.add(authority);

		authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		result.add(authority);

		return result;
	}

	// Object interface -------------------------------------------------------

	@Override
	public int hashCode() {
		return this.getAuthority().hashCode();
	}

	@Override
	public boolean equals(final Object other) {
		boolean result;

		if (this == other)
			result = true;
		else if (other == null)
			result = false;
		else if (!this.getClass().isInstance(other))
			result = false;
		else
			result = (this.getAuthority().equals(((Authority) other).getAuthority()));

		return result;
	}

	@Override
	public String toString() {
		return this.authority;
	}

}
