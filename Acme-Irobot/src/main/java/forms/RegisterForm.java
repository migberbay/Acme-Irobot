package forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

public class RegisterForm {
	private String type;
	
	private String	username;
	private String	password;
	private String	password2;
	
	private String	name;
	private String	surnames;
	private String	photo;
	private String	email;
	
	private Integer  countryCode;
	private Integer  areaCode;
	private String	phoneNumber;
	
	private String VATNumber;
	
	@NotNull
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@NotBlank
	@Pattern(regexp="^([E]{1}[S]{1})([A-Z]{1})([0-9]{8})$")
	public String getVATNumber() {
		return VATNumber;
	}
	
	public void setVATNumber(String VATNumber){
		this.VATNumber = VATNumber;
	}
	
	
	@NotBlank
	public String getUsername() {
		return this.username;
	}


	public void setUsername(final String username) {
		this.username = username;
	}
	
	@NotBlank
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
	
	@NotBlank
	public String getPassword2() {
		return this.password2;
	}

	public void setPassword2(final String password2) {
		this.password2 = password2;
	}
	
	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	
	@NotEmpty
	public String getSurnames() {
		return this.surnames;
	}

	public void setSurnames(final String surnames) {
		this.surnames = surnames;
	}

	@URL
	@NotBlank
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}
	
	@Email
	@NotBlank
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	
	//Phone Number
	@Min(1)
	@Max(999)
	public Integer getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(Integer countryCode) {
		this.countryCode = countryCode;
	}
	
	@Min(1)
	@Max(999)
	public Integer getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(Integer areaCode) {
		this.areaCode = areaCode;
	}
	
	@Size(min = 4)
	@NotBlank
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	
}
