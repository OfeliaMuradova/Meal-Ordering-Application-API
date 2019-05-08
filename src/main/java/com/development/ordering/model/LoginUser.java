package com.development.ordering.model;

public class LoginUser {

    private String username;
    private String password;
    private String role;
    private AuthToken token;

    public LoginUser(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }

    public AuthToken getToken(){
        return token;
    }

    public void setToken(AuthToken token){
        this.token = token;
    }
}
