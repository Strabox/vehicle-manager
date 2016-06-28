package com.pt.pires.controllers.model;

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
public class LoginController {

	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public String login(Model model,
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		
		if (error != null) {
			model.addAttribute("error", "Combinação de username e password inválida");
		}

		if (logout != null) {
			model.addAttribute("msg", "Logout com sucesso");
		}
		return "login";
	}
	
}
