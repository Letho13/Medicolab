package com.medicolab.note.service;

import com.medicolab.note.exception.NoteNotFoundException;
import com.medicolab.note.model.Note;
import com.medicolab.note.repository.NoteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NoteService {

   private final NoteRepository noteRepository;


    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    public void createNote (Note note) {
        noteRepository.insert(note);
    }

    public void createAllNotes(List<Note> notes) {
        noteRepository.saveAll(notes);
    }


    public List<Note> getPatientNotes (Integer patId){
        List<Note> notes = noteRepository.findAll();
        List<Note> patientNotes = notes.stream()
                .filter(note -> note.getPatId().equals(patId))
                .toList();

            return patientNotes;

    }

    public void deleteNote (String id) {
        noteRepository.deleteById(id);
    }

//    public Note updateNoteById(Note note) {
//
//        Note existingNote = noteRepository.findNoteByPatientId(note.getPatId())
//                        .orElseThrow(() -> new NoteNotFoundException(String.format("La note n'existe %s pas", id)
//                        ));
//        existingNote.setPatient(note.getPatient());
//        existingNote.setNote(note.getNote());
//
//       return  noteRepository.save(note);
//    }

    public String niveauDeRisque (Note note){



        return "";
    }


}
