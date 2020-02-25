package com.pt.pires.controllers.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for session expired session
 * @author André
 *
 */
@Controller
@Scope("session")
public class ExpiredSessionController {

	@RequestMapping(value = "/expiredSession",method = RequestMethod.GET)
	public String accessDenied(Model model) {
		return "/normal/errors/expiredSession";
	}
	
}
