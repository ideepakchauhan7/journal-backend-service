package net.engineeringDigestt.JournalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class redisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void sendMail()
    {
        redisTemplate.opsForList().rightPush("email","greatmanfor01@gmail.com");
        Object email = redisTemplate.opsForList().index("email",0L);
        String name=redisTemplate.opsForValue().get("name").toString();
        System.out.println(name);

    }
}
