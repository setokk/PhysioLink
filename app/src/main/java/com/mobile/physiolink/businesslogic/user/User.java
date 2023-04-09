package com.mobile.physiolink.businesslogic.user;

import static com.mobile.physiolink.businesslogic.validator.LoginInputValidator.NOT_VALID;

import java.io.Serializable;

public class User implements Serializable
{
    private final String username;

    public User(String username)
    {
        this.username = username;
    }

    public boolean isValid()
    {
        return !username.equals(NOT_VALID);
    }
}
