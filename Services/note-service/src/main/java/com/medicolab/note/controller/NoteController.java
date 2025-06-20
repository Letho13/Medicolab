package com.medicolab.note.controller;

import com.medicolab.note.model.Note;
import com.medicolab.note.service.NoteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<?> getPatientNotes(@PathVariable(name="id") Integer id) {
        List<Note> patientNotes = noteService.getPatientNotes(id);
        return ResponseEntity.ok(patientNotes);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addNote(@RequestBody Note note) {
        noteService.createNote(note);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/addAll")
    public ResponseEntity<Void> addAllNote(@RequestBody List<Note> notes) {
        noteService.createAllNotes(notes);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable (name="id") String id){
        noteService.deleteNote(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


//    @PutMapping("/update/{id}")
//    public ResponseEntity updateNoteById(@RequestBody String patId) {
//        noteService.updateNoteById(patId);
//        return (ResponseEntity) ResponseEntity.status(HttpStatus.OK); // je comprend pas bien pq je dois mettre ce la comme ça
//    }


}
