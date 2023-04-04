package com.cs673.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Getter
@Setter
@ToString
@TableName("user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @TableId(type = IdType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String username;
    @Column
    private String password;
    @Column
    private int role;
    @Column
    private String phone;
    @Column
    private String email;
    @Column
    private String address;
    @Column
    private String Q1;
    @Column
    private String A1;
    @Column
    private String Q2;
    @Column
    private String A2;
}
