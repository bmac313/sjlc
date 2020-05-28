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
import org.springframework.validation.Errors;
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
                                       @RequestParam(defaultValue = "false") boolean alertActive,
                                       @RequestParam(defaultValue = "") String alertType,
                                       Model model) {

        // Handle event page alerts;
        if (alertActive) {
            switch (alertType) {
                case "eventRegisterSuccess":
                    model.addAttribute("alertClass", "alert alert-success alert dismissible");
                    model.addAttribute("alert", "The registration was successful. We'll see you there!");
                    break;
                case "eventOverCapacityError":
                    model.addAttribute("alertClass", "alert alert-danger alert-dismissible");
                    model.addAttribute("alert", "This event is full and is no longer open to registration.");
                    break;
                case "eventClosedForRegError":
                    model.addAttribute("alertClass", "alert alert-danger alert-dismissible");
                    model.addAttribute("alert", "This event is currently closed for registration. If you have any questions, please contact the church office.");
                    break;
                case "eventCreateSuccess":
                    model.addAttribute("alertClass", "alert alert-success alert-dismissible");
                    model.addAttribute("alert", "Event created successfully!");
                    break;
                case "eventEditSuccess":
                    model.addAttribute("alertClass", "alert alert-success alert-dismissible");
                    model.addAttribute("alert", "Event edited successfully!");
                    break;
                case "eventDeleteSuccess":
                    model.addAttribute("alertClass", "alert alert-success alert dismissible");
                    model.addAttribute("alert", "Event deleted successfully.");
                    break;
                default:
                    model.addAttribute("alertClass", "");
                    model.addAttribute("alert", "");
                    break;
            }
        } else {
            model.addAttribute("alertClass", "hidden");
        }

        // Get the page request for paging and sorting the ChurchEvents and return pages
        PageRequest pageRequest = new PageRequest(page-1, 10, Sort.Direction.DESC, "eventDate");
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

    @RequestMapping(value = "/register/{id}", method = RequestMethod.GET)
    public String showEventRegPage(@PathVariable(value = "id") int id,
                                   Model model) {

        // Find the ChurchEvent for which the user is registering by ID based on the URL.
        ChurchEvent churchEvent = churchEventDao.findOne(id);

        // Redirect to the events page with an alert if the event's capacity is full or it is closed for registration
        if (churchEvent.getAttendees().size() >= churchEvent.getAttendeeCapacity()) {
            return "redirect:/events?alertActive=true&alertType=eventOverCapacityError";
        } else if (!churchEvent.isOpenForRegistration()){  // If the event is not full, but closed for registration, do the same.
            return "redirect:/events?alertActive=true&alertType=eventClosedForRegError";
        }

        // Set sidebar images
        String[] sideBarImagePaths = {"/img/altar.png", "/img/piano.png", "/img/aisle.png"};

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

    @RequestMapping(value = "/register/{id}", method = RequestMethod.POST)
    public String registerForEvent(@PathVariable(name = "id") int id,
                                   @Valid @ModelAttribute Attendee newAttendee,
                                   Errors errors,
                                   Model model) {

        if (errors.hasErrors()) {

            // Set sidebar images
            String[] sideBarImagePaths = {"/img/altar.png", "/img/piano.png", "/img/aisle.png"};

            // Rebind church event to model
            ChurchEvent churchEvent = churchEventDao.findOne(id);

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

        // Find the event by ID from the URL
        ChurchEvent churchEvent = churchEventDao.findOne(id);

        // Redirect to the events page with an alert if the event's capacity is full or it is closed for registration
        if (churchEvent.getAttendees().size() >= churchEvent.getAttendeeCapacity()) {
            return "redirect:/events?alertActive=true&alertType=eventOverCapacityError";
        } else if (!churchEvent.isOpenForRegistration()){  // If the event is not full, but closed for registration, do the same.
            return "redirect:/events?alertActive=true&alertType=eventClosedForRegError";
        }

        // Store the event in the Event field of the new Attendee
        newAttendee.setEvent(churchEvent);


        // Add the new attendee to the list of attendees for the event.
        churchEvent.getAttendees().add(newAttendee);

        // Save objects in database
        attendeeDao.save(newAttendee);
        churchEventDao.save(churchEvent);

        return "redirect:/events?alertActive=true&alertType=eventRegisterSuccess";
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public String showNewEventForm(Model model) {

        // Add new ChurchEvent object to model
        model.addAttribute(new ChurchEvent());

        // Model attributes
        model.addAttribute("title", "Schedule an Event | St. John's Lutheran Church");
        model.addAttribute("header", "Schedule an Event");

        return "churchevents/new-event";
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.POST)
    public String scheduleNewEvent(@Valid @ModelAttribute ChurchEvent newEvent,
                                   Errors errors,
                                   Model model) {

        if (errors.hasErrors()) {

            // Model attributes
            model.addAttribute("title", "Schedule an Event | St. John's Lutheran Church");
            model.addAttribute("header", "Schedule an Event");

            return "churchevents/new-event";
        }

        churchEventDao.save(newEvent);

        return "redirect:/events?alertActive=true&alertType=eventCreateSuccess";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editChurchEvent(@PathVariable(name = "id") int id,
                                  Model model) {

        // Find event to edit by id
        ChurchEvent churchEvent = churchEventDao.findOne(id);

        // TODO: Create template (churchevents/edit-event) and logic for editing an existing church event.

        return "churchevents/edit-event";
    }

    // For safety, a redirect is declared explicitly. Deleting from the database is a potentially dangerous feature.
    @RequestMapping(value = "deleteevent", method = RequestMethod.GET)
    public String redirectGet() {
        return "redirect:";
    }

    // This path is protected by the security config so that only admins can send requests to it.
    @RequestMapping(value = "deleteevent", method = RequestMethod.POST)
    public String deleteEventById(@RequestParam int churchEventId) {

        // TODO: Consider using the DELETE request method to further protect this as well as News Posts.

        churchEventDao.delete(churchEventId);

        return "redirect:/events?alertActive=true&alertType=eventDeleteSuccess";
    }

}