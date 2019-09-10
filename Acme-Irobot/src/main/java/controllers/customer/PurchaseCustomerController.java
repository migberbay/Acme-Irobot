package controllers.customer;

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

import services.ActorService;
import services.CommentService;
import services.CustomerService;
import services.IrobotService;
import services.PurchaseService;
import controllers.AbstractController;
import domain.CreditCard;
import domain.Customer;
import domain.Irobot;
import domain.Purchase;
import domain.Scientist;
import forms.KeywordForm;
import forms.PurchaseForm;

@Controller
@RequestMapping("purchase/customer")
public class PurchaseCustomerController extends AbstractController {

	@Autowired
	private IrobotService irobotService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private Validator validator;


	// Listing -----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(){

		ModelAndView result = new ModelAndView("purchase/list");
		result.addObject("purchases", customerService.getPrincipal().getPurchases());
		return result;
	}

	// Show -----------------------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam int purchaseId){

		ModelAndView result = new ModelAndView("purchase/show");
		Purchase p = purchaseService.findOne(purchaseId);
		if(p.getCustomer().equals(customerService.getPrincipal())){
		result.addObject("purchase", p);
		}else{
			result = new ModelAndView("error/access");
		}
		return result;
	}

	// Create -----------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int irobotId) {
		
		Irobot irobot = irobotService.findOne(irobotId);
		ModelAndView result = new ModelAndView("purchase/edit");
		PurchaseForm form = new PurchaseForm();
		form.setIrobot(irobot);
		
		Date d = new Date();
		Collection <Integer> months = new ArrayList<>();
		Collection <Integer> years = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			months.add(i+1);
			years.add(d.getYear()+i+1900);
		}
		
		CreditCard credit = customerService.getPrincipal().getCreditCard();
		String last4digits = "";
		if(credit != null){
			last4digits = credit.getNumber().substring(12, 16);
			result.addObject("hasCard", true );
			result.addObject("last4digits", last4digits );
		}
		
		result.addObject("months",months);
		result.addObject("years",years);
		result.addObject("form",form);
		
		return result;

	}

//	// Edit -----------------------------------------------------------------
//
//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
//	public ModelAndView edit(@RequestParam int irobotId) {
//		
//		ModelAndView result;
//		Irobot irobot = irobotService.findOne(irobotId);
//		
//		if (irobot.getScientist().equals(scientistService.getPrincipal())) {
//			result = new ModelAndView();
//			result = this.createEditModelAndView(irobot);
//		}else{
//			result = new ModelAndView("error/access");
//		}
//
//		return result;
//	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("form") PurchaseForm form, BindingResult binding) {
		ModelAndView res;
		
		CreditCard c = new CreditCard();
		Irobot irobot = form.getIrobot();
		Customer principal = customerService.getPrincipal();
		
		if (form.getUseProfileCard()) {
			c = principal.getCreditCard();
			form.setCVV(c.getCVV());
			form.setExpirationMonth(c.getExpirationDate().getMonth()+1);
			form.setExpirationYear(c.getExpirationDate().getYear()+1900);
			form.setHolder(c.getHolder());
			form.setMake(c.getMake());
			form.setNumber(c.getNumber());
			
			
		}
			validator.validate(form, binding);
		
			Boolean expired = false;
			Boolean expiresIn7 = false;
			Calendar cal = Calendar.getInstance();
			
			if(form.getExpirationYear()== cal.get(Calendar.YEAR) && form.getExpirationMonth()-1<=cal.get(Calendar.MONTH)){
				expired = true;
			}
			
			Date in7 = new Date(System.currentTimeMillis()+604800000L); //2592000000L <- 30 days to test;  604800000L <- 7 days
			Date expiration;
			
			if(!form.getUseProfileCard()){
				Calendar cale = Calendar.getInstance();
				cale.set(form.getExpirationYear(), form.getExpirationMonth()-1, 1);
				expiration = cale.getTime();
			}else{
				expiration = c.getExpirationDate();
			}
			
			if(expiration.before(in7)){
				expiresIn7 = true;
			}
			
			if (binding.hasErrors() || expired || expiresIn7) {
				System.out.println(binding);
				res = new ModelAndView("purchase/edit");
				
				if(form.getUseProfileCard()){
					res.addObject("form", new Purchase());
					res.addObject("errorInProfileCard", true);
				}else{
					res.addObject("isExpired", expired);
					res.addObject("form", form);
				}
				
				Date d = new Date();
				Collection <Integer> months = new ArrayList<>();
				Collection <Integer> years = new ArrayList<>();
				for (int i = 0; i < 11; i++) {
					months.add(i+1);
					years.add(d.getYear()+i+1900);
				}
				
				res.addObject("months",months);
				res.addObject("years",years);
				res.addObject("expiresIn7", expiresIn7);
		}else{
			Purchase p = purchaseService.create();
			
			p.setCustomer(principal);
			p.setIrobot(irobot);
			
			if(!form.getUseProfileCard()){
				Calendar cale = Calendar.getInstance();
				cale.set(form.getExpirationYear(), form.getExpirationMonth()-1, 1);
				
				p.setCVV(form.getCVV());
				p.setExpirationDate(cale.getTime());
				p.setHolder(form.getHolder());
				p.setMake(form.getMake());
				p.setNumber(form.getNumber());
			}else{
				p.setCVV(c.getCVV());
				p.setExpirationDate(c.getExpirationDate());
				p.setHolder(c.getHolder());
				p.setMake(c.getMake());
				p.setNumber(c.getNumber());
			}
			
			Purchase saved = purchaseService.save(p);
			irobot.getPurchases().add(saved);
			principal.getPurchases().add(saved);
			
			irobotService.save(irobot);

			res = new ModelAndView("redirect:/irobot/list.do");
		}
			return res;
		
	}

	//Ancillary ----------------------------------------------------------------------

}
