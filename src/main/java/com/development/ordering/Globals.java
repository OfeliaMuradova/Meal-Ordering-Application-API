package com.development.ordering;

import com.development.ordering.model.User;
import com.development.ordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Configurable
@Service
public class Globals {

    @Autowired
    UserService userService;

    public User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            String userName = ((UserDetails) auth.getPrincipal()).getUsername();
            return userService.getUserByUsername(userName);
        }
        return new User();
    }
}
