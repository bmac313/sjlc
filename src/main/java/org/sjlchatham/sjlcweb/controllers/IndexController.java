package org.sjlchatham.sjlcweb.controllers;

import org.sjlchatham.sjlcweb.data.NewsItemDao;
import org.sjlchatham.sjlcweb.models.NewsItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class IndexController {

    @Autowired
    private NewsItemDao newsItemDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("title", "St. John's Lutheran Church");
        model.addAttribute("homeActiveStatus", "active");

        return "index";
    }


    @RequestMapping(value = "news", method = RequestMethod.GET)
    public String index(Model model,
                        @RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "false") boolean justLoggedIn,
                        @RequestParam(defaultValue = "false") boolean justSignedUp) {

        PageRequest pageRequest = new PageRequest(page-1, 5, Sort.Direction.DESC, "timeStamp");
        int pages = newsItemDao.findAll(pageRequest).getTotalPages();

        if (justLoggedIn) {
            model.addAttribute("alertClass", "alert alert-success");
            model.addAttribute("alert", "Logged in successfully!");
        }

        if (justSignedUp) {
            model.addAttribute("alertClass", "alert alert-success");
            model.addAttribute("alert", "Signed up successfully!");
        }

        if ((page-1) <= 0) {
            model.addAttribute("visibilityPrev", "hidden");
        }

        if ((page) >= pages) {
            model.addAttribute("visibilityNext", "hidden");
        }

        model.addAttribute("newsitems", newsItemDao.findAll(pageRequest));
        model.addAttribute("title", "Latest Finds - MusicFinds");

        model.addAttribute("page", page);
        model.addAttribute("homeActiveStatus", "active");

        return "newsitems/news";
    }

    @RequestMapping(value = "newpost", method = RequestMethod.GET)
    public String showNewPostForm(Model model) {

        model.addAttribute("title", "Share a Find - MusicFinds");
        model.addAttribute("header", "Share a Find");
        model.addAttribute(new NewsItem());

        model.addAttribute("findsActiveStatus", "active");

        return "newsitems/new-post";
    }

    @RequestMapping(value = "newpost", method = RequestMethod.POST)
    public String handleNewPostSubmission(@Valid @ModelAttribute NewsItem postToAdd,
                                          Errors errors,
                                          Model model) {

        if (errors.hasErrors()) {

            model.addAttribute("title", "New Post - St. John's Lutheran Church");
            model.addAttribute("header", "Create a News Post");

            model.addAttribute("findsActiveStatus", "active");

            return "newsitems/new-post";
        }

        newsItemDao.save(postToAdd);

        return "redirect:";
    }

    @RequestMapping(value = "viewpost/{id}", method = RequestMethod.GET)
    public String viewPost(@PathVariable(value = "id") int id,
                           Model model) {

        NewsItem newsItem = newsItemDao.findOne(id);

        model.addAttribute("title", "View Post - MusicFinds");
        model.addAttribute("post", newsItem);

        model.addAttribute("findsActiveStatus", "active");

        return "newsitems/view-post";
    }

}
