package com.firstProgram.demo.service;

import com.firstProgram.demo.entity.Users;
import com.firstProgram.demo.repository.usersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private usersRepository usersRepository;
     public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
         Users usr=usersRepository.findByUsername(username);
         if(usr!=null) {
             UserDetails userDetail = org.springframework.security.core.userdetails.User.builder()
                     .username(usr.getUsername())
                     .password(usr.getPass())
                     .roles(usr.getRoles().toArray(new String[0])).build();
             return userDetail;
         }
         throw new UsernameNotFoundException("User Name not found: "+username);
    }
}
