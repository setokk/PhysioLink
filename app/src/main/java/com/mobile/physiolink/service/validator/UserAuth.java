package com.mobile.physiolink.service.validator;

import com.mobile.physiolink.model.user.Doctor;
import com.mobile.physiolink.model.user.Patient;
import com.mobile.physiolink.model.user.User;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.service.api.RequestFacade;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author Kote Kostandin (setokk) <br>
 * <a href="https://www.linkedin.com/in/kostandin-kote-255382223/">LinkedIn</a>
 * <br>
 * This class is used for the credentials validation from the {@link com.mobile.physiolink.LoginActivity}
 * class.
 */
public class UserAuth
{
    /**
     * This method is used for validating the credentials that a user entered
     * and then returning a <b>VALID</b> or <b>NOT_VALID</b> user
     * @param username the username input
     * @param password the password input
     * @return {@link com.mobile.physiolink.model.user.User}
     */
    public static void sendAuthRequest(String username,
                                       String password,
                                       Callback callback)
    {
        /* Prepare Map for body [key: "value"] */
        HashMap<String, String> keyValues = new HashMap<>(2);
        keyValues.put("username", username);
        keyValues.put("password", password);

        /* Make POST request to auth api route */
        RequestFacade.postRequest(API.AUTH_USER, keyValues, callback);
    }

    public static User prepareAndGetUser(Response response,
                                         String username)
            throws JSONException, IOException
    {
        User user = null;
        JSONObject json = new JSONObject(response.body().string());
        if (validCredentials(json))
        {
            long id = json.getLong("id");
            String type = json.getString("role");

            user = new User(id, username, type);
            if (!user.isPSF()) // Either doctor or patient
            {
                /* Common fields from JSON response */
                String name = json.getString("name");
                String surname = json.getString("surname");
                String email = json.getString("email");
                String phoneNumber = json.getString("phone_number");
                String city = json.getString("city");
                String address = json.getString("address");
                String postalCode = json.getString("postal_code");
                String imageURL = json.getString("image");

                /* Uncommon fields */
                if (user.isDoctor())
                {
                    String afm = json.getString("afm");
                    String physioName = json.getString("physio_name");

                    user = new Doctor(id, username, type, name, surname, email,
                            phoneNumber, afm, city, address, postalCode, physioName);
                }
                else if (user.isPatient())
                {
                    String amka = json.getString("amka");
                    long doctor_id = Long.parseLong(json.getString("doctor_id"));

                    user = new Patient(id, username, type, name, surname, email,
                            phoneNumber, amka, city, address, postalCode, doctor_id);
                }

                user.setImageURL(imageURL);
            }
        }

        return Optional.ofNullable(user)
                .orElse(new User(Error.INVALID_CREDENTIALS));
    }


    public static boolean validCredentials(JSONObject json)
    {
        // message: "Invalid credentials" JSON response -> not valid credentials
        return !json.toString().contains(Error.INVALID_CREDENTIALS);
    }
}
