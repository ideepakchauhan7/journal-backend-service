package net.engineeringDigestt.JournalApp.service;

import net.engineeringDigestt.JournalApp.Services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmialServiceaTest {

    @Autowired
    private EmailService emailService;
    @Test
    public void emialServiceaTest()
    {
        emailService.sendEmail("iamkapil7340@gmail.com","sing mail","hwllo");
    }
}
