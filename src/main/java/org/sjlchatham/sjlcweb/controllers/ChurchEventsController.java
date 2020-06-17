package org.sjlchatham.sjlcweb.controllers;

import org.sjlchatham.sjlcweb.data.AttendeeDao;
import org.sjlchatham.sjlcweb.data.ChurchEventDao;
import org.sjlchatham.sjlcweb.helpers.Alert;
import org.sjlchatham.sjlcweb.helpers.DateTime;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

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
                                       @RequestParam(defaultValue = "-1") int eventId,
                                       @RequestParam(defaultValue = "") String eventName,
                                       Model model) {

        // If alerts are set to active, create page alert and add to model
        if (alertActive) {
            Alert alert = new Alert(alertType);
            model.addAttribute("alertClass", alert.getCssClass());
            try {
                model.addAttribute("alert", alert.getAlertTextForEvent(churchEventDao.findOne(eventId)));
            } catch (NoSuchElementException | NullPointerException ex) {
                model.addAttribute("alert", alert.createDeleteText(eventName));
            }
        } else {
            model.addAttribute("alertClass", "hidden");
        }

        // Check to see if any events have passed; if so, close them to registration.
        closePastEvents();

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
        model.addAttribute("header", "Church Events");
        model.addAttribute("newsActiveStatus", "active");
        model.addAttribute("page", page);

        return "churchevents/events";
    }

    @RequestMapping(value = "/register/{id}", method = RequestMethod.GET)
    public String showEventRegPage(@PathVariable(value = "id") @ModelAttribute int id,
                                   Model model) {

        // Find the ChurchEvent for which the user is registering by ID based on the URL.
        ChurchEvent churchEvent = churchEventDao.findOne(id);

        /* closeEventIfPast() is run here instead of closePastEvents(), and only if the event
         * is still open. This is done in order to conserve system resources.*/
        if (churchEvent.isOpenForRegistration()) closeEventIfPast(id);

        // Redirect to the events page with an alert if the event's capacity is full or it is closed for registration
        if (churchEvent.getAttendees().size() >= churchEvent.getAttendeeCapacity()) {
            return "redirect:/events/viewevent/{id}?alertActive=true&alertType=eventOverCapacityError&eventId=" + churchEvent.getId();
        } else if (!churchEvent.isOpenForRegistration()){  // If the event is not full, but closed for registration, do the same.
            return "redirect:/events/viewevent/{id}?alertActive=true&alertType=eventClosedForRegError&eventId=" + churchEvent.getId();
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
        model.addAttribute("newsActiveStatus", "active");
        model.addAttribute("pageBodyText", "event-register-page-body");

        return "page_generic";
    }

    @RequestMapping(value = "/register/{id}", method = RequestMethod.POST)
    public String registerForEvent(@PathVariable(name = "id") @ModelAttribute int id,
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
            model.addAttribute("newsActiveStatus", "active");
            model.addAttribute("pageBodyText", "event-register-page-body");

            return "page_generic";
        }

        // Find the event by ID from the URL
        ChurchEvent churchEvent = churchEventDao.findOne(id);

        /* closeEventIfPast() is run here instead of closePastEvents(), and only if the event
         * is still open. This is done in order to conserve system resources.*/
        if (churchEvent.isOpenForRegistration()) closeEventIfPast(id);

        // Redirect to the events page with an alert if the event's capacity is full or it is closed for registration
        if (churchEvent.getAttendees().size() >= churchEvent.getAttendeeCapacity()) {
            return "redirect:/events/viewevent/{id}?alertActive=true&alertType=eventOverCapacityError&eventId=" + id;
        } else if (!churchEvent.isOpenForRegistration()){  // If the event is not full, but closed for registration, do the same.
            return "redirect:/events/viewevent/{id}?alertActive=true&alertType=eventClosedForRegError&eventId=" + id;
        }

        // Get the list of attendees
        List<Attendee> attendees = churchEvent.getAttendees();

        /* Check if an attendee with the same firstName, lastName, mi, suffix, and email
         * already exists in the attendee list. If so, reject signup and redirect to the event
         * info page with an error.
         */
        if (isDuplicateRegistration(newAttendee, id)){
            return "redirect:/events/viewevent/{id}?alertActive=true&alertType=duplicateSignupError";
        }

        // Store the event in the Event field of the new Attendee
        newAttendee.setEvent(churchEvent);

        // Add the new attendee to the list of attendees for the event.
        attendees.add(newAttendee);

        // Save objects in database
        attendeeDao.save(newAttendee);
        churchEventDao.save(churchEvent);

        return "redirect:/events?alertActive=true&alertType=eventRegisterSuccess&eventId=" + id;
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public String showNewEventForm(Model model) {

        // Add new ChurchEvent object to model
        model.addAttribute(new ChurchEvent());

        // Model attributes
        model.addAttribute("title", "Schedule an Event | St. John's Lutheran Church");
        model.addAttribute("header", "Schedule an Event");
        model.addAttribute("newsActiveStatus", "active");

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
            model.addAttribute("newsActiveStatus", "active");

            return "churchevents/new-event";
        }

        // Save the event
        churchEventDao.save(newEvent);

        return "redirect:/events?alertActive=true&alertType=eventCreateSuccess&eventId=" + newEvent.getId();
    }

    @RequestMapping(value = "/viewevent/{id}", method = RequestMethod.GET)
    public String viewEventPage(@PathVariable(name = "id") @ModelAttribute int id,
                                @RequestParam(defaultValue = "false") boolean alertActive,
                                @RequestParam(defaultValue = "") String alertType,
                                @RequestParam(name = "attendeeModalActive", defaultValue = "false") boolean attendeeModalActive,
                                Model model) {

        // Create alert if set to active
        if (alertActive) {
            Alert alert = new Alert(alertType);
            model.addAttribute("alertClass", alert.getCssClass());
            model.addAttribute("alert", alert.getAlertTextForEvent(churchEventDao.findOne(id)));
        }

        /* closeEventIfPast() is run here instead of closePastEvents(), and only if the event
         * is still open. This is done in order to conserve system resources.*/
        if (churchEventDao.findOne(id).isOpenForRegistration()) closeEventIfPast(id);

        // Open attendee modal if activated
        model.addAttribute("attendeeModalActive", attendeeModalActive);

        // Look up list of attendees that have an event.id equal to the PathVariable (event) id; add to model
        model.addAttribute("attendeesForEvent", attendeeDao.findByEventIdOrderByLastNameAscFirstNameAscSuffixAscMiAscEmailAsc(id));

        // Set register button to disabled if the event is closed.
        if (!churchEventDao.findOne(id).isOpenForRegistration())
            model.addAttribute("isDisabled", "disabled");

        // Model attributes
        model.addAttribute("title", "Event Details | St John's Lutheran Church");
        model.addAttribute("newsActiveStatus", "active");
        model.addAttribute(churchEventDao.findOne(id));

        return "churchevents/view-event";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String showEditEventForm(@PathVariable(name = "id") @ModelAttribute int id,
                                  Model model) {

        // Find event to edit by id
        model.addAttribute(churchEventDao.findOne(id));

        // Model attributes
        model.addAttribute("title", "Edit Event | St. John's Lutheran Church");
        model.addAttribute("newsActiveStatus", "active");
        model.addAttribute("header", "Edit Event");

        return "churchevents/edit-event";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editChurchEvent(@PathVariable(name = "id") @ModelAttribute int id,
                                  @Valid @ModelAttribute ChurchEvent editedEvent,
                                  Errors errors,
                                  Model model) {

        ChurchEvent eventToEdit = churchEventDao.findOne(id);

        // Check for JPA validation errors and custom errors
        if (errors.hasErrors()) {

            // Model attributes
            model.addAttribute("title", "Edit Event | St. John's Lutheran Church");
            model.addAttribute("header", "Edit Event");
            model.addAttribute("newsActiveStatus", "active");

            // Check for custom errors in addition to JPA
            if (!attendeeCapacityValid(editedEvent, eventToEdit))
                model.addAttribute("invalidCapacityError", "The attendee capacity cannot be less than the number of registered attendees.");

            return "churchevents/edit-event";
        }
        // If no JPA errors, check for custom error alone.
        if (!attendeeCapacityValid(editedEvent, eventToEdit)) {

            // Model attributes
            model.addAttribute("title", "Edit Event | St. John's Lutheran Church");
            model.addAttribute("header", "Edit Event");
            model.addAttribute("newsActiveStatus", "active");
            model.addAttribute("invalidCapacityError", "The attendee capacity cannot be less than the number of registered attendees.");

            return "churchevents/edit-event";
        }

        eventToEdit.setOpenForRegistration(editedEvent.isOpenForRegistration());
        eventToEdit.setName(editedEvent.getName());
        eventToEdit.setDescription(editedEvent.getDescription());
        eventToEdit.setEventDate(editedEvent.getEventDate());
        eventToEdit.setEventTime(editedEvent.getEventTime());
        eventToEdit.setChurchEventType(editedEvent.getChurchEventType());
        eventToEdit.setAttendeeCapacity(editedEvent.getAttendeeCapacity());

        churchEventDao.save(eventToEdit);

        return "redirect:/events/viewevent/{id}?alertActive=true&alertType=eventEditSuccess&eventId=" + id;
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

    // HELPER METHODS

    /* This method takes in an event ID and an attendee to be registered for that event,
     * then returns true or false based on whether an attendee with the same firstName, lastName,
     * mi, suffix, and email exists for that event.
     */
    private boolean isDuplicateRegistration(Attendee newAttendee, int eventId) {
        try {
            Attendee attendeeLookup =
                    attendeeDao.findByEventIdAndLastNameAndFirstNameAndMiAndSuffixAndEmail(
                            eventId,
                            newAttendee.getLastName(),
                            newAttendee.getFirstName(),
                            newAttendee.getMi(),
                            newAttendee.getSuffix(),
                            newAttendee.getEmail()
                    );

            System.out.println("Attendee already exists: " + attendeeLookup.getId());
            return true;
        } catch (NoSuchElementException | NullPointerException ex) {
            System.out.println("Attendee not found. Created new entry for Attendee.");
            return false;
        }
    }

    /* This method takes the original and edited ChurchEvents and returns whether the latter's
     * attendeeCapacity is valid. A valid attendee capacity is one that does not exceed the
     * current number of registered attendees.
     */
    private boolean attendeeCapacityValid(ChurchEvent editedEvent, ChurchEvent origEvent) {
        return editedEvent.getAttendeeCapacity() >= origEvent.getAttendees().size();
    }

    /* This method iterates through all events. If an event's date/time is earlier than
     * LocalDateTime.now, it sets the event's openForRegistration property to false.
     */
    private void closePastEvents() {

        // Init LDT variable to be assigned
        LocalDateTime eventDateTime;

        // Query all church events and iterate through them.
        try {
            for (ChurchEvent event : churchEventDao.findAll()) {

                eventDateTime = LocalDateTime.parse(event.getEventDate() + "T" + event.getEventTime());

                // Check whether each event's date is before the current time
                // If match is found, close to event to new attendees, and save the event
                if (DateTime.isInPast(eventDateTime)) {
                    event.setOpenForRegistration(false);
                    churchEventDao.save(event);
                }
            }
        } catch (NoSuchElementException | NullPointerException ex) {
            System.out.println("No events found for method closePastEvents()");
        }
    }

    /* This method takes a ChurchEvent as a parameter. If the event's date/time is earlier than
     * LocalDateTime.now, it sets the event's openForRegistration property to false.
     */
    private void closeEventIfPast(int eventId) {

        // Find the event by id
        ChurchEvent event = churchEventDao.findOne(eventId);

        // Parse LDT
        LocalDateTime eventDateTime = LocalDateTime.parse(
                event.getEventDate() + "T" + event.getEventTime()
        );

        // Check whether each event's date is before the current time
        // If match is found, close to event to new attendees, and save the event
        if (DateTime.isInPast(eventDateTime)) {
            event.setOpenForRegistration(false);
            churchEventDao.save(event);
        }
    }

}
