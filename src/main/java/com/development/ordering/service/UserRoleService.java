package com.development.ordering.service;

import com.development.ordering.model.UserRole;
import com.development.ordering.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<UserRole> getAllUserRoles(){
        List<UserRole> userRoles = new ArrayList<>();
        userRoleRepository.findAll().forEach(userRoles::add);

        return userRoles;
    }

    public void addOrUpdateUserRole(UserRole userRole) {
        userRoleRepository.save(userRole);
    }

    public UserRole getUserRoleByName(String roleName){
        return userRoleRepository.findByName(roleName);
    }
}
