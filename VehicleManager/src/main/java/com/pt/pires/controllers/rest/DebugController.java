package com.pt.pires.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pt.pires.initialization.Initializer;

@RestController
public class DebugController {

	@Autowired
	private Initializer init;
	
	@RequestMapping(value="reset",method = RequestMethod.GET)
	public void resetDatabase() throws Exception{
		init.initialize();
	}
	
}
