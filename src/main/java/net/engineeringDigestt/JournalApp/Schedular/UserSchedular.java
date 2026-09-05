package net.engineeringDigestt.JournalApp.Schedular;

import net.engineeringDigestt.JournalApp.Controller.entity.JournalEntry;
import net.engineeringDigestt.JournalApp.Controller.entity.User;
import net.engineeringDigestt.JournalApp.Repository.UserRepository;
import net.engineeringDigestt.JournalApp.Repository.userRepositoryIMP;
import net.engineeringDigestt.JournalApp.Services.EmailService;
import net.engineeringDigestt.JournalApp.Services.sentimentalAnalysisService;
import net.engineeringDigestt.JournalApp.Services.userEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class UserSchedular {


    @Autowired
    private EmailService emailService;
    @Autowired
    private userRepositoryIMP  userRepositoryimp;
    @Autowired
    private sentimentalAnalysisService sentimentalAnalysisService;
    @Autowired
    private userEntryService userEntryService;

    @Autowired
    private User user;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUSersendSAmail(){

        List<User> users= userRepositoryimp.getUserforSA();
        for(User u:users){
            List<JournalEntry>journalentry= user.getJournalEntries();
            List<String> filterdata=journalentry.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getContent()).collect(Collectors.toList());
             String entry=String.join(",",filterdata);
             String sentiment= sentimentalAnalysisService.getsentiment(entry);
             emailService.sendEmail(user.getEmail(),"sentiment for last 7 days",sentiment);
        }

    }

}
