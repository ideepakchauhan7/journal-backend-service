package net.engineeringDigestt.JournalApp.cache;

import jakarta.annotation.PostConstruct;
import net.engineeringDigestt.JournalApp.Controller.entity.ConfigJournalAppEntiry;
import net.engineeringDigestt.JournalApp.Repository.COnfigJournslAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class appcache {

    @Autowired
    public COnfigJournslAppRepository configJournalAppRepository;

    public  Map<String,String> APP_CACHE=new HashMap<>();

    @PostConstruct
    public void init()
    {
        List<ConfigJournalAppEntiry> all= configJournalAppRepository.findAll();
        for(ConfigJournalAppEntiry configJournalAppEntiry : all)
        {
            APP_CACHE.put(configJournalAppEntiry.getKey(),configJournalAppEntiry.getValue());
        }
    }

}
