package com.medicolab.risqueservice.repository;

import com.medicolab.risqueservice.model.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "note-service", url = "http://localhost:8083")
public interface NoteClient {

    @GetMapping("/api/note/patient/{id}")
    List<Note> getNotesByPatientId(@PathVariable("id") Integer patientId);
}
