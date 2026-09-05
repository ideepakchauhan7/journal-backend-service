package net.engineeringDigestt.JournalApp.Controller;

import lombok.extern.slf4j.Slf4j;
import net.engineeringDigestt.JournalApp.Controller.entity.User;
import net.engineeringDigestt.JournalApp.Services.UserDetailServiceimpl;
import net.engineeringDigestt.JournalApp.Services.userEntryService;
import net.engineeringDigestt.JournalApp.utils.JWTutils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
public class publicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceimpl userDetailsService;

    @Autowired
    private userEntryService userentryservice;

    @Autowired
    private JWTutils jwtutils;

    @GetMapping({"/Health-check", "/public/Health-check"})
    public String HealthCheck() {
        return "OK";
    }
    @PostMapping({"/signup", "/public/signup"})
    public ResponseEntity<?> signup(@RequestBody User user) {

        if (hasMissingCredentials(user)) {
            return ResponseEntity.badRequest().body("username and password are required");
        }
        try {
            userentryservice.savedEntry(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DuplicateKeyException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("username already exists");
        }
    }

    @PostMapping({"/login", "/public/login"})
    public ResponseEntity<?> login(@RequestBody User user) {
        if (hasMissingCredentials(user)) {
            return ResponseEntity.badRequest().body("username and password are required");
        }
        try {
            authenticationManager.
                    authenticate(new
                            UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userdetails = userDetailsService.loadUserByUsername(user.getUsername());
            String jwt=jwtutils.generateToken(userdetails.getUsername());
            return new ResponseEntity<>(jwt,HttpStatus.OK);

        }
        catch(AuthenticationException e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid username or password");
        }
        catch(Exception e)
        {
            log.error("exception occured while autjenticate",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    private boolean hasMissingCredentials(User user) {
        return user == null || user.getUsername() == null || user.getUsername().isBlank()
                || user.getPassword() == null || user.getPassword().isBlank();
    }
}
