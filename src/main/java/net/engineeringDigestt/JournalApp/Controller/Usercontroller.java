package net.engineeringDigestt.JournalApp.Controller;

import net.engineeringDigestt.JournalApp.Controller.entity.User;
import net.engineeringDigestt.JournalApp.Repository.UserRepository;
import net.engineeringDigestt.JournalApp.Services.userEntryService;
import net.engineeringDigestt.JournalApp.Services.weatherservices;
import net.engineeringDigestt.JournalApp.apiResponse.WeatherResponse;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class Usercontroller {
    @Autowired
    private userEntryService userentryservice;
    @Autowired
    private UserRepository userrepository;

//    @Autowired
//    private WeatherResponse weatherResponse;
    @Autowired
    private weatherservices weatherservices;


    @GetMapping
    public List<User> getallUsers() {
        return userentryservice.findall();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable ObjectId id) {
        User user = userentryservice.findbyid(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
//    @PostMapping
//    public void createUser(@RequestBody User user) {
//        userentryservice.savedEntry(user);
//    }

    @PutMapping()
    public ResponseEntity<?> updateuser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userindb = userentryservice.findbyusername(username);
        if (userindb == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userentryservice.updateUser(userindb, user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteuserbyid(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userrepository.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/hii")
    public ResponseEntity<?> greetings()
    {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>("hii" + authentication.getName() + " weather feels like " + weatherservices.getweather("Noida").getCurrent().getTemperature() , HttpStatus.OK);
    }
}

