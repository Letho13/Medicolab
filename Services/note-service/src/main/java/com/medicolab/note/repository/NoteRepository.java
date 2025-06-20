package com.medicolab.note.repository;

import com.medicolab.note.model.Note;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note,String> {


}
