const Video = Twilio.Video;

function createRoom() {
    const appointmentID = document.getElementById("appointment_input").value;
    const url = buildAPIUrl("/appointment/" + appointmentID + "/create-room");
    fetchAndJoin(url);
}

function joinRoom() {
    const appointmentID = document.getElementById("appointment_input").value;
    const url = buildAPIUrl("/appointment/" + appointmentID + "/join");
    fetchAndJoin(url);
}

function buildAPIUrl(path) {
    return window.location.origin + path;
}

function fetchAndJoin(url) {
    fetch(url, {method: "POST"})
        .then(response => response.json())
        .then(data => internalJoin(data.roomName, data.accessKey))
        .catch((error) => console.error('Create Room Error:', error));
}

function addParticipant(participant) {
    participant.tracks.forEach(publication => {
      if (publication.track) {
        document.getElementById('remote-media-div').appendChild(publication.track.attach());
      }
    });

    participant.on('trackSubscribed', track => {
      document.getElementById('remote-media-div').appendChild(track.attach());
    });
}

function internalJoin(roomName, accessKey) {
    console.log(`Join Room with name: ${roomName}`);

    Video.connect(accessKey, { name: roomName }).then(room => {
      console.log(`Successfully joined a Room: ${room}`);

      room.participants.forEach(participant => {
        console.log(`Participant already in room: ${participant}`);
        addParticipant(participant);
      });

      room.on('participantConnected', participant => {
        console.log(`A remote Participant connected: ${participant}`);
        addParticipant(participant);
      });
    }, error => {
      console.error(`Unable to connect to Room: ${error.message}`);
    });
}

