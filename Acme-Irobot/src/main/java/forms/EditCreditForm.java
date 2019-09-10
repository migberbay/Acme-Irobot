package forms;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import security.Authority;


public class EditCreditForm {
	private String holder;
	private String make;
	private String number;
	private Integer expirationMonth;
	private Integer expirationYear;
	private Integer CVV;
	
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
