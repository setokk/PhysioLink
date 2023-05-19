package com.mobile.physiolink.service.dao;

import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.service.schemas.DoctorSchema;

import java.util.HashMap;

import okhttp3.Callback;

public class DoctorDAO implements InterfaceDAO<Long, DoctorSchema>
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
    public void create(DoctorSchema item, Callback callback)
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
        keyValues.put("city", item.city);
        keyValues.put("address", item.address);
        keyValues.put("postal_code", item.postalCode);
        keyValues.put("afm", item.afm);
        keyValues.put("physio_name", item.physioName);

        RequestFacade.postRequest(API.CREATE_DOCTOR, keyValues, callback);
    }

    @Override
    public void update(Long id, DoctorSchema item, Callback callback)
    {

    }

    @Override
    public void delete(Long id, Callback callback)
    {

    }

    @Override
    public void get(Long id, Callback callback)
    {
        RequestFacade.getRequest(API.GET_DOCTOR + id, callback);
    }
}
