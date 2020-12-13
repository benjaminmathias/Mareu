package com.oc.mareu.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class MeetingRoom {

    private String name;

    private int backgroundColor;

    private String displayedName;

    public MeetingRoom() {

    }

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
