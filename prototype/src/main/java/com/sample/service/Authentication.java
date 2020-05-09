package com.sample.service;

import com.sample.model.User;
import org.fluentness.service.authentication.BasicAuthentication;
import org.fluentness.service.persistence.Persistence;

import static org.fluentness.service.persistence.Condition.eq;

public class Authentication extends BasicAuthentication {

    private final Persistence persistence;

    public Authentication(Persistence persistence) {
        this.persistence = persistence;
    }

    @Override
    protected boolean authorize(String username, String password) {
        return persistence.retrieve(User.class, eq("username", username), eq("password", password)).size() > 0;
    }
}
