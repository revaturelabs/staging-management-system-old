package com.revature.sms.domain.dto;

import com.revature.sms.domain.Token;
import com.revature.sms.domain.User;

public class UserTokenDTO {
    private User user;
    private Token token;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
