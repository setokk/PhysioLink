package com.mobile.physiolink.service.api;

import com.mobile.physiolink.model.user.Doctor;
import com.mobile.physiolink.model.user.PSF;
import com.mobile.physiolink.model.user.Patient;
import com.mobile.physiolink.model.user.User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class UserAuthenticator
{
    public static User authenticateUser(String username, String password)
    {
        User user = null;

        /* Prepare Map for body key: value */
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
                String type = json.getString("type");

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
                    String phoneNumber = json.getString("phoneNumber");
                    String afm = json.getString("afm");

                    user = new Doctor(id, username, type, name, surname, email, phoneNumber, afm);
                }
                else if (user.isPatient())
                {
                    String name = json.getString("name");
                    String surname = json.getString("surname");
                    String email = json.getString("email");
                    String phoneNumber = json.getString("phoneNumber");
                    String amka = json.getString("amka");

                    user = new Patient(id, username, type, name, surname, email, phoneNumber, amka);
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return user;
    }

    private static boolean validCredentials(JSONObject json)
    {
        // Empty JSON response = not valid credentials
        return !json.toString().equals("{}");
    }
}
