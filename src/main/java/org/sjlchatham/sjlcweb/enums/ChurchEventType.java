package org.sjlchatham.sjlcweb.enums;


public enum ChurchEventType {

    CHURCH_SERVICE("Church Service"),
    CHURCH_ACTIVITY("Church Activity"),
    FELLOWSHIP("Fellowship"),
    FUNERAL("Funeral"),
    MISSION("Mission"),
    VOLUNTEER("Volunteer"),
    YOUTH("Youth"),
    OTHER("Other");

    private String displayName;

    ChurchEventType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getPropertyName() {
        return this.name();
    }

    @Override
    public String toString() {
        return this.displayName;
    }

}
