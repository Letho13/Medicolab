package com.medicolab.microservices.patient.exception;


import java.util.Objects;

public class PatientNotFoundException extends RuntimeException {

    private final String msg;

    public PatientNotFoundException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientNotFoundException that = (PatientNotFoundException) o;
        return Objects.equals(msg, that.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(msg);
    }
}
