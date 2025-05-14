package com.medicolab.microservices.patient.controller;

import com.medicolab.microservices.patient.PatientRequest;
import com.medicolab.microservices.patient.model.Patient;
import com.medicolab.microservices.patient.repository.PatientRepository;
import com.medicolab.microservices.patient.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/patient")
@RestController

public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService, PatientRepository patientRepository) {
        this.patientService = patientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity <Patient> showPatient(@PathVariable("id") Integer id) {
       Patient patient = patientService.findPatientById(id);
       return ResponseEntity.ok(patient);
    }

    @PutMapping()
    public ResponseEntity <Void> editPatient(@RequestBody @Valid PatientRequest request) {
       patientService.updatePatient(request);
        return (ResponseEntity.ok().build());
    }

    @PostMapping()
    public ResponseEntity<Integer> createPatient(@RequestBody @Valid PatientRequest request) {
       return ResponseEntity.ok(patientService.createPatient(request));
    }

    @PostMapping("/delete/{id}")
    public void deletePatient(@PathVariable Integer id ) {
        patientService.deletePatient(id);
    }

}
