package br.com.uece.frameworks.stockfy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Create by Bruno Barbosa - 11/10/2018
 */
@Controller
public class HomeController {

    @RequestMapping("/login")
    public String login() {
        return "/pages/login";
    }

    @RequestMapping({"/home", "/"})
    public String home() {
        return "/pages/home";
    }

    @RequestMapping("/403")
    public String erro403() {
        return "/error/page_403";
    }

    @RequestMapping("/500")
    public String erro500() {
        return "/error/page_500";
    }
}
