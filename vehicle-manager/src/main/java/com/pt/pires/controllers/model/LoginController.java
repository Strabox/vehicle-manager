package com.pt.pires.controllers.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for login page
 * @author André
 *
 */
@Controller
@Scope("session")
public class LoginController {

	/**
	 * Endpoint that return the login page
	 * @param model Model to HTML processor
	 * @param error Error if invalidLogin
	 * @param logout Logout if successfulLogout
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public String login(Model model,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		model.addAttribute("invalidLogin", false);
		model.addAttribute("successfulLogout", false);
		if (error != null) {
			model.addAttribute("invalidLogin", true);
		}
		if (logout != null) {
			model.addAttribute("successfulLogout", true);
		}
		return "/normal/login";
	}
	
}
