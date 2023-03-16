package com.cs673.backend.DTO;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private int role;

}
