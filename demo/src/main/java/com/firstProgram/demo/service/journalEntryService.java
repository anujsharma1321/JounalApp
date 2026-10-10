package com.firstProgram.demo.service;

import com.firstProgram.demo.ExceptionHandler.ResourceNotFoundException;
import com.firstProgram.demo.entity.JournalEntry;
import com.firstProgram.demo.entity.Users;
import com.firstProgram.demo.repository.journalEntryRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class journalEntryService{
    @Autowired
    private journalEntryRepository journalEntryRepository;
    @Autowired
    private usersService usersService;


    public JournalEntry saveEntry(JournalEntry journalEntry,String username) {

        try{
            Users usr= usersService.findByUsername(username);
            JournalEntry saved=journalEntryRepository.save(journalEntry);
            usr.getJournalEntries().add(saved);

            usersService.saveEntry(usr);
            return journalEntry;
        }catch(Exception e){
            System.out.println("Exception: "+e);
            throw new RuntimeException("Something went wrong.");
        }

    }
    public void saveEntry(JournalEntry journalEntry){
            journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllByUser(String username){
        Users usr= usersService.findByUsername(username);
        return usr.getJournalEntries();
    }

    public List<JournalEntry> getAll(){

        return journalEntryRepository.findAll();
    }


    public Optional<JournalEntry> getOne(ObjectId id) {
        return journalEntryRepository.findById(id);
    }


    public boolean deleteOne(ObjectId id,String username){
        Users usr=usersService.findByUsername(username);

        usr.getJournalEntries().removeIf(x-> x.getId().equals(id));
        usersService.saveEntry(usr);
        journalEntryRepository.deleteById(id);
        return true;
    }

}
