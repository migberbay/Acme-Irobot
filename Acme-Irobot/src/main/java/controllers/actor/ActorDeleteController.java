package controllers.actor;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import services.ActorService;
import services.CommentService;
import services.CreditCardService;
import services.CustomerService;
import services.IrobotService;
import services.PurchaseService;
import services.ScientistService;
import controllers.AbstractController;
import domain.Actor;
import domain.Comment;
import domain.CreditCard;
import domain.Customer;
import domain.Irobot;
import domain.Purchase;
import domain.Scientist;

@Controller
@RequestMapping("actor/")
public class ActorDeleteController extends AbstractController {

	// Services ----------------------------------------------------------------

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ScientistService scientistService;
	
	@Autowired
	private CreditCardService creditCardService;
	
	@Autowired
	private IrobotService irobotService;
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private CommentService commentService;

	// Constructors ------------------------------------------------------------

	public ActorDeleteController() {
		super();
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteActor() {
		ModelAndView res;
		Collection <Authority> auths = LoginService.getPrincipal().getAuthorities();
		Authority customerAuth = new Authority();
		Authority scientistAuth = new Authority();

		customerAuth.setAuthority(Authority.CUSTOMER);
		scientistAuth.setAuthority(Authority.SCIENTIST);


		if (auths.contains(customerAuth)) {
			res = this.deleteCustomer();
		}else if(auths.contains(scientistAuth)){
			res = this.deleteScientist();
		}else{
			res = new ModelAndView("error/access");
		}
		
		return res;
	}
	
	
	public ModelAndView deleteCustomer() {
		
		ModelAndView res = new ModelAndView("redirect:/j_spring_security_logout");
	try {
		Customer c = customerService.getPrincipal();
		
		for (Comment comment : c.getComments()) {
			c.getComments().remove(comment);
			c = customerService.save(c);
			System.out.println("deleting: "+ comment);
			comment.setIrobot(null);
			comment = commentService.save(comment);
			commentService.delete(comment);
		}
		
		for (Purchase p : c.getPurchases()) {
			p.getIrobot().getPurchases().remove(p);
			irobotService.save(p.getIrobot());
			System.out.println("deleting: "+ p);
			purchaseService.delete(p);
		}
		
		
		UserAccount ua = c.getUserAccount();
		
		CreditCard creditCard = c.getCreditCard();
		c.setCreditCard(null);
		c = customerService.save(c);
		creditCardService.delete(creditCard);
		
		customerService.delete(c);
		userAccountService.delete(ua);
	} catch (Exception e) {
		deleteCustomer();
	}
		return res;
	}
	
	public ModelAndView deleteScientist() {
		
		ModelAndView res = new ModelAndView("redirect:/j_spring_security_logout");
//		ModelAndView res = new ModelAndView("redirect:/");
		
		try {

		Scientist s = scientistService.getPrincipal();
		
		for (Comment comment : s.getComments()) {
			s.getComments().remove(comment);
			s = scientistService.save(s);
			System.out.println("deleting: "+ comment);
			comment.setIrobot(null);
			comment = commentService.save(comment);
			commentService.delete(comment);
		}
		
		Collection<Irobot> robots = s.getIrobots();
		
		for (int i = 0; i < robots.size(); i++) {
		Irobot irobot = (Irobot) robots.toArray()[i];
		
		Collection<Purchase> purchases = irobot.getPurchases(); 
		
			for (int j = 0; j < purchases.size(); j++) {
				Purchase p =(Purchase) purchases.toArray()[j];
				System.out.println("deleted: " + p);
				irobot.getPurchases().remove(p);
				purchaseService.delete(p);
			}
			
		Collection<Comment> comments = commentService.findByIrobot(irobot);
			
			for (Comment c : comments) {
				Actor a = actorService.findByComment(c);
				a.getComments().remove(c);
				System.out.println("deleted: " + c);
				commentService.delete(c);
			}
			
			s.getIrobots().remove(irobot);
			System.out.println("deleting: "+ irobot);
			irobotService.delete(irobot);
		}

		UserAccount ua = s.getUserAccount();	
		
		CreditCard creditCard = s.getCreditCard();
		s.setCreditCard(null);
		s = scientistService.save(s);
		creditCardService.delete(creditCard);
		
		scientistService.delete(s);
		userAccountService.delete(ua);
		
		
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("Caught one boss!");
			deleteScientist();
		}
		
		return res;
	}
}
