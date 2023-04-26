package com.cs673.backend.controller;

import com.cs673.backend.entity.User;
import com.cs673.backend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserService userService;
    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole(1);

        boolean result = userService.saveOrUpdate(user);

        assertEquals(true, result);

        // clean up after the test
        userService.removeById(user.getId());

    }

    @Test
    public void testGetUserById() {
        // create a new user
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole(1);
        userService.saveOrUpdate(user);

        // retrieve the user by ID
        User retrievedUser = userService.getById(user.getId());

        // assert that the retrieved user matches the original user
        assertEquals(user.getUsername(), retrievedUser.getUsername());
        assertEquals(user.getPassword(), retrievedUser.getPassword());
        assertEquals(user.getRole(), retrievedUser.getRole());

        // clean up after the test
        userService.removeById(user.getId());
    }


    @Test
    public void testDeleteUserById() {
        // create a new user
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole(1);
        userService.saveOrUpdate(user);

        // delete the user by ID
        boolean result = userService.removeById(user.getId());

        // assert that the user was successfully deleted
        assertEquals(true, result);

        // try to retrieve the deleted user by ID
        User deletedUser = userService.getById(user.getId());

        // assert that the deleted user is null
        assertNull(deletedUser);
    }

    @Test
    public void testGetUsersByUsername() {
        // create some test users
        User user1 = new User();
        user1.setUsername("account1");
        user1.setPassword("password");
        user1.setRole(1);
        userService.saveOrUpdate(user1);

        User user2 = new User();
        user2.setUsername("account2");
        user2.setPassword("password");
        user2.setRole(1);
        userService.saveOrUpdate(user2);

        // retrieve the users by username
        List<User> userList = userService.getUserByName("account1");

        // assert that the correct number of users were retrieved
        assertEquals(2, userList.size());

        // clean up after the test
        userService.removeById(user1.getId());
        userService.removeById(user2.getId());
    }

//    @Test
//    public void testAddExistingUser() {
//        // create a new user with existing username
//        User user = new User();
//        user.setUsername("testuser456");
//        user.setPassword("password");
//        user.setRole(1);
//        userService.saveOrUpdate(user);
//
//        // create another user with the same username
//        User user2 = new User();
//        user2.setUsername("testuser456");
//        user2.setPassword("password2");
//        user2.setRole(2);
//
//        // attempt to add the second user with the same username
//        boolean result = userService.saveOrUpdate(user2);
//
//        // assert that the user was not added and the result is false
//        assertEquals(false, result);
//
//        // clean up after the test
//        userService.removeById(user.getId());
//    }

}
