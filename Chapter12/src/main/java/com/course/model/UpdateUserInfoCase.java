package com.course.model;

import lombok.Data;

@Data
public class UpdateUserInfoCase {
    private int caseId;
    private int userId;
    private String userName;
    private int age;
    private String sex;
    private String password;
    private int deleted;

    private String expected;
}
