package net.engineeringDigestt.JournalApp.Controller;

import net.engineeringDigestt.JournalApp.Controller.entity.User;
import net.engineeringDigestt.JournalApp.Services.userEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    private userEntryService userEntryService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers()
    {
      List<User> all=userEntryService.findall();
      if(all!=null && !all.isEmpty())
      {
         return new ResponseEntity<>(all, HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PostMapping("/create-admin-user")
    public void createadmin(@RequestBody User user)
    {
        userEntryService.saveAdmin(user);
    }

}
