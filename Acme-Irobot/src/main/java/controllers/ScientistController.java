package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ScientistService;


@Controller
@RequestMapping("scientist/")
public class ScientistController extends AbstractController {

	@Autowired
	private ScientistService scientistService;

	// Listing -----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result = new ModelAndView("scientist/list");

		result.addObject("scientists", scientistService.findAll());
		result.addObject("requestURI","scientist/list.do");

		return result;
	}

	// Show -----------------------------------------------------------


}
