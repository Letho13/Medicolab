package com.medicolab.note.service;

import com.medicolab.note.exception.NoteNotFoundException;
import com.medicolab.note.model.Note;
import com.medicolab.note.repository.NoteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

   private final NoteRepository noteRepository;


    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    public void createNote (Note note) {
        noteRepository.insert(note);
    }

    public ResponseEntity <List<Note>>GetPatientNotes (Integer patId){
        List<Note> notes = noteRepository.findAll();
        List<Note> patientNotes = notes.stream()
                .filter(note -> note.getPatId().equals(patId)).toList();
        return (ResponseEntity<List<Note>>) patientNotes;
    }

    public Note updateNoteById(String id) {
        Note note = new Note();
        Note existingNote = noteRepository.findById(id)
                        .orElseThrow(() -> new NoteNotFoundException(String.format("La note n'existe %s pas", id)
                        ));
        existingNote.setPatId(note.getPatId());
        existingNote.setPatient(note.getPatient());
        existingNote.setNote(note.getNote());

       return  noteRepository.save(note);
    }
}
