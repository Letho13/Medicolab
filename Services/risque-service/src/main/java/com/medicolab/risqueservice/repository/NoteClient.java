package com.medicolab.risqueservice.repository;

import com.medicolab.risqueservice.exception.NoteClientFallback;
import com.medicolab.risqueservice.model.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "note-service", fallback = NoteClientFallback.class)
public interface NoteClient {

    @GetMapping("/api/note/patient/{id}")
    List<Note> getNotesByPatientId(@PathVariable("id") Integer patientId);
}
