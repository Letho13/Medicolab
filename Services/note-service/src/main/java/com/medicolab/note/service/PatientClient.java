package com.medicolab.note.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PatientClient {

    private final RestTemplate restTemplate;

    private final String baseUrl = "http://localhost:8080/api/patient/";

    public PatientClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


}
