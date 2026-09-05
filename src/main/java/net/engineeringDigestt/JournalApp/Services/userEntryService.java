package net.engineeringDigestt.JournalApp.Services;

import lombok.extern.slf4j.Slf4j;
import net.engineeringDigestt.JournalApp.Controller.entity.User;
import net.engineeringDigestt.JournalApp.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class userEntryService {


    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordencoder=new BCryptPasswordEncoder();

    private static final Logger logger= LoggerFactory.getLogger(userEntryService.class);

    public boolean  savedEntry(User user) {

        try {
            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                user.setPassword(passwordencoder.encode(user.getPassword()));
            }
            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                user.setRoles(Arrays.asList("USER"));
            }
            userRepository.save(user);
            return true;
        }
        catch (RuntimeException e) {
            logger.info("this is an info");
            logger.debug("this is an debug");
            logger.warn("this is an warn");
            logger.trace("this is an trace");
            log.error("error occured for {} :",user.getUsername(),e);
            System.out.println("this is an error ");
            throw e;
        }
    }

    public User saveExistingUser(User user) {
        return userRepository.save(user);
    }

    public void saveAdmin(User user) {

        user.setPassword(passwordencoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);

    }
    public void savednewuser(User user)
    {
        user.setPassword(passwordencoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public List<User> findall()
    {
        return userRepository.findAll();
    }
    public User findbyid(ObjectId id)
    {
        return userRepository.findById(id).orElse(null);
    }
    public Boolean deleteid(ObjectId id)
    {
      userRepository.deleteById(id);
        return true;
    }
    public User findbyusername(String username)
    {
        return userRepository.findByUsername(username);
    }

    public User updateUser(User existingUser, User requestedUser) {
        if (existingUser == null) {
            return null;
        }
        if (requestedUser.getUsername() != null && !requestedUser.getUsername().isBlank()) {
            existingUser.setUsername(requestedUser.getUsername());
        }
        if (requestedUser.getPassword() != null && !requestedUser.getPassword().isBlank()) {
            existingUser.setPassword(passwordencoder.encode(requestedUser.getPassword()));
        }
        return userRepository.save(existingUser);
    }


}
