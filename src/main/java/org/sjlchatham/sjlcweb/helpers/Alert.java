package org.sjlchatham.sjlcweb.helpers;

import org.sjlchatham.sjlcweb.models.ChurchEvent;
import org.sjlchatham.sjlcweb.models.Post;

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

    // Returns an UN-ESCAPED alert message based on the Alert Type.
    // Use this method for alert messages WITHOUT the Event or Post Name.
    public String getAlertTextGeneric() {
        switch (this.type) {
            case "postCreateSuccess":
                return "The post was created successfully!";
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

    // Returns an UN-ESCAPED alert message based on the Alert Type including the Post Name
    // Takes a Post as a parameter.
    // Use this method for event-related alert messages that INCLUDE the Post Name.
    public String getAlertTextForPost(Post p) {
        switch (this.type) {
            case "postCreateSuccess":
                return "Post <strong>" + p.getTitle() + "</strong> was created successfully!";
            case "postEditSuccess":
                return "Post <strong>" + p.getTitle() + "</strong> was edited successfully!";
            case "postDeleteSuccess":
                return "Post <strong>" + p.getTitle() + "</strong> was deleted successfully.";
            default:
                return "";
        }
    }

    // Returns an UN-ESCAPED alert message based on the Alert Type including the Event Name
    // Takes a ChurchEvent as a parameter.
    // Use this method for event-related alert messages that INCLUDE the Event Name.
    public String getAlertTextForEvent(ChurchEvent e) {
        switch (this.type) {
            case "eventRegisterSuccess":
                return "Your registration for <strong>" + e.getName() + "</strong> was successful. We'll see you there!";
            case "eventCreateSuccess":
                return "Event <strong>" + e.getName() + "</strong> created successfully!";
            case "eventEditSuccess":
                return "Event <strong>" + e.getName() + "</strong> edited successfully!";
            case  "eventDeleteSuccess":
                return "Event <strong>" + e.getName() + "</strong> deleted successfully.";
            case "eventOverCapacityError":
                return "<strong>" + e.getName() + "</strong> is full and is no longer open to registration.";
            case "eventClosedForRegError":
                return "<strong>" + e.getName() + "</strong> is currently closed for registration. If you have any questions, please contact the church office at <strong><a href='mailto:stjohnsoffice@comcast.net'> stjohnsoffice@comcast.net</a></strong>.";
            case "duplicateSignupError":
                return "There is already a registration on file for <strong>" + e.getName() + "</strong> matching the information you entered. Please check the list of attendees or try again.";
            default:
                return "";
        }
    }

    // Returns the alert text for an event that has been deleted and can no longer be accessed.
    // Takes in an objectName (the name of a ChurchEvent or Post) as a String
    // You MUST store the event name in a String BEFORE event deletion, then pass the String as
    // a @RequestParam.
    public String createDeleteText(String objectName) {
        switch (this.type) {
            case "eventDeleteSuccess":
                return "Event <strong>" + objectName + "</strong> deleted successfully.";
            case "postDeleteSuccess":
                return "Post <strong>" + objectName + "</strong> deleted successfully.";
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
