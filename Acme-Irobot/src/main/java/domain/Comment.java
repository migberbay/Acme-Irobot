package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity{

	// Attributes -------------------------------------------------------------

	private String body;
	private Date moment;
	
	// Getters and Setters ---------------------------------------------------
	
	@NotBlank
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	//Relationships -----------------------------
	Irobot irobot;

	@ManyToOne(optional = false, cascade=CascadeType.ALL)
	public Irobot getIrobot() {
		return irobot;
	}

	public void setIrobot(Irobot irobot) {
		this.irobot = irobot;
	}
	
	
	
	
}
