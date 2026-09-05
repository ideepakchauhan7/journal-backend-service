package net.engineeringDigestt.JournalApp.Repository;

import net.engineeringDigestt.JournalApp.Controller.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
}
