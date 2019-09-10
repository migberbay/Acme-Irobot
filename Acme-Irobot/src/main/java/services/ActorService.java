package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Actor;
import domain.Admin;
import domain.Comment;
import domain.Customer;
import domain.Scientist;
import forms.EditPersonalForm;
import forms.RegisterForm;


@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository actorRepository;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ScientistService scientistService ;
	
	@Autowired
	private CustomerService customerService ;
	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private ConfigurationService configurationService;
	
	
	@Autowired
	private Validator validator;

	// Simple CRUD methods -----
	public Actor create (UserAccount ua){
		Actor res = new Actor();
		res.setUserAccount(ua);
		
		return res;
	}
	
	public Collection<Actor> findAll() {
		return actorRepository.findAll();
	}

	public Actor findOne(int Id) {
		return actorRepository.findOne(Id);
	}

	public Actor save(Actor actor) {

		Actor result;

		result = actorRepository.saveAndFlush(actor);
		return result;
	}

	public void delete(Actor actor) {
		
		actorRepository.delete(actor);
	}

	// Other business methods -----

	public Actor getPrincipal() {
		return actorRepository.getByUserAccount(LoginService.getPrincipal());
	}

	public Customer registerCustomer(Actor actor, RegisterForm form) {
		
		Customer res = customerService.create(actor.getUserAccount());
		UserAccount savedua =  userAccountService.save(actor.getUserAccount());
		
		System.out.println("la contraseña de la useraccount persistida es :" + savedua.getPassword());
		
		res.setUserAccount(savedua);
		
		res.setEmail(actor.getEmail());
		res.setName(actor.getName());
		res.setPhone(actor.getPhone());
		res.setPhoto(actor.getPhoto());
		res.setSurnames(actor.getSurnames());
		res.setVATNumber(form.getVATNumber());

		Customer saved = customerService.save(res);
				
		return saved;
	}
	
	public Scientist registerScientist(Actor actor, RegisterForm form) {

		Scientist res = scientistService.create(actor.getUserAccount());
		
		UserAccount savedua =  userAccountService.save(actor.getUserAccount());
		
		System.out.println("la contraseña de la useraccount persistida es :" + savedua.getPassword());
		
		res.setUserAccount(savedua);
		
		res.setEmail(actor.getEmail());
		res.setName(actor.getName());
		res.setPhone(actor.getPhone());
		res.setPhoto(actor.getPhoto());
		res.setSurnames(actor.getSurnames());
		res.setVATNumber(form.getVATNumber());
		
		Scientist saved = scientistService.save(res);
		
		return saved;
	}
	
	public Admin registerAdmin(Actor actor) {
		
		Admin res = adminService.create(actor.getUserAccount());
		
		Authority adminauth = new Authority();
		adminauth.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(adminauth));

		UserAccount savedua =  userAccountService.save(actor.getUserAccount());
		
		System.out.println("la contraseña de la useraccount persistida es :" + savedua.getPassword());
		
		res.setUserAccount(savedua);
		
		res.setEmail(actor.getEmail());
		res.setName(actor.getName());
		res.setPhone(actor.getPhone());
		res.setPhoto(actor.getPhoto());
		res.setSurnames(actor.getSurnames());
		
		Admin saved = adminService.save(res);
		
		return saved;
	}

	public Actor reconstruct(RegisterForm form, BindingResult binding){
		
		//Creamos la cuenta de usuario.
		
		UserAccount ua = userAccountService.create();
		
		ua.setPassword(form.getPassword());
		ua.setUsername(form.getUsername());
		
		// Le asignamos la authority cosrrespondiente.
		
		Authority authority = new Authority();
		authority.setAuthority(form.getType());
		ua.addAuthority(authority);
		
		// Creamos el telefono
		String areaCode;
		String countryCode;
		if(form.getAreaCode() != null){areaCode = form.getAreaCode().toString();}else{areaCode = "0";}
		if(form.getCountryCode() != null){countryCode = form.getCountryCode().toString();}else{countryCode = "0";}
		String defaultCountryCode = configurationService.find().getDefaultPhoneCode().toString();
		String phoneNumber = "";
		
		if(areaCode != "0" && countryCode != "0"){
			phoneNumber = "+" + countryCode + "(" + areaCode + ")" + form.getPhoneNumber(); 
		}else if(countryCode != "0"){
			phoneNumber = "+" +countryCode + form.getPhoneNumber();
		}else if (countryCode == "0" && areaCode == "0"){
			phoneNumber = "+" + defaultCountryCode + form.getPhoneNumber();
		}else{
			phoneNumber = "+"+defaultCountryCode + "(" + areaCode + ")" + form.getPhoneNumber();
		}
		
		
		// Creamos el actor con la useraccount sin persistir.
		
		Actor actor = this.create(ua);
		
		actor.setEmail(form.getEmail());
		actor.setName(form.getName());
		actor.setPhoto(form.getPhoto());
		actor.setSurnames(form.getSurnames());
		actor.setPhone(phoneNumber);
		
		validator.validate(form, binding);		
		
		if (form.getPassword().equals(form.getPassword2())==false) {
			ObjectError error = new ObjectError(form.getPassword(), "password does not match");
			binding.addError(error);
		}		
		return actor;
	}
	
	public Actor findByComment(Comment comment){
		Actor res = null;
		
		for (Actor a : this.findAll()) {
			if(a.getComments().contains(comment)){
				res = a;
				break;
			}
		}
		return res;
	}
	
	public String actorToJson(Actor actor){
		
		EditPersonalForm form = new EditPersonalForm();
		
		form.setEmail(actor.getEmail());
		form.setName(actor.getName());
		form.setPhoto(actor.getPhoto());
		form.setSurnames(actor.getSurnames());
		form.setPhoneNumber(actor.getPhone());
		
		String res = "";
		ObjectMapper mapper = new ObjectMapper();
		
        try {
            String json = mapper.writeValueAsString(form);
            res = json;
            System.out.println("JSON = " + json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
		return res;
	}
	
}