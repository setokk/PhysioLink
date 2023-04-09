package com.mobile.physiolink.businesslogic.validator;

import com.mobile.physiolink.businesslogic.user.User;

import java.util.Optional;

public class LoginInputValidator
{
    public static final String NOT_VALID = "NOT VALID";

    public static Optional<User> validateUser(String username,
                                                     String password)
    {
        /* Fetch from API */
        // User user = ...

        User user = new User(username);

        return Optional.ofNullable(user);
    }
}
