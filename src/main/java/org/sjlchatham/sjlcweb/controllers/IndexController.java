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
    public String index(@RequestParam(defaultValue = "false") boolean passChanged,
                        Model model) {

        model.addAttribute("title", "St. John's Lutheran Church");
        model.addAttribute("homeActiveStatus", "active");
        model.addAttribute("featuredPost", postDao.findFirstByOrderByTimeStampDesc());

        if (passChanged) {
            model.addAttribute("alertClass", "alert alert-success");
            model.addAttribute("alert", "Password changed successfully!");
        }

        return "index";
    }

}
