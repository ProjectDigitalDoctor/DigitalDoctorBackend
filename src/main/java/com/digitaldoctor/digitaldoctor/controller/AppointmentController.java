package com.digitaldoctor.digitaldoctor.controller;

import com.digitaldoctor.digitaldoctor.components.TwilioRoom;
import com.digitaldoctor.digitaldoctor.entities.Appointment;
import com.digitaldoctor.digitaldoctor.entities.Doctor;
import com.digitaldoctor.digitaldoctor.entities.Patient;
import com.digitaldoctor.digitaldoctor.repositories.AppointmentRepository;
import com.digitaldoctor.digitaldoctor.repositories.DoctorRepository;
import com.digitaldoctor.digitaldoctor.repositories.PatientRepository;
import com.twilio.twiml.video.Room;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AppointmentNotFoundException extends RuntimeException {
    AppointmentNotFoundException(Long id) {
        super("Could not find appointment with id " + id);
    }
}
class AppointmentNotStartedException extends RuntimeException {
    AppointmentNotStartedException(Long id) {
        super("Appointment with id " + id + " has not yet started");
    }
}

@RestController
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final TwilioRoom twilioRoom;
    private final AuthPatientProvider authPatientProvider;

    @GetMapping("/appointment")
    List<Appointment> getAppointments() {
        Patient patient = authPatientProvider.getLoggedInPatient().orElseThrow(PatientNotFoundException::new);
        return appointmentRepository.findByPatientId(patient.getId());
    }

    @GetMapping("/appointment/{id}")
    Appointment getAppointment(@PathVariable Long id) {
        Patient patient = authPatientProvider.getLoggedInPatient().orElseThrow(PatientNotFoundException::new);
        return appointmentRepository.findByIdAndPatientId(id, patient.getId())
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    @PutMapping("/appointment/{id}")
    Appointment updateAppointment(@RequestBody Appointment updatedAppointment, @PathVariable Long id) {
        Patient patient = authPatientProvider.getLoggedInPatient().orElseThrow(PatientNotFoundException::new);
        return appointmentRepository.findByIdAndPatientId(id, patient.getId())
                .map(appointment -> {
                    appointment.setTimestamp(updatedAppointment.getTimestamp());
                    appointment.setDuration(updatedAppointment.getDuration());
                    return appointmentRepository.save(appointment);
                })
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    @DeleteMapping("/appointment/{id}")
    void deleteAppointment(@PathVariable Long id) {
        Patient patient = authPatientProvider.getLoggedInPatient().orElseThrow(PatientNotFoundException::new);
        appointmentRepository.deleteByIdAndPatientId(id, patient.getId());
    }

    @AllArgsConstructor
    private static class RoomReturn {
        public String roomName;
        public String accessKey;
    }

    @PostMapping("/appointment/{id}/join")
    Object joinAppointment(@PathVariable Long id) {
        Patient patient = authPatientProvider.getLoggedInPatient().orElseThrow(PatientNotFoundException::new);
        Appointment appointment = appointmentRepository.findByIdAndPatientId(id, patient.getId())
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        if (appointment.getVideoRoomName() == null || appointment.getVideoRoomName().isBlank()) {
            throw new AppointmentNotStartedException(id);
        }

        String accessKey = twilioRoom.getAccessKey(appointment.getVideoRoomName(), patient.getId().toString());

        return new RoomReturn(appointment.getVideoRoomName(), accessKey);
    }

    @PostMapping("/appointment/{id}/create-room")
    RoomReturn createAppointmentRoom(@PathVariable Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));

        String roomName = twilioRoom.open();
        appointment.setVideoRoomName(roomName);
        appointmentRepository.save(appointment);

        String accessKey = twilioRoom.getAccessKey(roomName, "doctor");

        return new RoomReturn(roomName, accessKey);
    }

    @AllArgsConstructor
    @ToString
    private static class RequestAppointment {
        public Long patientID;
        public Long doctorID;
        public String reason;
        public String timestamp;
        public Integer duration;
    }

    @PostMapping("/appointment")
    void createAppointment(@RequestBody RequestAppointment requestAppointment) {
        Patient patient = patientRepository.findById(requestAppointment.patientID).orElseThrow();
        Doctor doctor = doctorRepository.findById(requestAppointment.doctorID).orElseThrow();

        Appointment appointment = new Appointment(null, patient, doctor, Timestamp.valueOf(requestAppointment.timestamp), requestAppointment.duration, requestAppointment.reason, null);
        System.out.println("Created " + appointmentRepository.saveAndFlush(appointment));
    }
}
