package com.psl.codingchallenge.webservice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handler for errors
 * Overrides the default error page
 */
@RestController
public class DefaultErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        String message = exception != null ? exception.getCause().getMessage() : "Unknown error occurred";
        return message;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
