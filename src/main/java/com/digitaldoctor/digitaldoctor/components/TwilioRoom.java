package com.digitaldoctor.digitaldoctor.components;

import com.twilio.jwt.accesstoken.AccessToken;
import com.twilio.jwt.accesstoken.VideoGrant;
import com.twilio.rest.video.v1.Room;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TwilioRoom {
    @Value("${twilio.auth.account_sid}")
    private String accountSid;
    @Value("${twilio.api.key.sid}")
    private String apiKeySid;
    @Value("${twilio.api.key.secret}")
    private String apiKeySecret;

    public String getAccessKey(String roomName, String identity) {
        final VideoGrant grant = new VideoGrant();
        grant.setRoom(roomName);

        // Create an Access Token
        final AccessToken token = new AccessToken.Builder(accountSid, apiKeySid, apiKeySecret)
                .identity(identity) // Set the Identity of this token
                .grant(grant) // Grant access to Video
                .build();

        // Serialize the token as a JWT
        return token.toJwt();
    }

    public String open() {
        Room room = Room.creator()
                .setType(Room.RoomType.GO)
                .setMaxParticipants(2)
                .create();

        return room.getUniqueName();
    }
}