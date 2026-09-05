package net.engineeringDigestt.JournalApp.Repository;

import net.engineeringDigestt.JournalApp.Controller.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class userRepositoryIMP {

    @Autowired
    private MongoTemplate mongoTemplate;
    public List<User> getUserforSA()
    {


        Query query=new Query();
        query.addCriteria(Criteria.where("email").exists(true));
//        query.addCriteria(Criteria.where("email").ne(null).ne(""));
        query.addCriteria(Criteria.where("sentimenatlanalysis").is(true));


        List<User> user=mongoTemplate.find(query,User.class);
        return user;
    }
}
