package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ConfigurationService;
import controllers.AbstractController;
import domain.Configuration;

@Controller
@RequestMapping("/admin/")
public class ConfigurationAdminController extends AbstractController {
	
	@Autowired
	private ConfigurationService configurationService;
	
//	@Autowired
//	private ActorService actorService;


	//Edit-------------------------------------------------------------
	@RequestMapping(value="/configuration", method=RequestMethod.GET)
	public ModelAndView edit(){
		ModelAndView res;
		
		//como solo debe existir una se puede hacer esto.
		Configuration c = (Configuration) configurationService.findAll().toArray()[0];
		res = this.createEditModelAndView(c);
		
		return res;
	}
	
	//Save FORM-------------------------------------------------------------	
	@RequestMapping(value="/configuration", method=RequestMethod.POST, params="save")
	public ModelAndView save(@Valid Configuration config, BindingResult binding){
		ModelAndView res;
		
		System.out.println(config.getBanner()+ " " +config.getSystemName()+ " "+ config.getWelcomeTextEnglish());
		System.out.println(binding);
		
		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			res = this.createEditModelAndView(config);
		} else{
		try {
			Configuration configuration = configurationService.findOne(config.getId());
			System.out.println(config);
			config.setVersion(configuration.getVersion());
			System.out.println(config);
			configurationService.save(config);
			res = new ModelAndView("redirect:/welcome/index.do");
		} catch (Throwable e) {
			res = new ModelAndView("admin/configuration");
			res.addObject("configuration", config);
			res.addObject("errorMessage", "admin.commit.error");
			e.printStackTrace();
		}
	}
		return res;
	}
	
	//Add and remove methods ------------------------------------------
	
	// word------------------------------------------------------------
/*	@RequestMapping(value="/addSpamWord", method=RequestMethod.POST, params="save")
	public ModelAndView addSpam(@Valid Word word, BindingResult binding){
		ModelAndView res;
		
		Configuration config = (Configuration) configurationService.findAll().toArray()[0];		
		
		if(binding.hasErrors()){
			res = createEditModelAndView(config);
		}else{
			try {
				Word saved = wordService.save(word);
				
				List<Word> aux = config.getspamWords();
				aux.add(saved);
				config.setSpamWords(aux);
				Configuration savedc = configurationService.save(config);
				
				res = createEditModelAndView(savedc);
			} catch (Throwable e) {
				e.printStackTrace();
				res = createEditModelAndView(config, "admin.commit.error");
				
			}
		}
		return res;
	}
	
	@RequestMapping(value="/deleteSpam", method=RequestMethod.GET)
	public ModelAndView removeSpam(@RequestParam int wordId){
		ModelAndView res;
		if (!LoginService.hasRole("ADMIN")) {
			res = new ModelAndView("error/access");
		}else{
		
		Configuration config = (Configuration) configurationService.findAll().toArray()[0];
			try {
				List<Word> aux = config.getspamWords();
				aux.remove(wordService.findOne(wordId));
				config.setSpamWords(aux);
				Configuration saved = configurationService.save(config);
				
				wordService.delete(wordService.findOne(wordId));
				res = createEditModelAndView(saved);
			} catch (Throwable e) {
				res = createEditModelAndView(config, "admin.commit.error");
				
			}
		}
		return res;
	}
	
	@RequestMapping(value="/listActors", method=RequestMethod.GET)
	public ModelAndView listActors(){
		ModelAndView res;
		Authority userAuth = new Authority();
		userAuth.setAuthority(Authority.USER);
		if (!LoginService.hasRole("ADMIN")) {
			res = new ModelAndView("error/access");
		}else{
			try{
				res = new ModelAndView("admin/listActors");
			} catch (Throwable e) {
				e.printStackTrace();
				res = new ModelAndView("admin/listActors");
				
			}
		}
		res.addObject("userAuth", userAuth);
		res.addObject("actors",actorService.findAll());
		return res;
	}
	*/
//	@RequestMapping(value="/computeSpammers", method=RequestMethod.GET)
//	public ModelAndView computeSpammers(){
//		ModelAndView res;
//		Authority userAuth = new Authority();
//		userAuth.setAuthority(Authority.USER);
//		if (!LoginService.hasRole("ADMIN")) {
//			res = new ModelAndView("error/access");
//		}else{
//		Configuration config = (Configuration) configurationService.findAll().toArray()[0];
//			try {
//				List<Word> spamWords = config.getspamWords();
//				Collection<Actor> actors = actorService.findAll();
//				
//				for (Actor a : actors) {
//					Double spamCont = 0.;
//					Double totalCont= 0.;
//					for (Message m : messageService.findBySender(a)) {
//						if(messageService.hasSpam(spamWords, m)){
//							spamCont++;
//						}
//						totalCont++;
//					}
//					if((spamCont/totalCont)>=0.1){
//						a.setIsSuspicious(true);
//					}else{
//						a.setIsSuspicious(false);
//					}
//					actorService.save(a);
//				}
//				
//				res = new ModelAndView("admin/listActors");
//				res.addObject("computedSpammers",true);
//			} catch (Throwable e) {
//				e.printStackTrace();
//				res = new ModelAndView("redirect:listActors.do");
//				
//			}
//		}
//		res.addObject("userAuth", userAuth);
//		res.addObject("actors",actorService.findAll());
//		return res;
//	}
//	
//	@RequestMapping(value="/computeScore", method=RequestMethod.GET)
//	public ModelAndView computeScore(){
//		ModelAndView res;
//		Authority userAuth = new Authority();
//		userAuth.setAuthority(Authority.USER);
//		if (!LoginService.hasRole("ADMIN")) {
//			res = new ModelAndView("error/access");
//		}else{
//		Collection<User> users = userService.findAll();
//			try {				
//				for (User user : users) {
//					System.out.println(user.getBets().size());
//					Double won = 0.;
//					Double lost = 0.;
//					for (Bet bet : user.getBets()) {
//						System.out.println(bet.getWinner()+ " "+ bet.getBetPool().getWinner());
//						if(bet.getBetPool().getWinner().equals(bet.getWinner())){
//							won++;
//						}else{lost++;}
//					}
//					user.setLuckScore(won - 0.5*lost);
//					userService.save(user);
//				}
//				res = new ModelAndView("admin/listActors");
//				res.addObject("computedScore",true);
//			} catch (Throwable e) {
//				e.printStackTrace();
//				res = new ModelAndView("redirect:listActors.do");
//			}
//		}
//		res.addObject("userAuth", userAuth);
//		res.addObject("actors",actorService.findAll());
//		return res;
//	}
//	
//	@RequestMapping(value="/banActor", method=RequestMethod.GET)
//	public ModelAndView banActor(@RequestParam int actorId){
//		ModelAndView res;
//		if (!LoginService.hasRole("ADMIN")) {
//			res = new ModelAndView("error/access");
//		}else{
//			try {
//				
//				Actor actor = actorService.findOne(actorId);
//				if(!actor.getIsBanned()){
//				actor.setIsBanned(true);
//				actorService.save(actor);
//				}
//				
//				res = new ModelAndView("redirect:listActors.do");
//			} catch (Throwable e) {
//				e.printStackTrace();
//				res = new ModelAndView("redirect:listActors.do");
//				
//			}
//		}
//		return res;
//	}
//
//	@RequestMapping(value="/unbanActor", method=RequestMethod.GET)
//	public ModelAndView unbanActor(@RequestParam int actorId){
//		ModelAndView res;
//		if (!LoginService.hasRole("ADMIN")) {
//			res = new ModelAndView("error/access");
//		}else{
//			try {
//				
//				Actor actor = actorService.findOne(actorId);
//				if(actor.getIsBanned()){
//				actor.setIsBanned(false);
//				actorService.save(actor);
//				}
//				
//				res = new ModelAndView("redirect:listActors.do");
//			} catch (Throwable e) {
//				e.printStackTrace();
//				res = new ModelAndView("redirect:listActors.do");
//				
//			}
//		}
//		return res;
//	}
	/*
	
	@RequestMapping(value="/notifyUpdate", method=RequestMethod.GET)
	public ModelAndView notifyUpdate(){
		ModelAndView res;
		if (!LoginService.hasRole("ADMIN")) {
			res = new ModelAndView("error/access");
		}else{
			try {
				List <Actor> actors = new ArrayList<>(actorService.findAll());
				
				Configuration config = configurationService.find();
				Boolean isSent =  config.getNotificationIsSent();
				if(!isSent){
					MessageForm form = new MessageForm();
					String recipients = " ";
					
					for (int i = 0; i < actors.toArray().length; i++) {
						recipients = recipients + actors.get(i).getUserAccount().getUsername();
						if(i<actors.toArray().length-1){
							recipients = recipients + " , ";
						}
					}
					
					form.setRecipients(recipients);
					form.setIsBroadcast(true);
					form.setTags("SYSTEM");
					form.setBody("The owners of the site have decided to change the domain name to Acme-Rookies, due to this every reference to the" +
							"concept of hacker is now referenced to as rookie, the system works as previously.");
					form.setSubject("Change in the naming of hackers.");
					
					
					BindingResult binding = null;
					Collection<Message> messages = messageService.reconstruct(form, binding);
					
					for (Message m : messages) {
						messageService.save(m);
					}
					config.setNotificationIsSent(true);
					configurationService.save(config);
				}
				res = new ModelAndView("redirect:/message/list.do");
			} catch (Throwable e) {
				e.printStackTrace();
				res = new ModelAndView("redirect:/message/list.do");
				
			}
		}
		return res;
	}*/
	
	//Helper methods---------------------------------------------------
	protected ModelAndView createEditModelAndView(Configuration config){
		ModelAndView res;
		res = createEditModelAndView(config, null);
		return res;
	}
	
	protected ModelAndView createEditModelAndView(Configuration config, String messageCode){
		ModelAndView res;
		
		res = new ModelAndView("admin/configuration");
		
//		Boolean language = false;
//		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("es")){
//			language=true;
//		}
//		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("en")){
//			language = false;
//		}
//		
		res.addObject("configuration", config);
		res.addObject("errorMessage", messageCode);
		
		return res;
	}
	
}