package com.mobile.physiolink.service.api.validator;

import com.mobile.physiolink.model.user.Doctor;
import com.mobile.physiolink.model.user.PSF;
import com.mobile.physiolink.model.user.Patient;
import com.mobile.physiolink.model.user.User;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Kote Kostandin (setokk) <br>
 * <a href="https://www.linkedin.com/in/kostandin-kote-255382223/">LinkedIn</a>
 * <br>
 * This class is used for the credentials validation from the {@link com.mobile.physiolink.LoginActivity}
 * class.
 */
public class UserAuth
{
    public static final String NOT_VALID = "NOT VALID";

    /**
     * This method is used for validating the credentials that a user entered
     * and then returning a <b>VALID</b> or <b>NOT_VALID</b> user
     * @param username the username input
     * @param password the password input
     * @return {@link com.mobile.physiolink.model.user.User}
     */
    public static User authenticateUser(String username, String password)
    {
        User user = null;

        /* Prepare Map for body [key: "value"] */
        HashMap<String, String> keyValues = new HashMap<>(2);
        keyValues.put("username", username);
        keyValues.put("password", password);

        /* Make POST request to auth api route */
        try
        {
            String response = Objects.requireNonNull(RequestFacade.postRequest(API.AUTH_USER, keyValues)
                            .body())
                    .string();
            JSONObject json = new JSONObject(response);

            if (validCredentials(json)) // If not valid, return null
            {
                long id = json.getLong("id");
                String type = json.getString("role");

                /* Create User superclass and check the type of user*/
                user = new User(id, username, type);
                if (user.isPSF())
                {
                    user = new PSF(id, username, type);
                }
                else if (user.isDoctor())
                {
                    String name = json.getString("name");
                    String surname = json.getString("surname");
                    String email = json.getString("email");
                    String phoneNumber = json.getString("phone_number");
                    String afm = json.getString("afm");

                    user = new Doctor(id, username, type, name, surname, email, phoneNumber, afm);
                }
                else if (user.isPatient())
                {
                    String name = json.getString("name");
                    String surname = json.getString("surname");
                    String email = json.getString("email");
                    String phoneNumber = json.getString("phone_number");
                    String amka = json.getString("amka");

                    user = new Patient(id, username, type, name, surname, email, phoneNumber, amka);
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(user)
                .orElse(new User(NOT_VALID));
    }

    private static boolean validCredentials(JSONObject json)
    {
        // message: "Invalid credentials" JSON response -> not valid credentials
        return !json.toString().contains("Invalid credentials");
    }
}
