package com.pt.pires.controllers.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	@RequestMapping(value="/hello",method = RequestMethod.GET)
	public String test(){
		System.out.println("a");
		return "greeting";
	}
	
}
