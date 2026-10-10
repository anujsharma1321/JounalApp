package com.firstProgram.demo.controller;


import com.firstProgram.demo.entity.JournalEntry;
import com.firstProgram.demo.entity.Users;
import com.firstProgram.demo.service.journalEntryService;
import com.firstProgram.demo.service.usersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private journalEntryService journalEntryService;
    @Autowired
    private usersService usersService;


    @GetMapping("/all-blogs")
    public ResponseEntity<?> getData() {
        try {
            List<JournalEntry>jr=journalEntryService.getAll();

            return new ResponseEntity<>(jr,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("all-users")
    private ResponseEntity<?> getAllUsers() {
        List<Users>result=usersService.getAll();
        if(result!=null && !result.isEmpty())
                return new ResponseEntity<>(result,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("add-admin-user")
    private ResponseEntity<?> addAdmin(@RequestBody Users usr){
        try{
            return new ResponseEntity<>(usersService.saveAdmin(usr),HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
