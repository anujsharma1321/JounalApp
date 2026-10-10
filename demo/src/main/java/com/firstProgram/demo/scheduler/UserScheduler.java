package com.firstProgram.demo.scheduler;

import com.firstProgram.demo.appConfig.appCache;
import com.firstProgram.demo.entity.JournalEntry;
import com.firstProgram.demo.entity.Users;
import com.firstProgram.demo.enums.Sentiment;
import com.firstProgram.demo.repository.userRepoImpl;
import com.firstProgram.demo.service.EmailService;
import com.firstProgram.demo.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class UserScheduler {
    @Autowired
    private userRepoImpl userRepo;

    @Autowired
    private EmailService emailService;
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;
    @Autowired
    private appCache appCache;

    //@Scheduled(cron="0 * * ? * *")
    public void fetchUsersSendSAMail(){
        List<Users> users=userRepo.getUserForSA();
        for(Users user: users){
            List<JournalEntry>filterData=user.getJournalEntries()
                    .stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).toList();

            Sentiment s=sentimentAnalysisService.getSentiment(filterData);
            System.out.println(user.getUsername()+" was " +s.toString()+" last week. ");
            //emailService.sendEmail(user.getEmail(),"Last 7 days report. ",sentiment);
        }
    }
    //@Scheduled(cron = "0 */10 * * * *")
    public void refreshAppCache(){
        appCache.init();
        System.out.println("Cache refreshed...");
    }

}
