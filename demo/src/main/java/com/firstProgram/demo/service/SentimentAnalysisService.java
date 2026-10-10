package com.firstProgram.demo.service;


import com.firstProgram.demo.entity.JournalEntry;
import com.firstProgram.demo.enums.Sentiment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SentimentAnalysisService {



    public Sentiment getSentiment(List<JournalEntry>entry){
        Map<Sentiment,Integer>mp=new HashMap<>();
        Sentiment s=Sentiment.ANXIOUS;
        int mx=0;
        for(JournalEntry e:entry){
            mp.put(e.getSentiment(),mp.getOrDefault(e.getSentiment(),0)+1);
            if(mx<mp.get(e.getSentiment())){
                mx=mp.get(e.getSentiment());
                s=e.getSentiment();
            }
        }
        return s;
    }

}
