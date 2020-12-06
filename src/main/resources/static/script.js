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

function internalJoin(roomName, accessKey) {
    console.log(`Join Room with name: ${roomName}`);

    Video.connect(accessKey, { name: roomName }).then(room => {
      console.log(`Successfully joined a Room: ${room}`);
      room.on('participantConnected', participant => {
        console.log(`A remote Participant connected: ${participant}`);

        participant.tracks.forEach(track => {
          document.getElementById('remote-media-div').appendChild(track.attach());
        });

        participant.on('trackAdded', track => {
          document.getElementById('remote-media-div').appendChild(track.attach());
        });
      });
    }, error => {
      console.error(`Unable to connect to Room: ${error.message}`);
    });
}

