package com.cs673.backend.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cs673.backend.DTO.UserDTO;
import com.cs673.backend.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chen
 * @since 2023-02-28
 */
public interface UserService extends IService<User> {

    User findUserByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    User register(UserDTO userDTO);

    //UserDTO login(UserDTO userDTO);

    //User register(UserDTO userDTO);
}
