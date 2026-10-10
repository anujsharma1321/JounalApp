package com.firstProgram.demo.service;

import com.firstProgram.demo.ExceptionHandler.ResourceNotFoundException;
import com.firstProgram.demo.appConfig.appCache;
import com.firstProgram.demo.entity.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Slf4j
@Component
public class WeatherService {
    @Value("${weather.API_KEY}")
    private String API_KEY;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private appCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getTemp(String city){
        WeatherResponse val=redisService.get("weather_of_"+city,WeatherResponse.class);
        if(val!=null){
            log.info("Redis is working fine");
            return val;
        }

        else{
            String newUrl=appCache.APP_CACHE.get("weather-api").replace("API_KEY",API_KEY).replace("CITY",city);
            ResponseEntity<WeatherResponse>response =restTemplate.exchange(newUrl, HttpMethod.GET,null, WeatherResponse.class);
            WeatherResponse body= response.getBody();
            if(body!=null){
                redisService.set("weather_of_"+city,body,300L);
                return body;
            }
            throw new ResourceNotFoundException("Weather not Found");
        }

    }

}
