package net.engineeringDigestt.JournalApp.Repository;

import net.engineeringDigestt.JournalApp.Controller.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId>
{
    User findByUsername(String username);

    void deleteByUsername(String username);
}
