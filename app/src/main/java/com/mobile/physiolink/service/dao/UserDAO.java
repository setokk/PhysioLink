package com.mobile.physiolink.service.dao;

import com.mobile.physiolink.model.user.User;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.service.schemas.DoctorSchema;
import com.mobile.physiolink.service.schemas.PatientSchema;
import com.mobile.physiolink.service.schemas.UserSchema;

import java.io.IOException;
import java.util.HashMap;

public class UserDAO implements InterfaceDAO<Long, UserSchema, User>
{
    /* Singleton Pattern */
    private static UserDAO userDAO = null;

    private UserDAO() {}

    public static UserDAO getInstance()
    {
        if (userDAO == null)
            userDAO = new UserDAO();

        return userDAO;
    }

    @Override
    public void create(UserSchema user) throws IOException
    {
        /* Can't allow creation of PSF users */

        /* Prepare HashMap for [key: "value"] in POST request body
        *  The id of the users auto-increments */
        String url = "";
        HashMap<String, String> keyValues = new HashMap<>(7);
        keyValues.put("username", user.username);
        keyValues.put("password", user.password);
        keyValues.put("name", user.name);
        keyValues.put("surname", user.surname);
        keyValues.put("email", user.email);
        keyValues.put("phone_number", user.phoneNumber);
        if (user instanceof DoctorSchema)
        {
            keyValues.put("afm", ((DoctorSchema) user).afm);

            url = API.CREATE_DOCTOR;
        }
        else if (user instanceof PatientSchema)
        {
            keyValues.put("amka", ((PatientSchema) user).amka);
            /* We need the doctor id to associate the patient with the doctor
                                [1 - 1 relationship] */
            keyValues.put("doctor_id", String.valueOf(((PatientSchema) user).doctor_id));

            url = API.CREATE_PATIENT;
        }

        RequestFacade.postRequest(url, keyValues);
    }

    @Override
    public void update(Long id, UserSchema user)
    {

    }

    @Override
    public void delete(Long id)
    {

    }

    @Override
    public User get(Long id)
    {
        return null;
    }
}
