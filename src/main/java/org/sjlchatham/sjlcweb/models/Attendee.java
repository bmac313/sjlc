// class EventRegistration
//       This class represents an individual registration for an event and holds attendee information.

package org.sjlchatham.sjlcweb.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Attendee {

    //Properties
    @Id
    @GeneratedValue
    private int id;

    private final String regDateTime;     // stores the date and time this registration was made.

    private String firstName;
    private String mi;
    private String lastName;
    private String email;

    @ManyToOne
    @JoinColumn(name = "attendee_id", nullable = false)
    private ChurchEvent event;     // stores the event corresponding to this registration

    //Constructors
    public Attendee(){
        this.regDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    public Attendee(String firstName, String mi, String lastName, String email, ChurchEvent event) {
        this();
        this.firstName = firstName;
        this.mi = mi;
        this.lastName = lastName;
        this.email = email;
        this.event = event;
    }

    public int getId() {
        return id;
    }

    public String getRegDateTime() {
        return regDateTime;
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