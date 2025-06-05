package com.medicolab.risqueservice.controller;

import com.medicolab.risqueservice.model.Patient;
import com.medicolab.risqueservice.service.RisqueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risque")
public class RisqueController {

    private final RisqueService risqueService;

    public RisqueController(RisqueService risqueService) {
        this.risqueService = risqueService;
    }

    @GetMapping("/analyse/{id}")
    public ResponseEntity<String> getRisqueAnalyse(@PathVariable("id") Integer id) throws Exception {
        String resultat = risqueService.analyseRisque(id);
        return ResponseEntity.ok(resultat);
    }


}
