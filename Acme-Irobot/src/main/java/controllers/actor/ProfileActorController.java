package controllers.actor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.AdminService;
import services.ConfigurationService;
import services.CreditCardService;
import services.CustomerService;
import services.ScientistService;
import controllers.AbstractController;
import domain.Actor;
import domain.Admin;
import domain.CreditCard;
import domain.Customer;
import domain.Scientist;
import forms.EditCreditForm;
import forms.EditPersonalForm;

@Controller
@RequestMapping("actor/")
public class ProfileActorController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ScientistService scientistService;
	
	@Autowired
	private CreditCardService cardService;
	
	@Autowired
	private Validator validator;
	

	// Show --------------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(required = false) Integer actorId) {

		ModelAndView result;

		// Diferentes autoridades:
		Authority adminAuth = new Authority();
		adminAuth.setAuthority("ADMIN");

		Authority customerAuth = new Authority();
		customerAuth.setAuthority("CUSTOMER");

		Authority scientistAuth = new Authority();
		scientistAuth.setAuthority("SCIENTIST");

		Actor principal;
		
		try {
			principal = actorService.getPrincipal();
		} catch (Exception e) {
			principal = null;
		}

		result = new ModelAndView("actor/show");

		if (actorId != null) { //accedemos al perfil de otro actor
			Actor actor = actorService.findOne(actorId);
				
			result.addObject("actor", actor); // actor que se va a mostrar
			result.addObject("logged", false); // flag para permitir editar
			
			
		} else {//accedemos a nuestro perfil
			Actor actor = actorService.getPrincipal();
			
			result.addObject("logged", true);
			result.addObject("actor", actor);
			
			if (actor.getUserAccount().getAuthorities().contains(adminAuth)) {
				Admin admin = adminService.findOne(actor.getId());
				result.addObject("actor", admin);
				result.addObject("actorIsAdmin",true);
			}

			if (actor.getUserAccount().getAuthorities().contains(customerAuth)) {
				Customer customer = customerService.findOne(actor.getId());
				result.addObject("actor", customer);
				result.addObject("actorIsCustomer",true);
			}

			if (actor.getUserAccount().getAuthorities().contains(scientistAuth)) {
				Scientist scientist = scientistService.findOne(actor.getId());
				result.addObject("actor", scientist);
				result.addObject("actorIsScientist",true);
			}			
		}
		
		result.addObject("requestURI", "actor/show.do");

		return result;
	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/editPersonal", method = RequestMethod.GET)
	public ModelAndView editPersonal() {
		ModelAndView result = new ModelAndView();
		EditPersonalForm f = new EditPersonalForm();
		Actor a = actorService.getPrincipal();
		
		f.setEmail(a.getEmail());
		f.setName(a.getName());
		f.setPhoto(a.getPhoto());
		f.setSurnames(a.getSurnames());
		

		result = createEditModelAndView(f,"personal");

		return result;
	}
	
	@RequestMapping(value = "/editCreditCard", method = RequestMethod.GET)
	public ModelAndView editCredit() {

		ModelAndView result = new ModelAndView();
		EditCreditForm f = new EditCreditForm();
		Customer c = null;
		Scientist s = null;
		Actor a = actorService.getPrincipal();
		
		Authority adminAuth = new Authority();
		adminAuth.setAuthority(Authority.ADMIN);
		
		Authority customerAuth = new Authority();
		customerAuth.setAuthority(Authority.CUSTOMER);
		
		Authority scientistAuth = new Authority();
		scientistAuth.setAuthority(Authority.SCIENTIST);
		
		if(a.getUserAccount().getAuthorities().contains(customerAuth)){
			c = (Customer) a;
		}
		
		if(a.getUserAccount().getAuthorities().contains(scientistAuth)){
			s = (Scientist) a;
		}
		
		if (a.getUserAccount().getAuthorities().contains(adminAuth)) {
			result = new ModelAndView("error/access");
		}else{
			if(s != null){
				if(s.getCreditCard() != null){
					f.setCVV(s.getCreditCard().getCVV());
					f.setHolder(s.getCreditCard().getHolder());
					f.setMake(s.getCreditCard().getMake());
					f.setNumber(s.getCreditCard().getNumber());
				}
			}
			if(c != null){
				if(c.getCreditCard() != null){
					f.setCVV(c.getCreditCard().getCVV());
					f.setHolder(c.getCreditCard().getHolder());
					f.setMake(c.getCreditCard().getMake());
					f.setNumber(c.getCreditCard().getNumber());
				}
			}
			result = createEditModelAndView(f,"credit");
		}
		
		return result;
	}

	// Save -----------------------------------------------------------------
	// Personal Data
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "savePersonal")
	public ModelAndView savePersonal(@ModelAttribute("form") EditPersonalForm form, final BindingResult binding) {
		ModelAndView res;
		Actor a = actorService.getPrincipal();
		
		validator.validate(form, binding);
		
		if (binding.hasErrors()) {
			System.out.println(binding);
			res = new ModelAndView("actor/edit");
			res.addObject("form", form);
			
			Date d = new Date();
			Collection <Integer> months = new ArrayList<>();
			Collection <Integer> years = new ArrayList<>();
			for (int i = 0; i < 11; i++) {
				months.add(i+1);
				years.add(d.getYear()+i+1900);
			}
			
			res.addObject("showPersonal", true);
			res.addObject("months",months);
			res.addObject("years",years);
			
			return res;
		} else {
			try {
				
			a.setEmail(form.getEmail());
			a.setName(form.getName());
			a.setPhoto(form.getPhoto());
			a.setSurnames(form.getSurnames());
			
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
			
			a.setPhone(phoneNumber);
			
			actorService.save(a);
		
		res = new ModelAndView("redirect:show.do");
		
		return res;
		
	} catch (final Throwable oops) {
		oops.printStackTrace();
		res = this.editPersonal();
		return res;
		}
	}
}
	
	// CreditCard
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveCredit")
	public ModelAndView saveCreditCard(@ModelAttribute("form") EditCreditForm form, final BindingResult binding) {
		ModelAndView res;
		
		Boolean expired = false;
		Calendar cal = Calendar.getInstance();
		
		if(form.getExpirationYear()== cal.get(Calendar.YEAR) && form.getExpirationMonth()-1<=cal.get(Calendar.MONTH)){
			expired = true;
		}
		
		validator.validate(form, binding);
		
		if (binding.hasErrors()|| expired) {
			System.out.println(binding);
			res = new ModelAndView("actor/edit");
			
			res.addObject("isExpired", expired);
			res.addObject("form", form);
			
			Date d = new Date();
			Collection <Integer> months = new ArrayList<>();
			Collection <Integer> years = new ArrayList<>();
			for (int i = 0; i < 11; i++) {
				months.add(i+1);
				years.add(d.getYear()+i+1900);
			}
			
			res.addObject("showCredit", true);
			res.addObject("months",months);
			res.addObject("years",years);

			return res;
		} else {
			try {
			Customer c = null;
			Scientist s = null;
				
			Authority cAuth = new Authority();
			cAuth.setAuthority(Authority.CUSTOMER);
			
			Authority sAuth = new Authority();
			sAuth.setAuthority(Authority.SCIENTIST);
			
			CreditCard credit = null;
			Actor a = actorService.getPrincipal();
			
			if (a.getUserAccount().getAuthorities().contains(sAuth)) {
				System.out.println("scientist authority detected");
				s = scientistService.getPrincipal();
				credit = s.getCreditCard();
			}
			
			if (a.getUserAccount().getAuthorities().contains(cAuth)) {
				System.out.println("customer authority detected");
				c = customerService.getPrincipal();
				credit = c.getCreditCard();
			}
			 
			if(credit == null){
				credit = cardService.create();
			}
			
			Calendar calendar = Calendar.getInstance();
			calendar.set(form.getExpirationYear(), form.getExpirationMonth()-1, 1);
			Date date = calendar.getTime();
			
			credit.setCVV(form.getCVV());
			credit.setExpirationDate(date);
			credit.setHolder(form.getHolder());
			credit.setMake(form.getMake());
			credit.setNumber(form.getNumber());
			
			CreditCard CC = cardService.save(credit);
			
			if (c != null) {
				c.setCreditCard(CC);
				customerService.save(c);
			}
			
			if (s != null){
				s.setCreditCard(CC);
				scientistService.save(s);
			}

		res = new ModelAndView("redirect:show.do"); 
		return res;
		
	} catch (final Throwable oops) {
		oops.printStackTrace();
		res = this.editCredit();
		return res;
		}
	}
}

//Generate JSON---------------------------------------------------------------------
	@RequestMapping(value = "/generateData", method = RequestMethod.GET)
	public ModelAndView generate() {
		
		ModelAndView res = new ModelAndView("actor/personalData");

		Actor actor = actorService.getPrincipal();
		
		res.addObject("data", actorService.actorToJson(actor));

		return res;
	}

	
//Ancillary---------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(Object form, String type) {
		ModelAndView result;
		result = this.createEditModelAndView(form, type ,null);
		return result;
	}

	private ModelAndView createEditModelAndView(Object form, String type,  String message) {

		ModelAndView result = new ModelAndView("actor/edit");
		
		if(type == "personal"){
			EditPersonalForm f1 = (EditPersonalForm) form;
			result.addObject("form", f1);
		}
		if(type == "credit"){
			EditCreditForm f2 = (EditCreditForm) form;
			result.addObject("form", f2);
		}
		
		Date d = new Date();
		Collection <Integer> months = new ArrayList<>();
		Collection <Integer> years = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			months.add(i+1);
			years.add(d.getYear()+i+1900);
		}
		
		result.addObject("message", message);
		result.addObject("showCredit", type=="credit");
		result.addObject("showPersonal", type=="personal");
		result.addObject("months",months);
		result.addObject("years",years);
		
		return result;
	}
}