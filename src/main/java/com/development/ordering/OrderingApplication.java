package com.development.ordering;

import com.development.ordering.model.User;
import com.development.ordering.service.UserDetailServiceImpl;
import com.development.ordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
public class OrderingApplication  {

    public static void main(String[] args) {
        SpringApplication.run(OrderingApplication.class, args);
    }

//todo global variable current user before any controller
//    @Autowired
//    private UserDetailServiceImpl userDetailService;
//
//    public User currentUser(){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String userName = ((User) ((UsernamePasswordAuthenticationToken) auth).getPrincipal()).getUsername();
//        return (User) userDetailService.loadUserByUsername(userName);
//    }

}