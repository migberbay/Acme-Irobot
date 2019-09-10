package controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import services.IrobotService;
import domain.Actor;
import domain.Comment;
import domain.Irobot;

@Controller
@RequestMapping("comment/")
public class CommentController extends AbstractController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private IrobotService irobotService;
	
	// Create -----------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(Integer irobotId) {
		ModelAndView result;

		Irobot irobot = irobotService.findOne(irobotId);
		
		Comment comment = commentService.create();
		comment.setIrobot(irobot);

		result = this.createEditModelAndView(comment);

		return result;

	}

//	// Edit -----------------------------------------------------------------
//
//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
//	public ModelAndView edit(@RequestParam final int commentId) {
//		
//		ModelAndView result;
//		Comment comment = commentService.findOne(commentId);
//		if (actorService.getPrincipal().getComments().contains(comment)) {
//			result = new ModelAndView();
//			result = this.createEditModelAndView(comment);
//		}else{
//			result = new ModelAndView("error/access");
//		}
//
//		return result;
//	}

	// Save -----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Comment comment,BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.getFieldErrors());
			result = this.createEditModelAndView(comment);
		} else
			try {
				comment.setMoment(new Date(System.currentTimeMillis()-1000));
				Actor a = actorService.getPrincipal();
				String body = comment.getBody();
				
				body += "<br><br>- <a href= \"actor/show.do?actorId="+a.getId()+"\">"+a.getUserAccount().getUsername()+"</a>";
				comment.setBody(body);
				Comment saved = this.commentService.save(comment);
				
				a.getComments().add(saved);
				actorService.save(a);
				result = new ModelAndView("redirect:/irobot/list.do");

			} catch (final Throwable oops) {
				for (StackTraceElement a : oops.getStackTrace()) {
					System.out.println(a);
				}
				result = this.createEditModelAndView(comment,"message.commit.error");
			}
		return result;
	}
	
	//Ancillary ----------------------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(Comment comment) {
		ModelAndView result;

		result = this.createEditModelAndView(comment, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final Comment comment, final String message) {

		ModelAndView result;

		result = new ModelAndView("comment/edit");

		result.addObject("comment", comment);
		result.addObject("message", message);

		return result;
	}

	
	// Listing -----------------------------------------------------------------

//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public ModelAndView list(@RequestParam(required = false) KeywordForm keyForm,@RequestParam(required = false) Integer scientistId ) {
//
//		ModelAndView result = new ModelAndView("irobot/list");
//		Collection<Irobot> irobots;
//		
//		if(keyForm ==  null){
//			irobots = irobotService.findAll();
//			result.addObject("form", new KeywordForm());
//		}else{
//			result.addObject("form", keyForm);
//			if (keyForm.getKeyword() == "") {
//				irobots = irobotService.findAll();
//			}else{
//				irobots = irobotService.findByKeyword(keyForm.getKeyword());
//			}
//		}
//		
//		if (scientistId != null) {
//			irobots = irobotService.findByScientist(scientistService.findOne(scientistId));
//		}
//		Boolean loggedUser;
//		try {
//			loggedUser = LoginService.getPrincipal() != null;
//		} catch (Exception e) {
//			loggedUser = false;
//		}
//		
//		result.addObject("irobots", irobots);
//		result.addObject("loggedUser",loggedUser);
//		result.addObject("requestURI","irobot/list.do");
//
//		return result;
//	}
//	
//	@RequestMapping(value = "/list", method = RequestMethod.POST, params = "save")
//	public ModelAndView list2(@ModelAttribute("form") KeywordForm keyForm, BindingResult binding){
//		return list(keyForm, null);
//	}



}
