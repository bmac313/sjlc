package org.sjlchatham.sjlcweb.controllers;

import org.sjlchatham.sjlcweb.data.AttendeeDao;
import org.sjlchatham.sjlcweb.data.ChurchEventDao;
import org.sjlchatham.sjlcweb.models.Attendee;
import org.sjlchatham.sjlcweb.models.ChurchEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("events")
public class ChurchEventsController {


    @Autowired
    private ChurchEventDao churchEventDao;

    @Autowired
    private AttendeeDao attendeeDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showChurchEventsPage(@RequestParam(defaultValue = "1") int page,
                                       Model model) {

        // TODO: Event page alerts
        // (insert here)

        // Get the page request for paging and sorting the ChurchEvents and return pages
        PageRequest pageRequest = new PageRequest(page-1, 10, Sort.Direction.DESC, "dateTime");
        int pages = churchEventDao.findAll(pageRequest).getTotalPages();

        // Hide "previous page" link if user is on first page
        if ((page-1) <= 0) {
            model.addAttribute("visibilityPrev", "hidden");
        }

        // Hide the "next page" link if user is on last page
        if ((page) >= pages) {
            model.addAttribute("visibilityNext", "hidden");
        }

        // Query events from database based on page request
        model.addAttribute("churchevents", churchEventDao.findAll(pageRequest));

        // Model attributes
        model.addAttribute("title", "Church Events | St. John's Lutheran Church");
        model.addAttribute("page", page);

        return "churchevents/events";
    }

    @RequestMapping(value = "/{id}/register", method = RequestMethod.GET)
    public String showEventRegPage(@PathVariable(value = "id") int id,
                                   Model model) {

        // Set sidebar images
        String[] sideBarImagePaths = {"/img/altar.png", "/img/piano.png", "/img/aisle.png"};

        // Find the ChurchEvent for which the user is registering by ID based on the URL.
        ChurchEvent churchEvent = churchEventDao.findOne(id);

        // Instantiate the new Attendee object to be filled and added.
        model.addAttribute(new Attendee());

        // Model attributes
        model.addAttribute("churchEvent", churchEvent);
        model.addAttribute("sideBarImagePaths", sideBarImagePaths);
        model.addAttribute("headerImagePath", "/img/altar_wide.png");
        model.addAttribute("title", "SJLC Event Signup Form | St. John's Lutheran Church");
        model.addAttribute("header", "Event Signup");
        model.addAttribute("optionalClass", "alt");
        model.addAttribute("pageBodyText", "event-register-page-body");

        return "page_generic";
    }

    @RequestMapping(value = "/{id}/register", method = RequestMethod.POST)
    public String registerForEvent(@PathVariable(name = "id") int id,
                                   @Valid @ModelAttribute Attendee newAttendee,
                                   Model model) {

        // Find the event by ID from the URL
        ChurchEvent churchEvent = churchEventDao.findOne(id);

        // Store the event in the Event field of the new Attendee
        newAttendee.setEvent(churchEvent);


        // Add the new attendee to the list of attendees for the event.
        churchEvent.getAttendees().add(newAttendee);

        // Save objects in database
        attendeeDao.save(newAttendee);
        churchEventDao.save(churchEvent);

        return "redirect:../../";
    }

}
