package com.medicolab.microservices.patient.service;

import com.medicolab.microservices.patient.model.PatientRequest;

import com.medicolab.microservices.patient.exception.PatientNotFoundException;
import com.medicolab.microservices.patient.mapper.PatientMapper;
import com.medicolab.microservices.patient.model.Patient;
import com.medicolab.microservices.patient.repository.PatientRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper mapper;

    public PatientService(PatientRepository patientRepository, PatientMapper mapper) {
        this.patientRepository = patientRepository;
        this.mapper = mapper;
        ;
    }

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Patient findPatientById(Integer id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Le patient avec l' " + id + " est introuvable !"));
        ;
        return patient;
    }


    public void updatePatient(PatientRequest request) {

        Patient patient = patientRepository.findById(request.getId())
                .orElseThrow(() -> new PatientNotFoundException(
                        String.format("Le patient %s n'existe pas !", request.getId())
                ));

        mergerPatient(patient, request);
        patientRepository.save(patient);

    }

    private void mergerPatient(Patient patient, PatientRequest request) {

        if (!StringUtils.isBlank(request.getNom())) {
            patient.setNom(request.getNom());
        }
        if (!StringUtils.isBlank(request.getPrenom())) {
            patient.setPrenom(request.getPrenom());
        }
        if (request.getDateDeNaissance() != null) {
            patient.setDateDeNaissance(request.getDateDeNaissance());
        }
        if (!StringUtils.isBlank(request.getGenre())) {
            patient.setGenre(request.getGenre());
        }
        if (request.getAdresse() != null) {
            patient.setAdresse(request.getAdresse());
        }
        if (request.getTelephone() != null) {
            patient.setTelephone(request.getTelephone());
        }

    }

    public Integer createPatient(PatientRequest request) {
        Patient patient = patientRepository.save(mapper.toPatient(request));
        return patient.getId();

    }

    public void deletePatient(Integer id) {
        patientRepository.deleteById(id);
    }

    public boolean patientOverThirty(Integer id) {
        Patient patient = findPatientById(id);
        return patient.getAge() > 30;
    }


}
