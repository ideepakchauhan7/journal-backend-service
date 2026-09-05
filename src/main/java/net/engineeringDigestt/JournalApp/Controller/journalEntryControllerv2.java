package net.engineeringDigestt.JournalApp.Controller;

import net.engineeringDigestt.JournalApp.Controller.entity.JournalEntry;
import net.engineeringDigestt.JournalApp.Services.journalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/Journal")
public class journalEntryControllerv2

{
    @Autowired
    private journalEntryService journalEntryService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getAllJournalEntriesofUser(@PathVariable String username){
        List<JournalEntry> all = journalEntryService.findAllByUsername(username);
        if (all == null) {
            return new ResponseEntity<>("User not found please make a entry first", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PostMapping("/{username}")
    public boolean createEntry(@RequestBody JournalEntry Myentry,@PathVariable String username)
    {
//        journalEntryMap.put(Long.valueOf(Myentry.getId()),Myentry);
        Myentry.setDate(LocalDateTime.now());
        journalEntryService.savedEntry(Myentry,username);
        return true;

    }
    @GetMapping("/id/{myid}")
    public JournalEntry getJournalentryByid(@PathVariable ObjectId myid)
    {
//        return journalEntryMap.get(myid);
        return journalEntryService.findbyid(myid);
    }
//    @DeleteMapping("id/{username}/{myid}")
//    public Boolean getJournalentryBbyid(@PathVariable ObjectId myid)
//    {
////        String username;
//        journalEntryService.deleteid(myid,username);
//       return true;
//    }

//    @PutMapping("id/{id}")
//    public JournalEntry updateid(@PathVariable ObjectId id , @RequestBody JournalEntry entry)
//    {
//        JournalEntry old = journalEntryService.findbyid(id);
//        if(old!=null)
//        {
//            old.setTitle(entry.getTitle()!=null && !entry.getTitle().equals("") ? entry.getTitle() : old.getTitle());
//            old.setContent(entry.getContent()!=null && !entry.getContent().equals("") ? entry.getContent() : old.getContent());
//        }
//        journalEntryService.savedEntry(old);
//        entry.setDate(LocalDateTime.now());
////
//        return old;
//    }
}
