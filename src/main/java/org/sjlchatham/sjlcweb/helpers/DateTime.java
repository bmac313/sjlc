package org.sjlchatham.sjlcweb.helpers;

import java.time.LocalDateTime;

// This class contains a collection of helper methods regarding Dates and Times.
// All methods are static and can be called by invoking the class name, followed by the method name.
public abstract class DateTime {

    // CLASS METHODS
    // A simple abstraction that determines whether a LocalDateTime is in the past.
    public static boolean isInPast(LocalDateTime ldt) {
        return ldt.isBefore(LocalDateTime.now());
    }
}
