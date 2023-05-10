package com.cs673.backend.controller;

import com.cs673.backend.entity.User;
import com.cs673.backend.service.UserService;
import com.cs673.backend.utils.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
@SpringBootTest
class LoginControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private LoginController loginController;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("$2a$10$czJwMkmG0tvbtp0SwSTdJODzkvD/GN.Qg/U0aZCn/mQunJhEG.Oa6");
    }

    @Test
    void login_success() {
        when(userService.findUserByUsername("testuser")).thenReturn(mockUser);
        when(jwtTokenUtil.generateToken(mockUser)).thenReturn("test-token");

        User loginUser = new User();
        loginUser.setUsername("testuser");
        loginUser.setPassword("testpassword");

        ResponseEntity<?> responseEntity = loginController.login(loginUser);

        verify(userService).findUserByUsername("testuser");
        verify(jwtTokenUtil).generateToken(mockUser);
        assert(responseEntity.getStatusCode() == HttpStatus.OK);
    }

    @Test
    void login_invalid_username() {
        when(userService.findUserByUsername("nonexistentuser")).thenReturn(null);

        User loginUser = new User();
        loginUser.setUsername("nonexistentuser");
        loginUser.setPassword("testpassword");

        ResponseEntity<?> responseEntity = loginController.login(loginUser);

        verify(userService).findUserByUsername("nonexistentuser");
        assert(responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED);
    }

    @Test
    void login_invalid_password() {
        when(userService.findUserByUsername("testuser")).thenReturn(mockUser);

        User loginUser = new User();
        loginUser.setUsername("testuser");
        loginUser.setPassword("invalidpassword");

        ResponseEntity<?> responseEntity = loginController.login(loginUser);

        verify(userService).findUserByUsername("testuser");
        assert(responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED);
    }
}