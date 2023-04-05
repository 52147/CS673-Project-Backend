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
    public ResponseEntity<?> securityQuestion(@RequestParam("username") String logUser, String A1, String A2) {
        User user = userService.findUserByUsername(logUser);
        try {

            if (user == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user");
            }
            if((user.getA1().equals(A1)) && (user.getA2().equals(A2))){
                //userService.changePassword(user.getUsername(), user.getPassword(), user.getNewPassword());
                return ResponseEntity.ok(user);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        return ResponseEntity.ok(user);
        }
    @GetMapping("/forget/changePassword")
    public void changePassword(@RequestParam("username") String logUser, String newPassword) {
        User user = userService.findUserByUsername(logUser);
        user.setPassword(newPassword);
        userService.save(user);
    }

}
