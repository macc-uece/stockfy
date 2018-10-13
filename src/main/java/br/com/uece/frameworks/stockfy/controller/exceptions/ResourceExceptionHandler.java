package br.com.uece.frameworks.stockfy.controller.exceptions;

import br.com.uece.frameworks.stockfy.service.exceptions.DataIntegrityException;
import br.com.uece.frameworks.stockfy.service.exceptions.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * ResourceExceptionHandler
 */

@ControllerAdvice
public class ResourceExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ObjectNotFoundException.class)
    public ModelAndView objectNotFound(ObjectNotFoundException e, final Model model) {
        logger.error("Exception during execution application", e);
        String errorMessage = (e != null ? e.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return new ModelAndView("/404", "Error404", model);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ModelAndView dataIntegrity(DataIntegrityException e, final Model model) {
        logger.error("Exception during execution application", e);
        String errorMessage = (e != null ? e.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return new ModelAndView("/400", "Error400", model);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView exception(final Throwable throwable, final Model model) throws IOException {
        logger.error("Exception during execution application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return new ModelAndView("/500", "Error500", model);
    }
}