package com.cs673.backend.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginData {
    private String username;
    private String password;

    public LoginData(@JsonProperty("username") String username,
                     @JsonProperty("password") String password) {
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

    public String toString() {
        return "LoginData [username=" + username + ", password=" + password + "]";
    }
}
