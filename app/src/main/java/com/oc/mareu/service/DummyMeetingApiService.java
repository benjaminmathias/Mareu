package com.oc.mareu.service;

import com.oc.mareu.model.Meeting;
import com.oc.mareu.model.MeetingRoom;

import java.util.ArrayList;
import java.util.List;

public class DummyMeetingApiService implements MeetingApiService {


    private List<Meeting> mMeetingList = DummyMeetingGenerator.generateMeetings();
    private List<MeetingRoom> mMeetingRoom = MeetingRoomGenerator.generateMeetingRooms();
    private static List<Meeting> MEETING_LIST = new ArrayList<>();

    @Override
    public List<Meeting> getMeetings() {
        return mMeetingList;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        mMeetingList.remove(meeting);
    }

    @Override
    public void addMeeting(Meeting meeting) {
        mMeetingList.add(meeting);
    }

    public List<MeetingRoom> getMeetingRooms() {
        return mMeetingRoom;
    }

    public List<Meeting> dateFilter(String date) {

       MEETING_LIST = mMeetingList;

        List<Meeting> dateFilterList = new ArrayList<>();

        for (int i = 0; i < DummyMeetingApiService.MEETING_LIST.size(); i++) {
            if ((DummyMeetingApiService.MEETING_LIST.get(i).getDate().equals(date))) {
                dateFilterList.add(DummyMeetingApiService.MEETING_LIST.get(i));
            }
        }

        return dateFilterList;
    }

    @Override
    public List<Meeting> meetingRoomFilter(MeetingRoom meetingRoom) {

        MEETING_LIST = mMeetingList;

        List<Meeting> meetingRoomFilterList = new ArrayList<>();

        for (int i = 0; i < DummyMeetingApiService.MEETING_LIST.size(); i++) {
            if ((DummyMeetingApiService.MEETING_LIST.get(i).getMeetingRooms().equals(meetingRoom))) {
                meetingRoomFilterList.add(DummyMeetingApiService.MEETING_LIST.get(i));
            }
        }

        return meetingRoomFilterList;
    }


}
