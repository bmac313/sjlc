package org.sjlchatham.sjlcweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PagesController {

    @RequestMapping(value = "/about/staff", method = RequestMethod.GET)
    public String showStaffPage(Model model) {

        String[] sideBarImagePaths = {"/img/altar.png", "/img/piano.png", "/img/aisle.png"};

        model.addAttribute("sideBarImagePaths", sideBarImagePaths);
        model.addAttribute("title", "Staff - St. John's Lutheran Church");
        model.addAttribute("header", "Staff");
        model.addAttribute("aboutActiveStatus", "active");
        model.addAttribute("pageBodyText", "staff-page-body");

        return "about/staff";
    }

    @RequestMapping(value = "/about/beliefs", method = RequestMethod.GET)
    public String showBeliefsPage(Model model) {

        String[] sideBarImagePaths = {"/img/altar.png", "/img/piano.png", "/img/aisle.png"};

        model.addAttribute("sideBarImagePaths", sideBarImagePaths);
        model.addAttribute("headerImagePath", "/img/about_us_header.png");
        model.addAttribute("title", "What We Believe - St. John's Lutheran Church");
        model.addAttribute("header", "What We Believe");
        model.addAttribute("aboutActiveStatus", "active");
        model.addAttribute("pageBodyText", "beliefs-page-body");

        return "about/beliefs";
    }

    @RequestMapping(value = "/about/worship", method = RequestMethod.GET)
    public String showWorshipPage(Model model) {

        String[] sideBarImagePaths = {"/img/altar.png", "/img/piano.png", "/img/aisle.png"};

        model.addAttribute("sideBarImagePaths", sideBarImagePaths);
        model.addAttribute("headerImagePath", "/img/altar_wide.png");
        model.addAttribute("title", "About Our Worship - St. John's Lutheran Church");
        model.addAttribute("header", "About Our Worship");
        model.addAttribute("aboutActiveStatus", "active");
        model.addAttribute("pageBodyText", "worship-page-body");

        return "page_generic";
    }

    @RequestMapping(value = "/about/history", method = RequestMethod.GET)
    public String showHistoryPage(Model model) {

        String[] sideBarImagePaths = {"/img/aisle_2.png", "/img/tall_cross.png", "/img/wooden_art.png"};

        model.addAttribute("sideBarImagePaths", sideBarImagePaths);
        model.addAttribute("headerImagePath", "/img/church_history_header.png");
        model.addAttribute("title", "Church History - St. John's Lutheran Church");
        model.addAttribute("header", "Church History");
        model.addAttribute("aboutActiveStatus", "active");
        model.addAttribute("pageBodyText", "history-page-body");

        return "page_generic";
    }

    @RequestMapping(value = "/preschool", method = RequestMethod.GET)
    public String showPreschoolPage(Model model) {

        String[] sideBarImagePaths = {"/img/altar.png", "/img/piano.png", "/img/aisle.png"};

        model.addAttribute("sideBarImagePaths", sideBarImagePaths);
        model.addAttribute("headerImagePath", "/img/preschool_header.png");
        model.addAttribute("title", "Preschool - St. John's Lutheran Church");
        model.addAttribute("header", "St. John's Lutheran Preschool");
        model.addAttribute("psActiveStatus", "active");
        model.addAttribute("pageBodyText", "preschool-page-body");

        return "page_generic";
    }

    @RequestMapping(value = "/calendar", method = RequestMethod.GET)
    public String showCalendarPage(Model model) {

        String[] sideBarImagePaths = {"/img/altar.png", "/img/piano.png", "/img/aisle.png"};

        model.addAttribute("sideBarImagePaths", sideBarImagePaths);
        model.addAttribute("headerImagePath", "/img/altar_wide.png");
        model.addAttribute("title", "Calendar - St. John's Lutheran Church");
        model.addAttribute("header", "Calendar");
        model.addAttribute("newsActiveStatus", "active");
        model.addAttribute("pageBodyText", "calendar-page-body");

        return "page_generic";
    }

    @RequestMapping(value = "/links", method = RequestMethod.GET)
    public String showLinksPage(Model model) {

        String[] sideBarImagePaths = {"/img/altar.png", "/img/piano.png", "/img/aisle.png"};

        model.addAttribute("sideBarImagePaths", sideBarImagePaths);
        model.addAttribute("headerImagePath", "/img/altar_wide.png");
        model.addAttribute("title", "Links - St. John's Lutheran Church");
        model.addAttribute("header", "Links");
        model.addAttribute("newsActiveStatus", "active");
        model.addAttribute("pageBodyText", "links-page-body");

        return "page_generic";
    }

    @RequestMapping(value = "/downloads/sermons", method = RequestMethod.GET)
    public String showSermonsPage(Model model) {

        String[] sideBarImagePaths = {"/img/altar.png", "/img/piano.png", "/img/aisle.png"};

        model.addAttribute("sideBarImagePaths", sideBarImagePaths);
        model.addAttribute("headerImagePath", "/img/altar_wide.png");
        model.addAttribute("title", "Archived Sermons - St. John's Lutheran Church");
        model.addAttribute("header", "Archived Sermons");
        model.addAttribute("sermonsActiveStatus", "active");
        model.addAttribute("pageBodyText", "sermons-page-body");

        return "page_generic";
    }

    @RequestMapping(value = "/downloads/newsletter", method = RequestMethod.GET)
    public String showNewsletterPage(Model model) {

        String[] sideBarImagePaths = {"/img/altar.png", "/img/piano.png", "/img/aisle.png"};

        model.addAttribute("sideBarImagePaths", sideBarImagePaths);
        model.addAttribute("headerImagePath", "/img/altar_wide.png");
        model.addAttribute("title", "Newsletter - St. John's Lutheran Church");
        model.addAttribute("header", "Newsletter");
        model.addAttribute("sermonsActiveStatus", "active");
        model.addAttribute("pageBodyText", "newsletter-page-body");

        return "page_generic";
    }

}
