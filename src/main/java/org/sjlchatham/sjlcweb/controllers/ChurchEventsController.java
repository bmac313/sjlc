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
import java.util.List;

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
                                       @RequestParam(defaultValue = "") String eventName,
                                       Model model) {

        // Handle event page alerts;
        if (alertActive) {
            switch (alertType) {
                case "eventRegisterSuccess":
                    model.addAttribute("alertClass", "alert alert-success alert dismissible");
                    model.addAttribute("alert", "Your registration for <strong>" + eventName + "</strong> was successful. We'll see you there!");
                    break;
                case "eventOverCapacityError":
                    model.addAttribute("alertClass", "alert alert-danger alert-dismissible");
                    model.addAttribute("alert", "<strong>" + eventName + "</strong> is full and is no longer open to registration.");
                    break;
                case "eventClosedForRegError":
                    model.addAttribute("alertClass", "alert alert-danger alert-dismissible");
                    model.addAttribute("alert", "<strong>" + eventName + "</strong> is currently closed for registration. If you have any questions, please contact the church office.");
                    break;
                case "eventCreateSuccess":
                    model.addAttribute("alertClass", "alert alert-success alert-dismissible");
                    model.addAttribute("alert", "Event <strong>" + eventName + "</strong> created successfully!");
                    break;
                case "eventEditSuccess":
                    model.addAttribute("alertClass", "alert alert-success alert-dismissible");
                    model.addAttribute("alert", "Event <strong>" + eventName + "</strong> edited successfully!");
                    break;
                case "eventDeleteSuccess":
                    model.addAttribute("alertClass", "alert alert-success alert dismissible");
                    model.addAttribute("alert", "Event <strong>" + eventName + "</strong> deleted successfully.");
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
            return "redirect:/events?alertActive=true&alertType=eventOverCapacityError&eventName=" + churchEvent.getName();
        } else if (!churchEvent.isOpenForRegistration()){  // If the event is not full, but closed for registration, do the same.
            return "redirect:/events?alertActive=true&alertType=eventClosedForRegError&eventName=" + churchEvent.getName();
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
            return "redirect:/events?alertActive=true&alertType=eventOverCapacityError&eventName=" + churchEvent.getName();
        } else if (!churchEvent.isOpenForRegistration()){  // If the event is not full, but closed for registration, do the same.
            return "redirect:/events?alertActive=true&alertType=eventClosedForRegError&eventName=" + churchEvent.getName();
        }

        // Get the list of attendees
        List<Attendee> attendees = churchEvent.getAttendees();

        // Store the event in the Event field of the new Attendee
        newAttendee.setEvent(churchEvent);

        // Add the new attendee to the list of attendees for the event.
        attendees.add(newAttendee);

        // TODO: Sort isn't working, fix
        // Sort the attendee list by last name
        attendees.sort(Attendee::compareTo);

        // Save objects in database
        attendeeDao.save(newAttendee);
        churchEventDao.save(churchEvent);

        return "redirect:/events?alertActive=true&alertType=eventRegisterSuccess&eventName=" + churchEvent.getName();
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

        // Save the event
        churchEventDao.save(newEvent);

        return "redirect:/events?alertActive=true&alertType=eventCreateSuccess&eventName=" + newEvent.getName();
    }

    @RequestMapping(value = "/viewevent/{id}", method = RequestMethod.GET)
    public String viewEventPage(@PathVariable(name = "id") int id,
                                @RequestParam(defaultValue = "false") boolean alertActive,
                                @RequestParam(defaultValue = "") String alertType,
                                @RequestParam(name = "attendeeModalActive", defaultValue = "false") boolean attendeeModalActive,
                                Model model) {

        // Get the event + event name
        ChurchEvent churchEvent = churchEventDao.findOne(id);
        String eventName = churchEvent.getName();

        // Page alerts
        if (alertActive) {
            switch (alertType) {
                case "eventOverCapacityError":
                    model.addAttribute("alertClass", "alert alert-danger alert-dismissible");
                    model.addAttribute("alert", "<strong>" + eventName + "</strong> is full and is no longer open to registration.");
                    break;
                case "eventClosedForRegError":
                    model.addAttribute("alertClass", "alert alert-danger alert-dismissible");
                    model.addAttribute("alert", "<strong>" + eventName + "</strong> is currently closed for registration. If you have any questions, please contact the church office.");
                    break;
                default:
                    model.addAttribute("alertClass", "");
                    model.addAttribute("alert", "");
                    break;
            }
        }

        // Open attendee modal if activated
        model.addAttribute("attendeeModalActive", attendeeModalActive);

        // Model attributes
        model.addAttribute("title", "Event Details | St John's Lutheran Church");
        model.addAttribute(churchEventDao.findOne(id));
        model.addAttribute("id", id);

        return "churchevents/view-event";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String showEditEventForm(@PathVariable(name = "id") int id,
                                  Model model) {

        // Find event to edit by id
        model.addAttribute(churchEventDao.findOne(id));

        // Model attributes
        model.addAttribute("title", "Edit Event | St. John's Lutheran Church");
        model.addAttribute("header", "Edit Event");

        return "churchevents/edit-event";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editChurchEvent(@PathVariable(name = "id") int id,
                                  @Valid @ModelAttribute ChurchEvent editedEvent,
                                  Errors errors,
                                  Model model) {

        if (errors.hasErrors()) {

            // Model attributes
            model.addAttribute("title", "Edit Event | St. John's Lutheran Church");
            model.addAttribute("header", "Edit Event");

            return "churchevents/edit-event";
        }

        ChurchEvent eventToEdit = churchEventDao.findOne(id);

        eventToEdit.setOpenForRegistration(editedEvent.isOpenForRegistration());
        eventToEdit.setName(editedEvent.getName());
        eventToEdit.setDescription(editedEvent.getDescription());
        eventToEdit.setEventDate(editedEvent.getEventDate());
        eventToEdit.setEventTime(editedEvent.getEventTime());
        eventToEdit.setChurchEventType(editedEvent.getChurchEventType());
        eventToEdit.setAttendeeCapacity(editedEvent.getAttendeeCapacity());

        churchEventDao.save(eventToEdit);

        return "redirect:/events/viewevent/{id}";
    }

    // For safety, a redirect is declared explicitly. Deleting from the database is a potentially dangerous feature.
    @RequestMapping(value = "removeattendee", method = RequestMethod.GET)
    public String redirectGetRemoveAttendee() {
        return "redirect:";
    }

    // This path is protected by the security config so that only admins can send requests to it.
    @RequestMapping(value = "removeattendee", method = RequestMethod.POST)
    public String removeAttendeeById(@RequestParam int attendeeListIdx,
                                     @RequestParam int attendeeId,
                                     @RequestParam int eventId) {

        // Get event and attendee list
        ChurchEvent churchEvent = churchEventDao.findOne(eventId);
        List<Attendee> attendees = churchEvent.getAttendees();

        // Remove attendee from list at provided index
        attendees.remove(attendeeListIdx);

        // Delete the attendee from the database
        attendeeDao.delete(attendeeDao.findOne(attendeeId));

        // Save the event object
        churchEventDao.save(churchEvent);

        return "redirect:/events/viewevent/" + eventId + "?attendeeModalActive=true";
    }

    // For safety, a redirect is declared explicitly. Deleting from the database is a potentially dangerous feature.
    @RequestMapping(value = "deleteevent", method = RequestMethod.GET)
    public String redirectGetDeleteEvent() {
        return "redirect:";
    }

    // This path is protected by the security config so that only admins can send requests to it.
    @RequestMapping(value = "deleteevent", method = RequestMethod.POST)
    public String deleteEventById(@RequestParam int id) {

        String eventName = churchEventDao.findOne(id).getName();
        churchEventDao.delete(churchEventDao.findOne(id));

        return "redirect:/events?alertActive=true&alertType=eventDeleteSuccess&eventName=" + eventName;
    }

}
