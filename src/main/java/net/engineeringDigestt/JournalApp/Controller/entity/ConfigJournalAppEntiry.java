package net.engineeringDigestt.JournalApp.Controller.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document(collection="config_journal_app")
@Data
@NoArgsConstructor

public class ConfigJournalAppEntiry {

    private String key;
    private String value;

}
