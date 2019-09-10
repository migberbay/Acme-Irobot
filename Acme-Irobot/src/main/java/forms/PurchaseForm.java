package forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import domain.Irobot;


public class PurchaseForm {
	private Boolean useProfileCard;
	private Irobot irobot;
	
	private String holder;
	private String make;
	private String number;
	private Integer expirationMonth;
	private Integer expirationYear;
	private Integer CVV;
	
	@NotNull
	public Irobot getIrobot() {
		return irobot;
	}

	public void setIrobot(Irobot irobot) {
		this.irobot = irobot;
	}

	public Boolean getUseProfileCard() {
		return useProfileCard;
	}

	public void setUseProfileCard(Boolean useProfileCard) {
		this.useProfileCard = useProfileCard;
	}

	@NotBlank
	public String getHolder() {
		return holder;
	}
	
	public void setHolder(String holder) {
		this.holder = holder;
	}
	
	@NotBlank
	@Pattern(regexp = "^VISA|MASTER CARD|AMERICAN EXPRESS|DINERS CLUB$")
	public String getMake() {
		return make;
	}
	
	public void setMake(String make) {
		this.make = make;
	}
	@NotBlank
	@Size(min = 16, max = 16)
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	@NotNull
	public Integer getExpirationMonth() {
		return expirationMonth;
	}
	
	public void setExpirationMonth(Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	
	@NotNull
	public Integer getExpirationYear() {
		return expirationYear;
	}
	
	public void setExpirationYear(Integer expirationYear) {
		this.expirationYear = expirationYear;
	}
	
	@NotNull
	@Range(min = 100, max = 999)
	public Integer getCVV() {
		return CVV;
	}
	public void setCVV(Integer cVV) {
		CVV = cVV;
	}
	
	
}
