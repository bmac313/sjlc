package org.sjlchatham.sjlcweb.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @RequestMapping("login")
    public String showLoginPage(Model model) {
        return "users/login";
    }

}
