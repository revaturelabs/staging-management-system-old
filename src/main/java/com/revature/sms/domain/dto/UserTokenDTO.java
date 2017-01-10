package com.revature.sms.domain.dto;

import com.revature.sms.domain.Token;
import com.revature.sms.domain.User;

public class UserTokenDTO {
    private User user;
    private String authToken;

    public String getToken() {
        return authToken;
    }

    public void setToken(String authToken) {
        this.authToken = authToken;
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
