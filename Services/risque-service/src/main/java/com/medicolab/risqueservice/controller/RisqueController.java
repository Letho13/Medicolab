package com.medicolab.risqueservice.controller;

import com.medicolab.risqueservice.service.RisqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/risque")
@RestController
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
