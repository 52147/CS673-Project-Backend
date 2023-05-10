package com.cs673.backend.controller;

import cn.hutool.crypto.digest.BCrypt;
import com.cs673.backend.entity.User;
import com.cs673.backend.service.UserService;
import com.cs673.backend.utils.JwtResponse;
import com.cs673.backend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @RequestMapping("/login")
    @PostMapping
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        User user = userService.findUserByUsername(loginUser.getUsername());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        if (!BCrypt.checkpw(loginUser.getPassword(), user.getPassword())) {
            if (!user.getPassword().equals(loginUser.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
        }


        String token = jwtTokenUtil.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
