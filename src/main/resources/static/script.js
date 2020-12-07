const Video = Twilio.Video;

const token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImN0eSI6InR3aWxpby1mcGE7dj0xIn0.eyJqdGkiOiJTSzk4N2E0ZDE4ZDI4ODBiYWVjNWM3ZGE2OTE0ZTliOTgzLTE2MDcxMTgxMTkiLCJpc3MiOiJTSzk4N2E0ZDE4ZDI4ODBiYWVjNWM3ZGE2OTE0ZTliOTgzIiwic3ViIjoiQUNmNTAyNTFkMjhkNGJjMTI4MTM0ZDljMjFiZGUzYjNjNyIsImV4cCI6MTYwNzEyMTcxOSwiZ3JhbnRzIjp7ImlkZW50aXR5IjoiTG9jYWwgVGVzdCIsInZpZGVvIjp7fX19.ia57nl2uWPIEc-g39TJ3FBltZOGLSY7bPsuGYNcVL-o";

function joinRoom() {
    const sid = document.getElementById("sip_input").value
    console.log(`Join Room with sid: ${sid}`);

    Video.connect(token, { name: sid }).then(room => {
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

