package net.engineeringDigestt.JournalApp.service;

import net.engineeringDigestt.JournalApp.Repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class userEnytryServiceTests
{
    @Autowired
    private UserRepository userRepository;

    @Disabled
    @Test
    public void testfindbyusername()
    {
        assertEquals(4,2 + 2);
        assertNotNull(userRepository.findByUsername("Gagan"));
        assertTrue(userRepository.findByUsername("Gagan").equals(userRepository.findByUsername("Gagan")));
    }
    @ParameterizedTest
    @CsvSource({
            "1,1,2","2,2,4","3,3,6"
    })
    public void test(int a,int b,int expected)
    {
        assertEquals(expected,a+b,"faild for "+ expected);
    }
}
