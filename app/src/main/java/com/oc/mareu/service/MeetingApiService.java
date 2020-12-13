package com.oc.mareu.service;

import com.oc.mareu.model.Meeting;
import com.oc.mareu.model.MeetingRoom;

import java.util.List;

public interface MeetingApiService {

    /**
     * Get all Meetings
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    /**
     * Delete a Meeting
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Add a Meeting
     * @param meeting
     */
    void addMeeting(Meeting meeting);

    /**
     * Get all MeetingRooms
     * @return {@link List}
     */
    List<MeetingRoom> getMeetingRooms();

    /**
     * Get a filtered list
     * @param date
     * @return {@link List}
     */
    List<Meeting> dateFilter(String date);

    /**
     * Get a filtered list
     * @param meetingRoom
     * @return {@link List}
     */
    List<Meeting> meetingRoomFilter(MeetingRoom meetingRoom);


}
