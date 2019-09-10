package controllers.scientist;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CommentService;
import services.IrobotService;
import services.PurchaseService;
import services.ScientistService;
import controllers.AbstractController;
import domain.Actor;
import domain.Comment;
import domain.CreditCard;
import domain.Irobot;
import domain.Purchase;
import domain.Scientist;
import forms.KeywordForm;

@Controller
@RequestMapping("irobot/scientist")
public class IrobotScientistController extends AbstractController {

	@Autowired
	private IrobotService irobotService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ScientistService scientistService;
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private ActorService actorService;


	// Listing -----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(){

		ModelAndView result;
		Scientist s = scientistService.getPrincipal();
		CreditCard cc =  s.getCreditCard();
		
		if(cc != null){
			Date in7 = new Date(System.currentTimeMillis()+604800000L); //2592000000L <- 30 days to test;  604800000L <- 7 days
			System.out.println("Expiration date: " +cc.getExpirationDate()+"  Checking date: "+ in7);
			if(cc.getExpirationDate().after(in7)){
				result = new ModelAndView("irobot/list");
				result.addObject("irobots", irobotService.findByScientist(s));
				result.addObject("loggedUser", true);
				result.addObject("isOwner", true);
				result.addObject("form", new KeywordForm());
				result.addObject("requestURI","irobot/scientist/list.do");
			}else{
				result =  new ModelAndView("error/access");
				result.addObject("creditCardNotValid",true);
			}
		}else{
			result =  new ModelAndView("error/access");
			result.addObject("creditCardNotValid",true);
		}
		
		

		return result;
	}

	// Show -----------------------------------------------------------
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(Integer irobotId){

		ModelAndView result = new ModelAndView("irobot/show");
		Irobot i = irobotService.findOne(irobotId);
		if(i.getScientist().equals(scientistService.getPrincipal())){
		result.addObject("irobot", i);
		result.addObject("comments", commentService.findByIrobot(i));
		}else{
			result = new ModelAndView("error/access");
		}
		return result;
	}

	// Create -----------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		
		Scientist s = scientistService.getPrincipal();
		CreditCard cc =  s.getCreditCard();
		
		if(cc != null){
			Date in7 = new Date(System.currentTimeMillis()+604800000L);
			if(cc.getExpirationDate().after(in7)){
				Irobot irobot = irobotService.create(s);
				result = this.createEditModelAndView(irobot);
			}else{
				result =  new ModelAndView("error/access");
				result.addObject("creditCardNotValid",true);
			}
		}else{
			result =  new ModelAndView("error/access");
			result.addObject("creditCardNotValid",true);
		}
		

		return result;

	}

	// Edit -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int irobotId) {
		
		ModelAndView result;
		Irobot irobot = irobotService.findOne(irobotId);
		
		if (irobot.getScientist().equals(scientistService.getPrincipal())) {
			result = new ModelAndView();
			result = this.createEditModelAndView(irobot);
		}else{
			result = new ModelAndView("error/access");
		}

		return result;
	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Irobot irobot, BindingResult binding) {
		ModelAndView result;

		
		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView(irobot);
		} else
			try {
				Irobot saved; 
				if (irobot.getId() == 0) {
					saved = irobotService.save(irobot);
					Scientist s = scientistService.getPrincipal();
					s.getIrobots().add(saved);
					scientistService.save(s);
				}else{
					
					Irobot ir = irobotService.findOne(irobot.getId());
					ir.setDescription(irobot.getDescription());
					ir.setIsDecomissioned(irobot.getIsDecomissioned());
					ir.setPrice(irobot.getPrice());
					ir.setTitle(irobot.getTitle());
					irobotService.save(ir);
				}
				
				result = new ModelAndView("redirect:/irobot/scientist/list.do");

			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(irobot,"message.commit.error");
			}
		return result;
	}
	
	// Delete -----------------------------------------------------------------

		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam int irobotId) {
			
			ModelAndView result;
			Irobot irobot = irobotService.findOne(irobotId);
			Scientist s = irobot.getScientist();
			
			if (irobot.getScientist().equals(scientistService.getPrincipal())) {
				for (Purchase p : irobot.getPurchases()) {
					System.out.println("deleted: " + p);
					purchaseService.delete(p);
				}
				
				Collection<Comment> comments = commentService.findByIrobot(irobot);
				System.out.println(comments);
				
				for (Comment c : comments) {
					Actor a = actorService.findByComment(c);
					a.getComments().remove(c);
					System.out.println("deleted: " + c);
					commentService.delete(c);
				}
				
				s.getIrobots().remove(irobot);
				irobotService.delete(irobot);
				result = new ModelAndView("redirect:/irobot/scientist/list.do");
				
			}else{
				result = new ModelAndView("error/access");
			}

			return result;
		}
	
	//Ancillary ----------------------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Irobot irobot) {
		ModelAndView result;
		result = this.createEditModelAndView(irobot, null);
		return result;
	}

	private ModelAndView createEditModelAndView(Irobot irobot, final String message) {
		ModelAndView result;
		result = new ModelAndView("irobot/edit");
		result.addObject("irobot", irobot);
		result.addObject("message", message);
		return result;
	}

}
