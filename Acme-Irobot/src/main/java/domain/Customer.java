package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {
	
	private String VATNumber;
	
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
	private Collection<Purchase> purchases;
	
	@OneToOne (optional = true)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@OneToMany (mappedBy = "customer")
	public Collection<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(Collection<Purchase> purchases) {
		this.purchases = purchases;
	}

	
}
