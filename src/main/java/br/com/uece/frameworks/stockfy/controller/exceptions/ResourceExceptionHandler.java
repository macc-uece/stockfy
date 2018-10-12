package br.com.uece.frameworks.stockfy.controller.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import br.com.uece.frameworks.stockfy.service.exceptions.DataIntegrityException;
import br.com.uece.frameworks.stockfy.service.exceptions.ObjectNotFoundException;

/**
 * ResourceExceptionHandler
 */

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
	public ModelAndView objectNotFound(ObjectNotFoundException e) {
		ModelAndView mav = new ModelAndView("404");
		return mav;
	}

	@ExceptionHandler(DataIntegrityException.class)
	public ModelAndView dataIntegrity(DataIntegrityException e) {
		ModelAndView mav = new ModelAndView("400");
		return mav;
	}
}