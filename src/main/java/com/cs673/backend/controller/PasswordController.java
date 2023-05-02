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
    UserService userService;

    @RequestMapping("/forget")
    @PostMapping("")
    public ResponseEntity<?> securityQuestion(@RequestBody User logUser) {
        User user = userService.findUserByUsername(logUser.getUsername());
        try {

            if (user == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user");
            }
            if((user.getA1().equals(logUser.getA1())) && (user.getA2().equals(logUser.getA2()))){
                return ResponseEntity.ok(user);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        return ResponseEntity.ok(user);
        }
    @PostMapping("/forget/changePassword")
    public void changePassword(@RequestBody User logUser, @RequestParam(required = true) String newPassword) {
        System.out.println("newpass = " + newPassword);
        if (newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("New password cannot be empty.");
        }
        User user = userService.findUserByUsername(logUser.getUsername());
        try {
            user.setPassword(newPassword);
            userService.saveOrUpdate(user);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while changing the password. Please try again later.");
        }

    }

}
