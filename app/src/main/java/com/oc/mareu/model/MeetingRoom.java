package com.oc.mareu.model;


import java.util.Objects;

/**
 * Model object representing a MeetingRoom
 */
public class MeetingRoom {

    /**
     * Name of the room
     */
    private String name;

    /**
     * Color
     */
    private int backgroundColor;

    /**
     * Displayed name
     */
    private String displayedName;

    public MeetingRoom(String name, int backgroundColor, String displayedName) {
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.displayedName = displayedName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String displayedName) {
        this.displayedName = displayedName;
    }

    // Used for Spinner
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingRoom that = (MeetingRoom) o;
        return backgroundColor == that.backgroundColor &&
                Objects.equals(name, that.name) &&
                Objects.equals(displayedName, that.displayedName);
    }

}
