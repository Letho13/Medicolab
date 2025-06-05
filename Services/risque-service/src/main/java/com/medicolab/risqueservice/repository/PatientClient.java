package com.medicolab.risqueservice.repository;

import com.medicolab.risqueservice.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patient-service", url = "http://localhost:8080")
public interface PatientClient  {

    @GetMapping("/api/patient/{id}")
    Patient getPatientById(@PathVariable("id") Integer id);
}


