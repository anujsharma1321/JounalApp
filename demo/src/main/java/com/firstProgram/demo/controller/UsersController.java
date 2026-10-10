package com.firstProgram.demo.controller;

import com.firstProgram.demo.ExceptionHandler.ResourceNotFoundException;
import com.firstProgram.demo.entity.Users;
import com.firstProgram.demo.entity.WeatherResponse;
import com.firstProgram.demo.service.WeatherService;
import com.firstProgram.demo.service.usersService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private usersService usersService;
    @Autowired
    private WeatherService weatherService;


    @GetMapping("/profile")
    public Users profile() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        return usersService.findByUsername(authentication.getName());
    }

    @GetMapping("/greeting")
    public ResponseEntity<?> greeting(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse body=weatherService.getTemp("delhi");
        if(body==null)
            return new ResponseEntity<>("Hi "+authentication.getName()+" seems like some error in fetching data. ",HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(authentication.getName()+" Temp: "+ body.getCurrent().getTempC().toString(),HttpStatus.OK);

    }

    @GetMapping("/{id}")
    private ResponseEntity<Optional<Users>> findOne(@PathVariable ObjectId id) {
        Optional<Users> usr = usersService.getOne(id);
        if (usr.isPresent())
            return new ResponseEntity<>(usr, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    private ResponseEntity<?> updateUser(@RequestBody Users usr) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usrname = authentication.getName();
        Users old = usersService.findByUsername(usrname);
        if (old != null) {
            old.setUsername(usr.getUsername());
            old.setPass(usr.getPass());
            Users val = usersService.saveUser(old);
            return new ResponseEntity<>(val, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    ResponseEntity<?> deleteUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        try{
            Users usr=usersService.deleteOne(authentication.getName());
            return new ResponseEntity<>(usr,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}