package com.oc.mareu.service;

import com.oc.mareu.model.Meeting;
import com.oc.mareu.model.MeetingRoom;

import java.util.List;

public interface MeetingApiService {

    List<Meeting> getMeetings();

    void deleteMeeting(Meeting meeting);

    void addMeeting(Meeting meeting);

    List<MeetingRoom> getMeetingRooms();

    List<Meeting> dateFilter(String date);

    List<Meeting> meetingRoomFilter(MeetingRoom meetingRoom);


}
