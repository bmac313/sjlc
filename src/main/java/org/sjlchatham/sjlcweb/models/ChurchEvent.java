package org.sjlchatham.sjlcweb.models;

import org.sjlchatham.sjlcweb.enums.ChurchEventType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ChurchEvent {

    // Properties
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 1, max = 200)
    private String name;

    @NotNull
    @Size(min = 1, max = 25)                 // 25 is the max as this is close the the max date format I want (mm/dd/yyyy hh:mm:ss TMZ)
    private String dateTime;                 // The date and time is stored using a 24 hour clock for consistency.

    @NotNull
    @Size(max = 100)
    private ChurchEventType churchEventType;

    // Join to Attendees table
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "event")
    private List<Attendee> attendees;        // Holds a list of registered attendees for the event.

    @NotNull
    @Size(min = 1, max = 1000000)
    private int attendeeCapacity;            // The maximum number of attendees allowed to register for the event.

    // Constructors
    public ChurchEvent(){}

    public ChurchEvent(String name, String dateTime, ChurchEventType churchEventType, ArrayList<Attendee> attendees, int attendeeCapacity) {
        this.name = name;
        this.dateTime = dateTime;
        this.churchEventType = churchEventType;
        this.attendees = attendees;
        this.attendeeCapacity = attendeeCapacity;
    }


    // Getters and Setters
    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

    @Override
    public String toString() {
        return "ChurchEvent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateTime=" + dateTime +
                ", churchEventType=" + churchEventType +
                ", attendees=" + attendees +
                ", attendeeCapacity=" + attendeeCapacity +
                '}';
    }
}
