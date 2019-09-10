/*
 * WelcomeController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.ConfigurationService;
import domain.Actor;
import domain.Configuration;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	
	@Autowired
	ConfigurationService configurationService;
	
	@Autowired
	ActorService actorService;
	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView result;
		
		SimpleDateFormat formatter;
		String moment;
		String name = "Anonymous";
		boolean actorIsBanned = false;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		
		if(LoginService.hasRole("ADMIN") || LoginService.hasRole("CUSTOMER") || LoginService.hasRole("SCIENTIST")){
		Actor actor = actorService.getPrincipal();
		name = actor.getName();
		}
		Configuration c = (Configuration) configurationService.findAll().toArray()[0];
		String welcomeText=" ";
		
//		System.out.println("el locale es este: "+ LocaleContextHolder.getLocale().getLanguage());
		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("es")){
			welcomeText = c.getWelcomeTextSpanish();
		}

		if(LocaleContextHolder.getLocale().getLanguage().toLowerCase().equals("en")){
			welcomeText = c.getWelcomeTextEnglish();
		}

		if (actorIsBanned) {
			result = new ModelAndView("redirect:/j_spring_security_logout");
		}else{
		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("welcomeMessageToDisplay",welcomeText);
		result.addObject("systemName", c.getSystemName());
		result.addObject("moment", moment);
		}

		return result;
	}

	//TOS ----------------------------------------------------------------------

	@RequestMapping(value = "/TOS")
	public ModelAndView TOS() {
		final ModelAndView result = new ModelAndView("welcome/TOS");
		return result;
	}
}
