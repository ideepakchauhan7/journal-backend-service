package net.engineeringDigestt.JournalApp.Services;

import net.engineeringDigestt.JournalApp.Controller.entity.JournalEntry;
import net.engineeringDigestt.JournalApp.Controller.entity.User;
import net.engineeringDigestt.JournalApp.Repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class journalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;



    @Autowired
    private userEntryService userEntryService;
@Transactional
    public void savedEntry(JournalEntry journalEntry,String username) {
    try{
        User user = userEntryService.findbyusername(username);
        if (user != null) {
            // Save the journal entry first
            JournalEntry saved = journalEntryRepository.save(journalEntry);

            // Link the entry to the user
            user.getJournalEntries().add(saved);

            // Save journal references without re-encoding the existing password hash.
            userEntryService.saveExistingUser(user);
        }

//        User user=userEntryService.findbyusername(username);
//       JournalEntry saved= journalEntryRepository.save(journalEntry);
//       user.getJournalEntries().add(saved);
//       user.setUsername(null);
//       userEntryService.savedEntry(user);
    }
    catch(Exception ex)
    {
//        System.out.println(ex);
        ex.printStackTrace();
    }
    }
    public List<JournalEntry> findall()
    {
        return journalEntryRepository.findAll();
    }
    public List<JournalEntry> findAllByUsername(String username) {
        User user = userEntryService.findbyusername(username);
        if (user == null) {
            return null;
        }
        List<JournalEntry> journalEntries = user.getJournalEntries();
        return journalEntries != null ? journalEntries : Collections.emptyList();
    }
    public  JournalEntry findbyid(ObjectId id)
    {

        return journalEntryRepository.findById(id).get();
    }
    public  Boolean deleteid(ObjectId id, String username)
    {
        User user=userEntryService.findbyusername(username);
        user.getJournalEntries().removeIf(x->x.getId().equals(id));
        userEntryService.saveExistingUser(user);
      journalEntryRepository.deleteById(id);
        return true;
    }
}
