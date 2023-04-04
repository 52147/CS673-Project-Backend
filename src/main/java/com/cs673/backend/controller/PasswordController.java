package com.cs673.backend.controller;
import com.cs673.backend.entity.User;
import com.cs673.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PasswordController {
    @Autowired
    private UserService userService;

    @RequestMapping("/forgotPassword")
    @PutMapping("")
    public ResponseEntity<?> changePassword(@RequestBody User loginUser) {
        try {
            User user = userService.findUserByUsername(loginUser.getUsername());

            //userService.changePassword(user.getUsername(), user.getPassword(), user.getNewPassword());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }

}
