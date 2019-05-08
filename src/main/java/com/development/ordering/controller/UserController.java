package com.development.ordering.controller;

import com.development.ordering.model.User;
import com.development.ordering.model.UserDto;
import com.development.ordering.service.UserDetailServiceImpl;
import com.development.ordering.service.UserService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public User getOne(@PathVariable(value = "id") Long id){
        return userService.getUserById(id);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public User saveUser(@RequestBody UserDto userDto, @PathVariable(value = "id") Long id) throws Exception{
        try{
            User user = userService.convertToEntity(userDto);
            return userService.userSave(user);
        }catch (DataIntegrityViolationException e)
        {
            throw new DataIntegrityViolationException("Username or email already exists");
        }
    }

    @RequestMapping(value="/changePassword/{id}", method = RequestMethod.POST)
    public User changePassword(@RequestBody Map<String, Object> payload, @PathVariable long id) throws Exception{
        return userService.changePassword(payload.get("oldPassword").toString(), payload.get("newPassword").toString(), id);
    }
}