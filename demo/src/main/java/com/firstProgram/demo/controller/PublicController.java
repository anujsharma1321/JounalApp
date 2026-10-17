package com.firstProgram.demo.controller;

import com.firstProgram.demo.entity.UserDTO;
import com.firstProgram.demo.entity.Users;
import com.firstProgram.demo.service.CustomUserDetailsServiceImp;
import com.firstProgram.demo.service.usersService;
import com.firstProgram.demo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private usersService usersService;
    @Autowired
    private CustomUserDetailsServiceImp userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Ok";
    }

    @GetMapping("/{username}")
    private ResponseEntity<?> getUser(@PathVariable String username){
        try {
            Users usr = usersService.findByUsername(username);
            return new ResponseEntity<>(usr, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("No user found ");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signup")
    private ResponseEntity<Users> signup(@RequestBody Users usr) {
        try {
            Users saved = usersService.saveUser(usr);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Username already exist");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    private ResponseEntity<String> login(@RequestBody UserDTO usr) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usr.getUsername(),usr.getPass()));
            UserDetails userDetails=userDetailsService.loadUserByUsername(usr.getUsername());
            String jwt=jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
