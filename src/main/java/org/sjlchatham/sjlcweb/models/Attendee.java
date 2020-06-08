// class EventRegistration
//       This class represents an individual registration for an event and holds attendee information.

package org.sjlchatham.sjlcweb.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Attendee {

    //Properties
    @Id
    @GeneratedValue
    private int id;

    private final String regDateTime;     // stores the date and time this registration was made.

    @NotNull
    @Size(min = 1, max = 100, message = "First name must be between 1 and 100.")
    private String firstName;

    @Size(max = 1, message = "Middle initial cannot exceed 1 character.")
    private String mi;

    @NotNull
    @Size(min = 1, max = 100, message = "Last name must be between 1 and 100.")
    private String lastName;

    @Size(min= 1, message = "Please fill out this field.")
    @Pattern(regexp = "^\\w+[-\\w.]*@\\w+((-\\w+)|(\\w*))\\.[a-z]{2,3}$", message = "Please enter a valid email address.")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "church_event_id")
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


    // Helper methods
    @Override
    public String toString() {
        return "Attendee{" +
                "id=" + id +
                ", regDateTime='" + regDateTime + '\'' +
                ", firstName='" + firstName + '\'' +
                ", mi='" + mi + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", event=" + event +
                ", eventId=" + event.getId() +
                '}';
    }
}
