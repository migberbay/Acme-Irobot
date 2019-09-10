package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ScientistService;
import services.IrobotService;

import controllers.AbstractController;


@Controller
@RequestMapping("/admin/")
public class DashboardAdminController extends AbstractController {
	
	
	@Autowired
	private IrobotService irobotService;

	@Autowired
	private ScientistService scientistService;
	
	

	//DASHBOARD--------------------------------------------------------
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public ModelAndView dashboard(){
		ModelAndView res;
	
		res = new ModelAndView("admin/dashboard");

		res.addObject("avgIrobotsPerScientist", Math.round(irobotService.getAverageIrobotPerScientist()*100.0d)/100.0d);
		res.addObject("minIrobotsPerScientist", irobotService.getMinimumIrobotPerScientist());
		res.addObject("maxIrobotsPerScientist", irobotService.getMaximumIrobotPerScientist());
		res.addObject("stdevIrobotsPerScientist", Math.round(irobotService.getStdevIrobotPerScientist()*100.0d)/100.0d);
		
		
		res.addObject("top10bestSellingScientist", scientistService.Top10BestSelling());
		res.addObject("top10bestSellingIrobots", irobotService.top10BestSelling());

		return res;
	}
	
	
}