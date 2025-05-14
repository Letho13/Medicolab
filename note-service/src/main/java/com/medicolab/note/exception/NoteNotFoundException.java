package com.medicolab.note.exception;

import java.util.Objects;

public class NoteNotFoundException extends RuntimeException {

    private final String msg;

    public NoteNotFoundException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteNotFoundException that = (NoteNotFoundException) o;
        return Objects.equals(msg, that.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(msg);
    }

}
