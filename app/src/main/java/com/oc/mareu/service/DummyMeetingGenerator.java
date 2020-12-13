package com.oc.mareu.service;

import com.oc.mareu.model.Meeting;
import com.oc.mareu.model.MeetingRoom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DummyMeetingGenerator {

    public static List<MeetingRoom> mMeetingRoom = MeetingRoomGenerator.generateMeetingRooms();

    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
            new Meeting(" 9:30", "18-12-2020", "Réunion A", "walterwhite@albuquerque.edu", mMeetingRoom.get(0)),
            new Meeting(" 10:30", "18-12-2020", "Réunion B", "jessepinkman@notsuspiciousmail.com", mMeetingRoom.get(1)),
            new Meeting(" 11:30", "18-12-2020", "Réunion C", "gusfring@lospolloshermanos.com", mMeetingRoom.get(2)),
            new Meeting(" 14:30", "18-12-2020", "Réunion D", "saulgoodman@attorneyatlaw.com", mMeetingRoom.get(3))
    );

    static List<Meeting> generateMeetings(){
        return new ArrayList<>(DUMMY_MEETING);
    }
}
