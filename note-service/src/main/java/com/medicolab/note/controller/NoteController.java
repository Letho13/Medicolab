package com.medicolab.note.controller;

import com.medicolab.note.model.Note;
import com.medicolab.note.service.NoteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<Note>> getPatientNotes(@PathVariable Integer id) {
       return noteService.GetPatientNotes(id);
    }

    @PostMapping("/add")
    public ResponseEntity addNote(@RequestBody Note note) {
        noteService.createNote(note);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateNoteById(@RequestBody String id) {
        noteService.updateNoteById(id);
        return (ResponseEntity) ResponseEntity.status(HttpStatus.OK); // je comprend pas bien pq je dois mettre ce la comme ça
    }


}
