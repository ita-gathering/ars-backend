package com.ars.po;

import lombok.Data;
import org.springframework.data.annotation.Id;


/**
 * Created by guowanyi on 2019/3/12.
 */
@Data
public class User {
    @Id
    private String id;
    private String userName;
    private String password;

    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
