package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String banner;
	private Integer defaultPhoneCode;
	
	private String systemName;
	private String welcomeTextEnglish;
	private String welcomeTextSpanish;
	
	// Getters and Setters ---------------------------------------------------
	
	@URL
	@NotBlank
	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	@Min(1)
	@Max(999)
	@NotNull
	public Integer getDefaultPhoneCode() {
		return defaultPhoneCode;
	}

	public void setDefaultPhoneCode(Integer defaultPhoneCode) {
		this.defaultPhoneCode = defaultPhoneCode;
	}

	@NotBlank
	public String getSystemName() {
		return systemName;
	}

	
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	@NotBlank
	public String getWelcomeTextEnglish() {
		return welcomeTextEnglish;
	}

	public void setWelcomeTextEnglish(String welcomeTextEnglish) {
		this.welcomeTextEnglish = welcomeTextEnglish;
	}

	@NotBlank
	public String getWelcomeTextSpanish() {
		return welcomeTextSpanish;
	}

	public void setWelcomeTextSpanish(String welcomeTextSpanish) {
		this.welcomeTextSpanish = welcomeTextSpanish;
	}
	
	
	//Relationships--------------------------------------------------------------------
	
	

}
