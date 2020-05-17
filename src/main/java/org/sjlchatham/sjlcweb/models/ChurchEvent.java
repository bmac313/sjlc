package org.sjlchatham.sjlcweb.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class ChurchEvent {

    // Properties
    @Id
    @GeneratedValue
    private int id;

    private Date dateTime;

    @OneToMany
    @JoinColumn(name = "churchevent_id", nullable = false)
    private List<Attendee> attendees;  // Holds a list of registered attendees for the event.

    private int attendeeCapacity;           // The maximum number of attendees allowed to register for the event.

    // Constructors
    public ChurchEvent(){}

    public ChurchEvent(Date dateTime, ArrayList<Attendee> attendees, int attendeeCapacity) {
        this.dateTime = dateTime;
        this.attendees = attendees;
        this.attendeeCapacity = attendeeCapacity;
    }


    // Getters and Setters
    public int getId() {
        return this.id;
    }

    public Date getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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

}
