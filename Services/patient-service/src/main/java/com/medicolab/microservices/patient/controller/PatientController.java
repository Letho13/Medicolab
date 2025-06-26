package com.medicolab.microservices.patient.controller;

import com.medicolab.microservices.patient.model.PatientRequest;
import com.medicolab.microservices.patient.model.Patient;
import com.medicolab.microservices.patient.repository.PatientRepository;
import com.medicolab.microservices.patient.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/patient")
@RestController
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService, PatientRepository patientRepository) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Patient> patientsPage = patientService.getAll(page, size);
        List<Patient> patients = patientsPage.getContent();

        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> showPatient(@PathVariable("id") Integer id) {
        Patient patient = patientService.findPatientById(id);
        return ResponseEntity.ok(patient);
    }

    @PutMapping()
    public ResponseEntity<Void> editPatient(@RequestBody @Valid PatientRequest request) {
        patientService.updatePatient(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Integer> createPatient(@RequestBody @Valid PatientRequest request) {
        return ResponseEntity.ok(patientService.createPatient(request));
    }

    @DeleteMapping("/delete/{id}")
    public void deletePatient(@PathVariable("id") Integer id) {
        patientService.deletePatient(id);
    }

}
