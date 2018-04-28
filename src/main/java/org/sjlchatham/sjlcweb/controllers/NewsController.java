package org.sjlchatham.sjlcweb.controllers;

import org.sjlchatham.sjlcweb.data.PostDao;
import org.sjlchatham.sjlcweb.models.Post;
import org.sjlchatham.sjlcweb.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("news")
public class NewsController {

    @Autowired
    private PostDao postDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showNewsPage(@RequestParam(defaultValue = "1") int page, Model model) {

        PageRequest pageRequest = new PageRequest(page-1, 5, Sort.Direction.DESC, "timeStamp");
        int pages = postDao.findAll(pageRequest).getTotalPages();

        if ((page-1) <= 0) {
            model.addAttribute("visibilityPrev", "hidden");
        }

        if ((page) >= pages) {
            model.addAttribute("visibilityNext", "hidden");
        }

        model.addAttribute("newsActiveStatus", "active");
        model.addAttribute("posts", postDao.findAll(pageRequest));
        model.addAttribute("title", "Latest News and Updates - St. John's Lutheran Church");

        model.addAttribute("page", page);

        return "newsitems/news";
    }

    @RequestMapping(value = "new-post", method = RequestMethod.GET)
    public String showNewsForm(Model model) {

        model.addAttribute("title", "Create a News Item - St. John's Lutheran Church");
        model.addAttribute("header", "Create a News Item");
        model.addAttribute(new Post());

        return "newsitems/new-post";
    }

    @RequestMapping(value = "new-post", method = RequestMethod.POST)
    public String handleFormSubmission(@Valid @ModelAttribute Post postToAdd,
                                       Errors errors,
                                       Principal principal,
                                       Model model) {

        if (errors.hasErrors()) {

            model.addAttribute("title", "New Post - St. John's Lutheran Church");
            model.addAttribute("header", "Create a News Post");

            model.addAttribute("findsActiveStatus", "active");

            return "newsitems/new-post";
        }

        String loggedInUser = principal.getName();
        postToAdd.setAuthor(loggedInUser);

        postDao.save(postToAdd);

        return "redirect:";
    }

    @RequestMapping(value = "viewpost/{id}", method = RequestMethod.GET)
    public String viewPost(@PathVariable(value = "id") int id,
                           Model model) {

        Post post = postDao.findOne(id);

        model.addAttribute("title", "View Post - MusicFinds");
        model.addAttribute("post", post);

        model.addAttribute("newsActiveStatus", "active");

        return "newsitems/view-post";
    }

}
