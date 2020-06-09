package org.sjlchatham.sjlcweb.models;

import org.sjlchatham.sjlcweb.enums.ChurchEventType;
import org.sjlchatham.sjlcweb.helpers.MilitaryStandardTimes;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
public class ChurchEvent {

    // Properties
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private boolean openForRegistration;

    @NotNull
    @Size(min = 1, max = 200, message = "Name must be between 1 and 200 characters.")
    private String name;

    @NotNull
    @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters.")
    private String description;

    @NotNull
    @Size(min= 1, message = "Date is empty or incomplete.")
    private String eventDate;

    @NotNull
    @Size(min= 1, message = "Time is empty or incomplete.")
    private String eventTime;

    @NotNull
    private ChurchEventType churchEventType;

    // Join to Attendees table
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "event")
    private List<Attendee> attendees;        // Holds a list of registered attendees for the event.

    @NotNull
    @Min(value = 1, message = "Attendee capacity cannot be less than 1.")
    @Max(value = 100000, message = "Attendee capacity currently cannot exceed 100,000 attendees.")
    private int attendeeCapacity;            // The maximum number of attendees allowed to register for the event.

    // Constructors
    public ChurchEvent(){
        this.openForRegistration = true;
    }

    public ChurchEvent(String name, String description, String eventDate, String eventTime, ChurchEventType churchEventType, ArrayList<Attendee> attendees, int attendeeCapacity) {
        this.name = name;
        this.description = description;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.churchEventType = churchEventType;
        this.attendees = attendees;
        this.attendeeCapacity = attendeeCapacity;
    }


    // Getters and Setters
    public int getId() {
        return this.id;
    }

    public boolean isOpenForRegistration() {
        return openForRegistration;
    }

    public void setOpenForRegistration(boolean openForRegistration) {
        this.openForRegistration = openForRegistration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventDate() {
        return this.eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public ChurchEventType getChurchEventType() {
        return churchEventType;
    }

    public void setChurchEventType(ChurchEventType churchEventType) {
        this.churchEventType = churchEventType;
    }

    public List<Attendee> getAttendees() {
        return this.attendees;
    }

    public void setAttendees(ArrayList<Attendee> attendees) {
        this.attendees = attendees;
    }

    public int getAttendeeCapacity() {
        return this.attendeeCapacity;
    }

    public void setAttendeeCapacity(int attendeeCapacity) {
        this.attendeeCapacity = attendeeCapacity;
    }

    // Helper Methods
    public String getTimeStamp() {
        return this.eventDate + " @ " + convertMilToStandardTime(this.eventTime) + " CST";
    }

    private String convertMilToStandardTime(String t) {

        // Init AM/PM variable
        String amPM = "";

        // Get the hour in military time (i.e. the two characters before the colon symbol)
        String hourInMil = t.substring(0, t.indexOf(":"));

        // Determine AM/PM of hourInMil (hour is AM if less than or equal to 12:00; PM if greater than 12:00
        if (Integer.parseInt(hourInMil) < 12) {
            amPM = "AM";
        } else if (Integer.parseInt(hourInMil) >= 12) {
            amPM = "PM";
        }

        // Match with the equivalent standard hour in the map, build new string, and return
        return MilitaryStandardTimes.getHours().get(hourInMil)     // Get the equivalent standard hour in the map
                + t.substring(t.indexOf(":"), t.length())          // Get the minutes of the hour
                + " "
                + amPM;                                            // Add AM/PM marker
    }

    // This method returns a string containing the CSS class for an icon corresponding to the event type.
    public String getCssIconClass() {
        switch (this.churchEventType) {
            case CHURCH_SERVICE: return "fas fa-church";
            case CHURCH_ACTIVITY: return "fas fa-bible";
            case FELLOWSHIP: return "fas fa-users";
            case FUNERAL: return "fas fa-cross";
            case MISSION: return "fas fa-globe-africa";
            case VOLUNTEER: return "fas fa-hands-helping";
            case YOUTH: return "fas fa-seedling";
            case OTHER: return "fas fa-icons";
            default: return "fas fa-icons";
        }
    }

    @Override
    public String toString() {
        return "ChurchEvent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", eventDate=" + eventDate +
                ", churchEventType=" + churchEventType +
                ", attendees=" + attendees +
                ", attendeeCapacity=" + attendeeCapacity +
                '}';
    }
}
