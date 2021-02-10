package com.digitaldoctor.digitaldoctor.components;

import com.digitaldoctor.digitaldoctor.entities.Patient;
import com.digitaldoctor.digitaldoctor.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PatientAuthDetailsService implements UserDetailsService {
    private final PatientRepository patientRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Patient patient = patientRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return buildUserForAuthentication(patient);
    }

    private UserDetails buildUserForAuthentication(Patient patient) {
        return new User(patient.getUsername(), patient.getPassword(),
                true, true, true, true, new ArrayList<>());
    }
}
