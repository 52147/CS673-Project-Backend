package com.cs673.backend.controller;

import com.cs673.backend.entity.User;
import com.cs673.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ResetPasswordControllerTest {

    @Mock
    private UserService userService;

    @Test
    public void testSecurityQuestionWithValidAnswers() {
        // given
        PasswordController passwordController = new PasswordController();
        passwordController.userService = userService;
        User user = new User();
        user.setUsername("testuser");
        user.setA1("answer1");
        user.setA2("answer2");
        when(userService.findUserByUsername(anyString())).thenReturn(user);
        User logUser = new User();
        logUser.setUsername("testuser");
        logUser.setA1("answer1");
        logUser.setA2("answer2");

        // when
        ResponseEntity<?> responseEntity = passwordController.securityQuestion(logUser);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
    }

    @Test
    public void testSecurityQuestionWithInvalidAnswers() {
        // given
        PasswordController passwordController = new PasswordController();
        passwordController.userService = userService;
        when(userService.findUserByUsername(anyString())).thenReturn(null);
        User logUser = new User();
        logUser.setUsername("nonexistentuser");
        logUser.setA1("answer1");
        logUser.setA2("answer2");

        // when
        ResponseEntity<?> responseEntity = passwordController.securityQuestion(logUser);

        // then
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("No user", responseEntity.getBody());
    }

    @Test
    public void testChangePassword() {
        // given
        PasswordController passwordController = new PasswordController();
        passwordController.userService = userService;
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("oldpassword");
        when(userService.findUserByUsername(anyString())).thenReturn(user);
        User logUser = new User();
        logUser.setUsername("testuser");
        String newPassword = "newpassword";

        // when
        passwordController.changePassword(logUser, newPassword);

        // then
        assertEquals(newPassword, user.getPassword());
    }
}