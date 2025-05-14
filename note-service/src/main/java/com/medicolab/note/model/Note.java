package com.medicolab.note.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "note")
public class Note {

    @Id
    private String noteId;
    private Integer patId;
    private String patient;
    private String note;

    public Note() {
    }

    public Note(String noteId, Integer patId, String patient, String note) {
        this.noteId = noteId;
        this.patId = patId;
        this.patient = patient;
        this.note = note;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public Integer getPatId() {
        return patId;
    }

    public void setPatId(Integer patId) {
        this.patId = patId;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId='" + noteId + '\'' +
                ", patId=" + patId +
                ", patient='" + patient + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
