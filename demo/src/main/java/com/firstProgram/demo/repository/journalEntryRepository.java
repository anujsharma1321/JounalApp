package com.firstProgram.demo.repository;

import com.firstProgram.demo.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface journalEntryRepository extends MongoRepository<JournalEntry, ObjectId>{

}
