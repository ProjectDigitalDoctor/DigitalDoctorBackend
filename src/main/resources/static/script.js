const Video = Twilio.Video;

function createRoom() {
    const appointmentID = document.getElementById("create_appointment_input").value;
    const url = buildAPIUrl("/appointment/" + appointmentID + "/create-room");
    fetchAndJoin(url);
}

function joinRoom() {
    const appointmentID = document.getElementById("join_appointment_input").value;
    const url = buildAPIUrl("/appointment/" + appointmentID + "/join");
    fetchAndJoin(url);
}

function createAppointment() {
    const body = {
        patientID: document.getElementById("app_patient_input").value,
        doctorID: document.getElementById("app_doctor_input").value,
        reason: document.getElementById("app_reason_input").value,
        timestamp: document.getElementById("app_timestamp_input").value,
        duration: document.getElementById("app_duration_input").value
    };
    const url = buildAPIUrl("/appointment/");
    fetch(url, {
        method: "POST",
        body: JSON.stringify(body),
        headers: {
            'Content-Type': 'application/json'
        },
    }).catch((error) => console.error('Create Appointment:', error));
}

function createPrescription() {
    const body = {
        patientID: document.getElementById("pre_patient_input").value,
        doctorID: document.getElementById("pre_doctor_input").value,
        drugPZN: document.getElementById("pre_drug_input").value,
        usage: document.getElementById("pre_usage_input").value,
        dateOfIssue: document.getElementById("pre_issue_input").value,
        validUntil: document.getElementById("pre_expiry_input").value,
        redeemed: document.getElementById("pre_redeemed_input").checked,
    };
    const url = buildAPIUrl("/prescription/");
    fetch(url, {
        method: "POST",
        body: JSON.stringify(body),
        headers: {
            'Content-Type': 'application/json'
        },
    }).catch((error) => console.error('Create Prescription:', error));
}

function createMedicalCertificate() {
    const body = {
        patientID: document.getElementById("cert_patient_input").value,
        doctorID: document.getElementById("cert_doctor_input").value,
        reason: document.getElementById("cert_reason_input").value,
        dateOfIssue: document.getElementById("cert_issue_input").value,
        validUntil: document.getElementById("cert_expiry_input").value,
    };
    const url = buildAPIUrl("/medical-certificate/");
    fetch(url, {
        method: "POST",
        body: JSON.stringify(body),
        headers: {
            'Content-Type': 'application/json'
        },
    }).catch((error) => console.error('Create Medical Certificate:', error));
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

