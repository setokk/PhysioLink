package com.mobile.physiolink.model.validator;

import com.mobile.physiolink.model.user.User;
import com.mobile.physiolink.service.api.UserAuthenticator;

import java.util.Optional;

/**
 * @author Kote Kostandin (setokk) <br>
 * <a href="https://www.linkedin.com/in/kostandin-kote-255382223/">LinkedIn</a>
 * <br>
 * This class is used for the credentials validation from the {@link com.mobile.physiolink.LoginActivity}
 * class.
 */
public class LoginInputValidator
{
    public static final String NOT_VALID = "NOT VALID";

    /**
     * This method is used for validating the credentials that a user entered
     * and then returning a <b>VALID</b> or <b>NOT_VALID</b> user
     * @param username the username input
     * @param password the password input
     * @return {@link com.mobile.physiolink.model.user.User}
     */
    public static User validateUser(String username,
                                    String password)
    {
        /* Fetch from API */
        User user = UserAuthenticator.authenticateUser(username, password);

        return Optional.ofNullable(user)
                .orElse(new User(NOT_VALID));
    }
}
