package br.com.uece.frameworks.stockfy.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create by Bruno Barbosa - 11/10/2018
 */
public class Error500 {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void exception(final Throwable throwable, final Model model, final HttpServletResponse response) throws IOException {
        logger.error("Exception during execution application", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        response.sendRedirect("/500");
    }
}
