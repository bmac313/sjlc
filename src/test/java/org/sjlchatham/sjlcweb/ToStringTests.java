package org.sjlchatham.sjlcweb;

import org.junit.Test;
import org.sjlchatham.sjlcweb.enums.ChurchEventType;
import org.sjlchatham.sjlcweb.models.*;

import java.util.ArrayList;
import java.util.List;

public class ToStringTests {

    @Test
    public void eventToString() {
        System.out.println(new ChurchEvent(
                "Test", "testestesest", "2020-06-11", "14:09", ChurchEventType.CHURCH_ACTIVITY, new ArrayList<>(), 10
        ).toString());
    }

    @Test
    public void attendeeToString() {
        System.out.println(new Attendee(
                "Robert", "J", "Downey", "Jr", "rdj@gmail.com", new ChurchEvent()
        ).toString());
    }

    @Test
    public void authoritiesToString() {
        System.out.println(new Authorities(
                new User("random", "1234", "random@gmail.com", true),
                "ROLE_ADMIN"
        ).toString());
    }

    @Test
    public void postToString() {
        System.out.println(new Post(
                "Test", "random", "FSDAKDF393439kfddkkjfff", "i.imgur.com/random.png"
        ).toString());
    }

    @Test
    public void userToString() {
        System.out.println(new User(
                "random", "1234", "random@gmail.com", true
        ).toString());
    }

    @Test
    public void pwResetTokenToString() {
        System.out.println(new PasswordResetToken(
                new User("random", "1234", "random@gmail.com", true),
                "token"
        ));
    }

}
