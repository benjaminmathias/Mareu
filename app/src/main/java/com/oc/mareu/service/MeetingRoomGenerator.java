package com.oc.mareu.service;


import com.oc.mareu.model.MeetingRoom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.CYAN;
import static android.graphics.Color.DKGRAY;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.LTGRAY;
import static android.graphics.Color.MAGENTA;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;

public abstract class MeetingRoomGenerator {

    private static List<MeetingRoom> MEETING_ROOMS = Arrays.asList(
            new MeetingRoom("Salle 1", BLACK, "Walter"),
            new MeetingRoom("Salle 2", GREEN, "Jesse"),
            new MeetingRoom("Salle 3", BLUE, "Gus"),
            new MeetingRoom("Salle 4", CYAN, "Saul"),
            new MeetingRoom("Salle 5", RED, "Hank"),
            new MeetingRoom("Salle 6", MAGENTA, "Mike"),
            new MeetingRoom("Salle 7", YELLOW, "Kim"),
            new MeetingRoom("Salle 8", GRAY, "Lalo"),
            new MeetingRoom("Salle 9", LTGRAY, "Nacho"),
            new MeetingRoom("Salle 10", DKGRAY, "Todd")
    );

    static List<MeetingRoom> generateMeetingRooms() {
        return new ArrayList<>(MEETING_ROOMS);
    }
}
