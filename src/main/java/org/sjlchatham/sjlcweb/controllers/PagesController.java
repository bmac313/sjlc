package org.sjlchatham.sjlcweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PagesController {

    @RequestMapping(value = "/about/staff", method = RequestMethod.GET)
    public String showStaffPage(Model model) {

        model.addAttribute("title", "Staff - St. John's Lutheran Church");
        model.addAttribute("header", "Staff");
        model.addAttribute("pageBody", "staff-page-body");

        return "page_generic";
    }

    @RequestMapping(value = "/about/beliefs", method = RequestMethod.GET)
    public String showBeliefsPage(Model model) {

        model.addAttribute("title", "What We Believe - St. John's Lutheran Church");
        model.addAttribute("header", "What We Believe");
        model.addAttribute("pageBody", "beliefs-page-body");

        return "page_generic";
    }

    @RequestMapping(value = "/about/history", method = RequestMethod.GET)
    public String showHistoryPage(Model model) {

        model.addAttribute("title", "Church History - St. John's Lutheran Church");
        model.addAttribute("header", "What We Believe");
        model.addAttribute("pageBody", "history-page-body");

        return "page_generic";
    }

}
