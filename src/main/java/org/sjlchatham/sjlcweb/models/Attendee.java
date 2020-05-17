// class EventRegistration
//       This class represents an individual registration for an event and holds attendee information.

package org.sjlchatham.sjlcweb.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Attendee {

    //Properties
    @Id
    @GeneratedValue
    private int id;

    private Date regDateTime;     // stores the date and time this registration was made.

    private String firstName;
    private String mi;
    private String lastName;
    private String email;

    @ManyToOne
    @JoinColumn(name = "attendee_id", nullable = false)
    private ChurchEvent event;     // stores the event corresponding to this registration

    //Constructors
    public Attendee(){}

    public Attendee(Date regDateTime, String firstName, String mi, String lastName, String email, ChurchEvent event) {
        this.regDateTime = regDateTime;
        this.firstName = firstName;
        this.mi = mi;
        this.lastName = lastName;
        this.email = email;
        this.event = event;
    }

    public int getId() {
        return id;
    }

    public Date getRegDateTime() {
        return regDateTime;
    }

    public void setRegDateTime(Date regDateTime) {
        this.regDateTime = regDateTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMi() {
        return mi;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ChurchEvent getEvent() {
        return event;
    }

    public void setEvent(ChurchEvent event) {
        this.event = event;
    }
}
