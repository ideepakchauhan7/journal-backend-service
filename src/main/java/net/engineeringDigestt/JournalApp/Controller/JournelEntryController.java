package net.engineeringDigestt.JournalApp.Controller;

import net.engineeringDigestt.JournalApp.Controller.entity.JournalEntry;
import net.engineeringDigestt.JournalApp.Controller.entity.User;
import net.engineeringDigestt.JournalApp.Services.journalEntryService;
import net.engineeringDigestt.JournalApp.Services.userEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournelEntryController {

    private final journalEntryService journalEntryService;
    @Autowired
    private userEntryService userEntryService;
    private Map<Long,JournalEntry> journalEntryMap=new HashMap();

    public JournelEntryController(journalEntryService journalEntryService)
    {
        this.journalEntryService = journalEntryService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllJournalEntries()
    {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        List<JournalEntry> all = journalEntryService.findAllByUsername(username);
        if(all == null)
        {
            return new ResponseEntity<>("User not found please make a entry first",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(all, HttpStatus.OK);

//        return (journalEntryMap.values().stream().toList());
    }

@PostMapping
    public ResponseEntity<JournalEntry> createJournalEntry(@RequestBody JournalEntry journalEntry)
{

    try {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();

        User user = userEntryService.findbyusername(username);
        if(user==null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (user != null) {
            journalEntry.setDate(java.time.LocalDateTime.now());
            journalEntryService.savedEntry(journalEntry, username);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
        catch(Exception e)
        {
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/id/{myid}")
    public ResponseEntity<JournalEntry> getJournalentryByid(@PathVariable ObjectId myid)
    {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user=userEntryService.findbyusername(username);
         List<JournalEntry> collect=user.getJournalEntries().stream().filter(x->x.getId().equals(myid)).collect(Collectors.toList());
         if(!collect.isEmpty())
         {
             Optional<JournalEntry> journalEntry= Optional.ofNullable(journalEntryService.findbyid(myid));
             if(journalEntry.isPresent())
             {
                 return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
             }

         }

        return  new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/id/{myid}")
    public ResponseEntity<?> deleteJournalentryBbyid(@PathVariable ObjectId myid)
    {
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();

        boolean remove = journalEntryService.deleteid(myid,username);
        if(remove) {
            return new ResponseEntity<>("the content has been deleted", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}


//    @PutMapping("id/{id}")
//    public JournalEntry updateid(@PathVariable Long id , @RequestBody JournalEntry entry)
//    {
//        return journalEntryMap.put(id,entry);
//    }

