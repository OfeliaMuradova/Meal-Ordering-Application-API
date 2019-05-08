package com.development.ordering.service;

import com.development.ordering.model.User;
import com.development.ordering.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserAccountService implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(userName);

        if (user == null){
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        return (UserDetails) user;
    }
}
