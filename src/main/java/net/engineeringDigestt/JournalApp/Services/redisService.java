package net.engineeringDigestt.JournalApp.Services;

import lombok.extern.slf4j.Slf4j;
import net.engineeringDigestt.JournalApp.apiResponse.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class redisService {


    @Autowired
    private RedisTemplate redisTemplate;
    public <T> T get(String key, Class<T> entityClass)

    {
        try {
            Object o = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(o.toString(), entityClass);
        }
        catch (Exception e)
        {
            log.error("Exception " + e);
            return null;
        }
    }

    public <T> T set(String key, Object o,Long ttl)

    {
        try {
            redisTemplate.opsForValue().set(key,o,ttl, TimeUnit.SECONDS);

        }
        catch (Exception e)
        {
            log.error("Exception " + e);
            return null;
        }
    return null;
    }
}
