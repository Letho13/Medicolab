package com.medicolab.note.service;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PatientClient {

    private final RestTemplate restTemplate;

    private final String baseUrl = "http://patient-service/api/patient/";

    public PatientClient(@LoadBalanced RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


}
