package com.course.model;

import lombok.Data;

@Data
public class User {
    private int userId;
    private String userName;
    private int age;
    private String sex;
    private String password;
    private int deleted;

    @Override
    public String toString(){
        return(
                "userId:" + userId + "," +
                        "userName:" + userName + "," +
                        "age:" + age + "," +
                        "sex:" + sex + "," +
                        "password:" + password + "," +
                        "deleted:" + deleted
        );
    }
}
