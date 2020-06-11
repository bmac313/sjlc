package org.sjlchatham.sjlcweb.helpers;

import org.sjlchatham.sjlcweb.models.ChurchEvent;

public class Alert {

    private String type;

    public Alert(String type) {
        this.type = type;
    }

    // GETTERS and SETTERS
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // CLASS METHODS

    // Returns a Bootstrap CSS class based on the EventType
    public String getCssClass() {
        switch (this.type) {
            case "eventRegisterSuccess":
            case "eventCreateSuccess":
            case "eventEditSuccess":
            case "eventDeleteSuccess":
                return "alert alert-success alert dismissible";
            case "eventOverCapacityError":
            case "eventClosedForRegError":
            case "duplicateSignupError":
                return "alert alert-danger alert-dismissible";
            default: return "";
        }
    }

    // Returns an UN-ESCAPED alert message based on the EventType.
    // Use this method for alert messages WITHOUT the Event Name.
    public String getAlertTextGeneric() {
        switch (this.type) {
            case "eventRegisterSuccess":
                return "Your registration was successful. We'll see you there!";
            case "eventCreateSuccess":
                return "Event created successfully!";
            case "eventEditSuccess":
                return "Event edited successfully!";
            case "eventDeleteSuccess":
                return "Event deleted successfully.";
            case "eventOverCapacityError":
                return "The event for which you are registering is full and is no longer open to registration.";
            case "eventClosedForRegError":
                return "The event for which you are registering is currently closed for registration. If you have any questions, please contact the church office at <strong><a href='mailto:stjohnsoffice@comcast.net'> stjohnsoffice@comcast.net</a></strong>.";
            case "duplicateSignupError":
                return "There is already a registration on file for that event matching the information you entered. Please check the list of attendees or try again.";
            default:
                return "";
        }
    }

    // Returns an UN-ESCAPED alert message based on the EventType including the Event Name
    // Takes a ChurchEvent as a parameter.
    // Use this method for event-related alert messages that INCLUDE the Event Name.
    public String getAlertTextForEvent(ChurchEvent event) {
        switch (this.type) {
            case "eventRegisterSuccess":
                return "Your registration for <strong>" + event.getName() + "</strong> was successful. We'll see you there!";
            case "eventCreateSuccess":
                return "Event <strong>" + event.getName() + "</strong> created successfully!";
            case "eventEditSuccess":
                return "Event <strong>" + event.getName() + "</strong> edited successfully!";
            case "eventDeleteSuccess":
                return "Event <strong>" + event.getName() + "</strong> deleted successfully.";
            case "eventOverCapacityError":
                return "<strong>" + event.getName() + "</strong> is full and is no longer open to registration.";
            case "eventClosedForRegError":
                return "<strong>" + event.getName() + "</strong> is currently closed for registration. If you have any questions, please contact the church office at <strong><a href='mailto:stjohnsoffice@comcast.net'> stjohnsoffice@comcast.net</a></strong>.";
            case "duplicateSignupError":
                return "There is already a registration on file for <strong>" + event.getName() + "</strong> matching the information you entered. Please check the list of attendees or try again.";
            default:
                return "";
        }
    }

    // HELPER METHODS
    @Override
    public String toString() {
        return "Alert{" +
                ", type=" + type +
                '}';
    }
}
