package com.firstProgram.demo.repository;

import com.firstProgram.demo.entity.configJournal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface configJournalRepository extends MongoRepository<configJournal, ObjectId> {
}
