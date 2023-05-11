package com.mobile.physiolink.service.dao;

import android.util.Log;

import com.mobile.physiolink.model.user.Doctor;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.service.schemas.DoctorSchema;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class DoctorDAO implements InterfaceDAO<Long, DoctorSchema, Doctor>
{
    /* Singleton Pattern */
    private static DoctorDAO doctorDAO = null;

    private DoctorDAO() {}

    public static DoctorDAO getInstance()
    {
        if (doctorDAO == null)
            doctorDAO = new DoctorDAO();

        return doctorDAO;
    }

    @Override
    public void create(DoctorSchema item) throws IOException
    {
        /* Prepare HashMap for [key: "value"] in POST request body
         *  The id of the users auto-increments */
        HashMap<String, String> keyValues = new HashMap<>(9);
        keyValues.put("username", item.username);
        keyValues.put("password", item.password);
        keyValues.put("name", item.name);
        keyValues.put("surname", item.surname);
        keyValues.put("email", item.email);
        keyValues.put("phone_number", item.phoneNumber);
        keyValues.put("address", item.address);
        keyValues.put("afm", item.afm);
        keyValues.put("physio_name", item.physioName);

        RequestFacade.postRequest(API.CREATE_DOCTOR, keyValues);
    }

    @Override
    public void update(Long id, DoctorSchema item)
    {

    }

    @Override
    public void delete(Long id)
    {

    }

    @Override
    public Doctor get(Long id)
    {
        try {
            String response = RequestFacade.getRequest(API.GET_DOCTOR + id).body()
                    .string();
            JSONObject json = new JSONObject(response);
            if (json.toString().contains(Error.RESOURCE_NOT_FOUND))
                return new Doctor();

            JSONObject doctor = json.getJSONObject("doctor");
            return new Doctor(id,
                    doctor.getString("username"), "doctor",
                    doctor.getString("name"),
                    doctor.getString("surname"),
                    doctor.getString("email"),
                    doctor.getString("phone_number"),
                    doctor.getString("afm"),
                    doctor.getString("address"),
                    doctor.getString("physio_name"));

        } catch (IOException | JSONException e) {
            Log.i("ERROR", e.getMessage());
            return new Doctor();
        }
    }
}
