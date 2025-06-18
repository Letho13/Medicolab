package com.medicolab.risqueservice.exception;

import com.medicolab.risqueservice.model.Note;
import com.medicolab.risqueservice.repository.NoteClient;

import java.util.Collections;
import java.util.List;

public class NoteClientFallback implements NoteClient {

    @Override
    public List<Note> getNotesByPatientId(Integer patientId) {
        return Collections.emptyList();
    }

}
