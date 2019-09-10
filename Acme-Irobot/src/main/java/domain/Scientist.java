package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Scientist extends Actor {
	
	public String VATNumber;
	
	@NotBlank
	@Pattern(regexp="^([E]{1}[S]{1})([A-Z]{1})([0-9]{8})$")
	public String getVATNumber() {
		return VATNumber;
	}

	public void setVATNumber(String VATNumber) {
		this.VATNumber = VATNumber;
	}
	
	//Relationships
	private CreditCard creditCard;
	private Collection<Irobot> irobots;
	
	@OneToOne (optional = true)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@OneToMany (mappedBy = "scientist")
	public Collection<Irobot> getIrobots() {
		return irobots;
	}

	public void setIrobots(Collection<Irobot> irobots) {
		this.irobots = irobots;
	}
	
	

}
