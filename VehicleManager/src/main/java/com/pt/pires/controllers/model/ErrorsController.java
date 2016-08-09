package com.pt.pires.controllers.model;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for general error page
 * @author André
 *
 */
@Controller
@Scope("session")
public class ErrorsController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public String error() {
        return "/normal/errors/error";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
    
}
