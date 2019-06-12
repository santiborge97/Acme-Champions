
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class PlayerRecord extends DomainEntity {

	private Date	startDate;
	private Date	endDate;
	private Double	salary;
	private Integer	squadNumber;


	@Past
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@Past
	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@NotNull
	@DecimalMin(value = "0.0")
	public Double getSalary() {
		return this.salary;
	}

	public void setSalary(final Double salary) {
		this.salary = salary;
	}

	@NotNull
	@Range(min = 1, max = 99)
	public Integer getSquadNumber() {
		return this.squadNumber;
	}

	public void setSquadNumber(final Integer squadNumber) {
		this.squadNumber = squadNumber;
	}

}
