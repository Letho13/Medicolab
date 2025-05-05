package com.medicolab.microservices.patient.service;

import com.medicolab.microservices.patient.model.Patient;
import com.medicolab.microservices.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient findPatientById(Integer id) {
       Patient patient = patientRepository.findById(id)
               .orElseThrow(() -> new NoSuchElementException("Le patient avec l' " + id + " est introuvable !"));;
       return patient;
    }


    public Patient updatePatient(Integer id, Patient updatedPatient) {

        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Le patient avec l' " + id + " est introuvable !"));

        existingPatient.setNom(updatedPatient.getNom());
        existingPatient.setPrenom(updatedPatient.getPrenom());
        existingPatient.setDateDeNaissance(updatedPatient.getDateDeNaissance());
        existingPatient.setGenre(updatedPatient.getGenre());
        existingPatient.setAdresse(updatedPatient.getAdresse());
        existingPatient.setTelephone(updatedPatient.getTelephone());

       return patientRepository.save(existingPatient);

    }

    public void createPatient(Patient patient) {
        patientRepository.save(patient);
    }

    public void deletePatient(Integer id) {
        patientRepository.deleteById(id);
    }

}
