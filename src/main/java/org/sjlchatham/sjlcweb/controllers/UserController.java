package org.sjlchatham.sjlcweb.controllers;

import org.sjlchatham.sjlcweb.data.UserDao;
import org.sjlchatham.sjlcweb.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

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

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String showSignUpPage(Model model) {
        model.addAttribute(new User());
        model.addAttribute("title", "Create an Account - St. John's Lutheran Church");
        model.addAttribute("header", "Create an Account");

        return "users/signup";
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String signUpUser(@Valid @ModelAttribute User newUser,
                             Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Create an Account - St. John's Lutheran Church");
            model.addAttribute("header", "Create an Account");

            return "users/signup";
        }

        userDao.save(newUser);

        return "redirect:";

    }

    @RequestMapping(value = "admin", method = RequestMethod.GET)
    public String showAdminPage (ModelMap model, Principal principal) {
        String loggedInUser = principal.getName(); // Username of the currently logged-in user / who is sending this request.
        return "users/admin";
    }

}
