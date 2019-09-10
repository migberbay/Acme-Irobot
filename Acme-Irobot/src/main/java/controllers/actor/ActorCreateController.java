package controllers.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.ActorService;
import controllers.AbstractController;
import domain.Actor;
import forms.RegisterForm;

@Controller
@RequestMapping("actor/")
public class ActorCreateController extends AbstractController {

	// Services ----------------------------------------------------------------

	// @Autowired
	// private UserAccountService userAccountService;

	@Autowired
	private ActorService actorService;

	// Constructors ------------------------------------------------------------

	public ActorCreateController() {
		super();
	}

	// Create -----------------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerActor(String type) {
		RegisterForm form = new RegisterForm();
		System.out.println(type);
		form.setType(type);
		return this.createRegisterModelAndView(form);
	}

	
	// SAVE-------------------------------------------------------
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("registerForm") RegisterForm registerForm, final BindingResult binding) {
		Boolean passMatch = false;
		
		if(registerForm.getPassword().equals(registerForm.getPassword2())){passMatch=true;}
		
		Actor actor = this.actorService.reconstruct(registerForm, binding);//de aqui ya sale con la authority y datos basicos.
			
			if (binding.hasErrors() || !passMatch) {
				System.out.println(binding);
				ModelAndView res = this.createRegisterModelAndView(registerForm);
				res.addObject("passMatch", passMatch);
				return res;
			} else {
			try {
				switch (registerForm.getType()) {
				case "CUSTOMER":
					actorService.registerCustomer(actor, registerForm);
					
					break;
				case "SCIENTIST":
					actorService.registerScientist(actor, registerForm);
					
					break;
				case "ADMIN":
					actorService.registerAdmin(actor);
					
					break;
				}
				return new ModelAndView("redirect:/");
			
		} catch (final Throwable oops) {
			oops.printStackTrace();
			return this.createRegisterModelAndView(registerForm, "commit.error");
			}
			
		}
	}

	
// Ancillary methods -------------------------------------------------------------------------
	
	protected ModelAndView createRegisterModelAndView(RegisterForm form) {
		ModelAndView result;
		result = this.createRegisterModelAndView(form, null);
		return result;
	}
	

	protected ModelAndView createRegisterModelAndView(RegisterForm form, String messageCode) {
		ModelAndView res;
		
		Authority adminAuth = new Authority();
		adminAuth.setAuthority(Authority.ADMIN);
		
		Boolean isAdmin = false;
		
		try {
			isAdmin = LoginService.getPrincipal().getAuthorities().contains(adminAuth);
		} catch (Exception e) {
			System.out.println("the user is not logged?");
		}
		
		if (form.getType().equals("ADMIN") && !isAdmin) {
			return new ModelAndView("error/access");
		}else{
		res = new ModelAndView("actor/register");
		
		res.addObject("registerForm",form);
		res.addObject("message", messageCode);
		return res;
	}
}
	


}
