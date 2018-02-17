package org.sjlchatham.sjlcweb.controllers;

import org.sjlchatham.sjlcweb.data.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @Autowired
    private PostDao postDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(@RequestParam(defaultValue = "false") boolean justLoggedIn,
                        @RequestParam(defaultValue = "false") boolean justSignedUp,
                        Model model) {

        if (justLoggedIn) {
            model.addAttribute("alertClass", "alert alert-success");
            model.addAttribute("alert", "Logged in successfully!");
        }

        if (justSignedUp) {
            model.addAttribute("alertClass", "alert alert-success");
            model.addAttribute("alert", "Signed up successfully!");
        }

        model.addAttribute("title", "St. John's Lutheran Church");
        model.addAttribute("homeActiveStatus", "active");
        model.addAttribute("featuredPost", postDao.findOne(1));

        return "index";
    }

}
