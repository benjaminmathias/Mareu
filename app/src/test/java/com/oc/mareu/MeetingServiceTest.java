package com.oc.mareu;

import com.oc.mareu.DI.DI;
import com.oc.mareu.model.Meeting;
import com.oc.mareu.model.MeetingRoom;
import com.oc.mareu.service.DummyMeetingGenerator;
import com.oc.mareu.service.MeetingApiService;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Meeting service
 */
@RunWith(JUnit4.class)
public class MeetingServiceTest {

    private MeetingApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETING;
        MatcherAssert.assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void deleteMeetingsWithSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void addMeetingsWithSuccess() {
        MeetingRoom testRoom = service.getMeetingRooms().get(0);
        Meeting newMeeting = new Meeting("1", "1", "1", "1", testRoom);
        service.addMeeting(newMeeting);
        assertTrue(service.getMeetings().contains(newMeeting));
    }

    @Test
    public void getMeetingRoomsWithSuccess() {
        List<MeetingRoom> meetingRooms = service.getMeetingRooms();
        List<MeetingRoom> expectedMeetingRooms = DummyMeetingGenerator.mMeetingRoom;
        MatcherAssert.assertThat(meetingRooms, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetingRooms.toArray()));
    }

    @Test
    public void filterByDateWithSuccess() {
        String testDate = "20-12-2020";
        MeetingRoom testRoom = service.getMeetingRooms().get(0);
        Meeting meeting = new Meeting("1", "1", "1", "1", testRoom);
        meeting.setDate(testDate);

        service.addMeeting(meeting);
        List<Meeting> filteredList = service.dateFilter(testDate);
        assertEquals(filteredList.size(), 1);
    }

    @Test
    public void filterByMeetingRoomWithSuccess() {
        MeetingRoom filterTestRoom = service.getMeetingRooms().get(5);
        Meeting meeting = new Meeting("1", "1", "1", "1", filterTestRoom);
        service.addMeeting(meeting);

        assertEquals(service.getMeetings().size(), 5);

        List<Meeting> filteredList = service.meetingRoomFilter(filterTestRoom);
        assertTrue(filteredList.contains(meeting));
        assertEquals(filteredList.size(), 1);
    }
}
