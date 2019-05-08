package com.development.ordering.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

public class UserDto {
    private Long id;
    private String name;
    private String position;
    private String email;
    private String phone_number;
    private String username;
    private UserRole userRole;

    @OneToMany
    private List<Order> orders;

    public UserDto(){}

    public UserDto(String name, String position, String email, String phone_number, String username) {
        super();
        this.name = name;

        this.position = position;
        this.email = email;
        this.phone_number = phone_number;
        this.username = username;
    }

    public UserDto(String name, String position, String email, String phone_number, String username, UserRole userRole) {
        super();
        this.name = name;
        this.position = position;
        this.email = email;
        this.phone_number = phone_number;
        this.username = username;
        this.userRole = userRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String user_name) {
        this.username = user_name;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
