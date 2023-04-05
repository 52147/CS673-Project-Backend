package com.cs673.backend.controller;
import com.cs673.backend.entity.User;
import com.cs673.backend.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class EmailController {

    @Autowired
    private UserService userService;

 /*   @Autowired
    private MailService mailService;*/

    @RequestMapping("/forget")
    @PutMapping("")
    public void forgotPassword(@RequestParam("email") String email) throws Exception {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }
        if(user.getEmail().equals(email)) {

/*        String resetToken = userService.generateResetToken(user);
        String resetLink = "http://example.com/reset-password?token=" + resetToken;

        String subject = "Password reset request";
        String text = "Click the link below to reset your password: " + resetLink;

        mailService.sendEmail(user.getEmail(), subject, text);*/
        }
    }
}