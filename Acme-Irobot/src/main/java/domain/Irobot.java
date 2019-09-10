
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Irobot extends DomainEntity {
	
	// Attributes -----------------------------------------------------------

	private String description;
	private String title;
	private String ticker;
	private Integer price;
	private Boolean isDecomissioned;


	// Getters and Setters ---------------------------------------------------
	@NotBlank
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	@Pattern(regexp="^([A-Z]{4}[-][0-9]{4})$")
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Boolean getIsDecomissioned() {
		return isDecomissioned;
	}

	public void setIsDecomissioned(Boolean isDecomissioned) {
		this.isDecomissioned = isDecomissioned;
	}

	// Relationships ----------------------------------------------------------



	private Collection<Purchase> purchases;
	private Scientist scientist;

	@OneToMany (mappedBy = "irobot")
	public Collection<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(Collection<Purchase> purchases) {
		this.purchases = purchases;
	}

	@ManyToOne
	public Scientist getScientist() {
		return scientist;
	}

	public void setScientist(Scientist scientist) {
		this.scientist = scientist;
	}
	

	

}
