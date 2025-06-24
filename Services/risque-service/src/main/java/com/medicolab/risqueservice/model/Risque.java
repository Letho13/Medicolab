package com.medicolab.risqueservice.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Risque {
    @JsonProperty("risques")
    private List<String> risque;

    public Risque() {
    }

    public Risque(List<String> risque) {
        this.risque = risque;
    }

    public List<String> getRisque() {
        return risque;
    }

    public void setRisque(List<String> risque) {
        this.risque = risque;
    }
}
