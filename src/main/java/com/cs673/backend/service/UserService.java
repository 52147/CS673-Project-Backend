package com.cs673.backend.service;

import com.cs673.backend.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {
    public User findUserByUsername(String username);

    public User findByUsernameAndPassword(String username, String password);
}
