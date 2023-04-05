package com.cs673.backend.DTO;
import lombok.Data;

import javax.persistence.Column;

@Data
public class UserDTO {
    private String username;
    private String password;
    private int role;
    private String phone;
    private String email;
    private String address;
    private String Q1;
    private String A1;
    private String Q2;
    private String A2;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.password = email;
    }
}
