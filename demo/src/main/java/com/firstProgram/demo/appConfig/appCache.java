package com.firstProgram.demo.appConfig;

import com.firstProgram.demo.entity.configJournal;
import com.firstProgram.demo.repository.configJournalRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class appCache{
    @Autowired
    private configJournalRepository configJournalRepository;

    public Map<String,String> APP_CACHE=new HashMap<>();
    @PostConstruct
    public void init(){
        List<configJournal> all=configJournalRepository.findAll();

        for(configJournal e:all)
            APP_CACHE.put(e.getKey(),e.getValue());
    }
}
