package com.digitaldoctor.digitaldoctor.components;

import com.twilio.rest.video.v1.Room;
import org.springframework.stereotype.Component;

@Component
public class TwilioRoom {
    public String open() {
        Room room = Room.creator()
                .setType(Room.RoomType.GO)
                .setMaxParticipants(2)
                .create();

        return room.getUniqueName();
    }
}