package com.medicolab.microservices.patient.mapper;

import com.medicolab.microservices.patient.PatientRequest;
import com.medicolab.microservices.patient.model.Patient;
import org.springframework.stereotype.Service;

@Service
public class PatientMapper {

    public Patient toPatient(PatientRequest request) {

        if (request == null) {
            return null;
        }
        Patient patient = new Patient();
        patient.setId(request.getId());
        patient.setNom(request.getNom());
        patient.setPrenom(request.getPrenom());
        patient.setDateDeNaissance(request.getDateDeNaissance());
        patient.setGenre(request.getGenre());
        patient.setAdresse(request.getAdresse());
        patient.setTelephone(request.getTelephone());

        return patient;
    }
}
