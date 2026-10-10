package com.firstProgram.demo.controller;

import com.firstProgram.demo.ExceptionHandler.ResourceNotFoundException;
import com.firstProgram.demo.entity.JournalEntry;
import com.firstProgram.demo.entity.Users;
import com.firstProgram.demo.service.journalEntryService;
import com.firstProgram.demo.service.usersService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryContoller{
    @Autowired
    private journalEntryService journalEntryService;

    @Autowired
    private usersService userService;

    @GetMapping("/getdata")
    public ResponseEntity<List<JournalEntry>> getData(){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        Users usr=userService.findByUsername(auth.getName());

        boolean isAdmin=usr.getRoles().stream().anyMatch(r->r.equals("ADMIN"));
        if(!isAdmin)
            throw new AccessDeniedException("You are not authorized to access all journal entry");
        return ResponseEntity.ok(journalEntryService.getAll());
    }

    @GetMapping("/my-blogs")
    public ResponseEntity<?> showEntriesOfUser(){
            Authentication auth= SecurityContextHolder.getContext().getAuthentication();
            List<JournalEntry> jr=journalEntryService.getAllByUser(auth.getName());
            return  ResponseEntity.ok(jr);
    }


    @PostMapping("/add-blog")
    public ResponseEntity<JournalEntry> setData(@RequestBody JournalEntry myEntry){
        try {
            Authentication auth= SecurityContextHolder.getContext().getAuthentication();
            String username=auth.getName();
            myEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryService.saveEntry(myEntry,username);
            System.out.println("Entry added: " + saved.getId());
            return new ResponseEntity<>(saved,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> findById(@PathVariable ObjectId id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userService.findByUsername(auth.getName());

        JournalEntry journalEntry = journalEntryService.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journal entry not found"));

        boolean belongsToUser = user.getJournalEntries()
                .stream()
                .anyMatch(entry -> entry.getId().equals(id));

        if (!belongsToUser) {
            throw new AccessDeniedException("You are not authorized to access this journal entry");
        }

        return ResponseEntity.ok(journalEntry);
    }


    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteByID(@PathVariable ObjectId id) {
        try {
            Authentication auth=SecurityContextHolder.getContext().getAuthentication();
            Users usr=userService.findByUsername(auth.getName());

            List<JournalEntry>collect=usr.getJournalEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());

            if(!collect.isEmpty()) {
                journalEntryService.deleteOne(id, usr.getUsername());
                return new ResponseEntity<>(HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/update/{id}")
    public JournalEntry updateJournal(@PathVariable("id") ObjectId id,
                                      @RequestBody JournalEntry newentry) {

        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        Users usr=userService.findByUsername(auth.getName());

        List<JournalEntry>collect=usr.getJournalEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());

        if(!collect.isEmpty()) {
            JournalEntry old=journalEntryService.getOne(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
            old.setTitle(( newentry.getTitle()!=null  && !newentry.getTitle().equals("") )? newentry.getTitle(): old.getTitle());
            old.setDesc(( newentry.getDesc()!=null  && !newentry.getDesc().equals("") )? newentry.getDesc(): old.getDesc());

            journalEntryService.saveEntry(old);
            return old;
        }
        return null;
    }

}
