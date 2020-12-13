package com.oc.mareu.model;

import java.util.Objects;

/**
 * Model object representing a Meeting
 */

public class Meeting {

    /** Hour and minute */
    private String time;

    /** Day */
    private String date;

    /** Topic */
    private String topic;

    /** Emails */
    private String emails;

    /** MeetingRoom object */
    private MeetingRoom mMeetingRooms;

    public Meeting(String time, String date, String topic, String emails, MeetingRoom meetingRooms) {
        this.time = time;
        this.date = date;
        this.topic = topic;
        this.emails = emails;
        this.mMeetingRooms = meetingRooms;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public MeetingRoom getMeetingRooms() {
        return mMeetingRooms;
    }

    public void setMeetingRooms(MeetingRoom meetingRooms) {
        mMeetingRooms = meetingRooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return Objects.equals(hashCode(), meeting.hashCode());
    }



}
