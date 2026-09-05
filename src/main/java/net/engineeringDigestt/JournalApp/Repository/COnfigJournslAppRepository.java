package net.engineeringDigestt.JournalApp.Repository;

import net.engineeringDigestt.JournalApp.Controller.entity.ConfigJournalAppEntiry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface COnfigJournslAppRepository extends MongoRepository<ConfigJournalAppEntiry, ObjectId> {
}
