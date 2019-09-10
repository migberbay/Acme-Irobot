
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Purchase extends DomainEntity {

	// Attributes -----------------------------------------------------------

	private Date moment;
	private String holder;
	private String make;
	private String number;
	private Date expirationDate;
	private Integer CVV;


	// Getters and Setters ---------------------------------------------------

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}
	
	@NotBlank
	public String getHolder() {
		return this.holder;
	}

	public void setHolder(final String holder) {
		this.holder = holder;
	}

	@NotBlank
	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}

	@Size(min = 16, max = 16)
	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}
	
	@Future
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(final Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	@NotNull
	@Range(min = 100, max = 999)
	public Integer getCVV() {
		return this.CVV;
	}

	public void setCVV(final Integer CVV) {
		this.CVV = CVV;
	}


	// Relationships ----------------------------------------------------------

	
	private Irobot irobot;
	private Customer customer;
	
	@ManyToOne
	public Irobot getIrobot() {
		return irobot;
	}

	public void setIrobot(Irobot irobot) {
		this.irobot = irobot;
	}

	@ManyToOne
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}	

}
