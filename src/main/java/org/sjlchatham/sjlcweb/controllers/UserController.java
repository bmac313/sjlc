package org.sjlchatham.sjlcweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String showLoginPage(Model model) {
        model.addAttribute("title", "Log In - St. John's Lutheran Church");
        model.addAttribute("header", "Log In");
        return "users/login";
    }

    @RequestMapping(value = "login-error", method = RequestMethod.GET)
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("title", "Log In - St. John's Lutheran Church");
        model.addAttribute("header", "Log In");
        return "users/login";
    }

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public String showAdminPage (ModelMap model) {
        return "users/admin";
    }

}
