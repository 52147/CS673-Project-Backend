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

    @RequestMapping("/forget")
    @PostMapping("")
    public ResponseEntity<?> getUsername(@RequestBody User logUser) {
        User user = userService.findUserByUsername(logUser.getUsername());
        try {

            if (user == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        return ResponseEntity.ok(user);
        }
    @RequestMapping("/forget/securityQuestion")
    @PostMapping("")
    public ResponseEntity<?> securityQuestion(@RequestBody User logUser) {
        User user = userService.findUserByUsername(logUser.getUsername());
        try {
            if((user.getA1().equals(logUser.getA1())) && (user.getA2().equals(logUser.getA2()))){
                //userService.changePassword(user.getUsername(), user.getPassword(), user.getNewPassword());
                return ResponseEntity.ok(user);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/forget/changePassword")
    public void changePassword(@RequestBody User logUser, String newPassword) {
        User user = userService.findUserByUsername(logUser.getUsername());
        user.setPassword(newPassword);
        userService.save(user);
    }

}
