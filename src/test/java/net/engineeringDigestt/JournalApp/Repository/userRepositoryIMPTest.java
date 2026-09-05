package net.engineeringDigestt.JournalApp.Repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class userRepositoryIMPTest
{

    @Autowired
    private userRepositoryIMP userRepository;

    @Test
    public void TestsaveNewname()
    {
        Assertions.assertNotNull(userRepository.getUserforSA());
//       userRepository.getUserforSA();
    }

}
