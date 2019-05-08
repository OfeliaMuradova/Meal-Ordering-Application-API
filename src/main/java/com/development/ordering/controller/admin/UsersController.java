package com.development.ordering.controller.admin;

import com.development.ordering.model.User;
import com.development.ordering.model.UserDto;
import com.development.ordering.model.UserRole;
import com.development.ordering.repository.UserRoleRepository;
import com.development.ordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List listUser(){
        return userService.findAll();
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public UserDto getOne(@PathVariable(value = "id") Long id){
        return userService.convertToDto(userService.getUserById(id));
    }

    @RequestMapping(value="/", method = RequestMethod.POST)
    public User createUser(@RequestBody User user) throws Exception{
        try {
            return userService.userCreate(user);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Username or email already exists");
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public User updateUser(@RequestBody UserDto userDto, @PathVariable(value = "id") Long id) throws Exception{
        try {
            User user = userService.convertToEntity(userDto);
            return userService.userSave(user);
        }catch (NullPointerException e){
            throw new NullPointerException("Not Found Object");
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Username or email already exists");
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public boolean deleteUser(@PathVariable(value = "id") Long id){
        try {
            userService.deleteUser(id);
            return true;
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Delete is impossible, user has orders");
        }
    }

    @RequestMapping(value = "/user_roles", method = RequestMethod.GET)
    public List<UserRole> getUserRoles(){
        return (List<UserRole>) userRoleRepository.findAll();
    }
}
