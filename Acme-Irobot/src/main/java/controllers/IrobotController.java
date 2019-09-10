package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import services.IrobotService;
import services.ScientistService;
import domain.Irobot;
import forms.KeywordForm;

@Controller
@RequestMapping("irobot/")
public class IrobotController extends AbstractController {

	@Autowired
	private IrobotService irobotService;
	
	@Autowired
	private ScientistService scientistService;

	// Listing -----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) KeywordForm keyForm,@RequestParam(required = false) Integer scientistId ) {

		ModelAndView result = new ModelAndView("irobot/list");
		Collection<Irobot> irobots;
		
		Authority customerAuth = new Authority();
		customerAuth.setAuthority(Authority.CUSTOMER);
		
		
		if(keyForm ==  null){
			irobots = irobotService.findAll();
			result.addObject("form", new KeywordForm());
		}else{
			result.addObject("form", keyForm);
			if (keyForm.getKeyword() == "") {
				irobots = irobotService.findAll();
			}else{
				irobots = irobotService.findByKeyword(keyForm.getKeyword());
			}
		}
		
		if (scientistId != null) {
			irobots = irobotService.findByScientist(scientistService.findOne(scientistId));
		}
		Boolean loggedUser;
		Boolean isCustomer;
		try {
			UserAccount principal = LoginService.getPrincipal();
			loggedUser = principal != null;
			isCustomer = principal.getAuthorities().contains(customerAuth);
			System.out.println("auths: " + principal.getAuthorities());
			
		} catch (Exception e) {
			loggedUser = false;
			isCustomer = false;
		}
		
		result.addObject("irobots", irobots);
		result.addObject("loggedUser",loggedUser);
		result.addObject("isCustomer",isCustomer);
		result.addObject("requestURI","irobot/list.do");

		return result;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, params = "save")
	public ModelAndView list2(@ModelAttribute("form") KeywordForm keyForm, BindingResult binding){
		return list(keyForm, null);
	}

	// Show -----------------------------------------------------------


}
