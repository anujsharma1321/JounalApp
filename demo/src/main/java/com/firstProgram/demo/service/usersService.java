package com.firstProgram.demo.service;

import com.firstProgram.demo.ExceptionHandler.ResourceNotFoundException;
import com.firstProgram.demo.entity.JournalEntry;
import com.firstProgram.demo.entity.Users;
import com.firstProgram.demo.repository.journalEntryRepository;
import com.firstProgram.demo.repository.usersRepository;
import com.mongodb.DuplicateKeyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class usersService{

@Autowired
private usersRepository userRepository;

//private static final Logger logger= LoggerFactory.getLogger(usersService.class);

private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    public Users createUser(Users user) {
        try {
            return userRepository.save(user);
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("Username already exists");
        }
    }
    public Users saveUser(Users user){
        try {
            user.setPass(passwordEncoder.encode(user.getPass()));
            user.setRoles(Arrays.asList("USER"));
            Users usr=userRepository.save(user);
            log.info("User {} save successfully ",user.getUsername());
            return usr;
        }catch (DataIntegrityViolationException e) {
            log.warn("Failed to save user: {}", user.getUsername(), e);
            throw new RuntimeException("username already existbjkk.", e);
        }
    }
    public Users saveAdmin(Users user){
        user.setPass(passwordEncoder.encode(user.getPass()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        return userRepository.save(user);
    }

    public List<Users> getAll(){
        return userRepository.findAll();
    }

    public Optional<Users> getOne(ObjectId id) {
        return userRepository.findById(id);
    }

    public boolean deleteOne(ObjectId id){
        userRepository.deleteById(id);
        return true;
    }

    public Users findByUsername(String username){
        Users usr= userRepository.findByUsername(username);
        return usr;
    }

    public void saveEntry(Users usr){
        userRepository.save(usr);
    }

    public Users deleteOne(String name){
        return userRepository.deleteByUsername(name);
    }

}
