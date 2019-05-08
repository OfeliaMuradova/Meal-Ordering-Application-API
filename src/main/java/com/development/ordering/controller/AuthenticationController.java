package com.development.ordering.controller;

import com.development.ordering.config.TokenProvider;
import com.development.ordering.model.AuthToken;
import com.development.ordering.model.LoginUser;
import com.development.ordering.model.User;
import com.development.ordering.service.UserDetailServiceImpl;
import com.development.ordering.service.UserService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody LoginUser loginUser) throws AuthenticationException {

//         final Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginUser.getUsername(),
//                        loginUser.getPassword()
//                )
//         );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        final String token = jwtTokenUtil.generateToken(authentication);
//        User user = userService.getUserByUsername(loginUser.getUsername());
//        user.setToken(token);

        return ResponseEntity.ok(userService.login(loginUser));
    }

//    @RequestMapping(value = "/logout", method = RequestMethod.POST)
//    public ResponseEntity<?> logout(@RequestBody LoginUser loginUser) throws  AuthenticationException {
//        return ResponseEntity.ok(true);
//    }
}
