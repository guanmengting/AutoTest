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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

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
