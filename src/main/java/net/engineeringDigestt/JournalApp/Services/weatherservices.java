package net.engineeringDigestt.JournalApp.Services;

import net.engineeringDigestt.JournalApp.Controller.entity.User;
import net.engineeringDigestt.JournalApp.apiResponse.WeatherResponse;
//import org.apache.catalina.connector.Response;
import net.engineeringDigestt.JournalApp.cache.appcache;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class weatherservices {
    @Value("${weather.api.key}")
    private  String apikey;
//    private final static String API="http://api.weatherstack.com/current?access_key=YOUR_ACCESS_KEY&query=Delhi";

    @Autowired
    private  RestTemplate restTemplate;
    @Autowired
    public appcache AppCache;
    @Autowired
    private redisService redisService;


    public WeatherResponse getweather(String city)
    {

       WeatherResponse weatherresponse= redisService.get("Weather of "+city,WeatherResponse.class);
        if(weatherresponse !=null)
        {
            return weatherresponse;
        }
        else {
            String finalAPi = AppCache.APP_CACHE.get("weather_api").replace("Delhi", city).replace("YOUR_ACCESS_KEY", apikey);

            User user = User.builder().username("kapil").password("kapil").build();
            HttpEntity<User> httpEntity = new HttpEntity<>(user);
            ResponseEntity<WeatherResponse> Response = restTemplate.exchange(finalAPi, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = Response.getBody();
//            return body;
            if(body!=null)
            {
                redisService.set("Weather of "+city,body,300l);

            }
            return body;
        }
    }
}
