package com.mobile.physiolink.service.dao;

import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.service.schemas.PatientSchema;

import java.util.HashMap;

import okhttp3.Callback;

public class PatientDAO implements InterfaceDAO<Long, PatientSchema>
{
    /* Singleton Pattern */
    private static PatientDAO patientDAO = null;

    private PatientDAO() {}

    public static PatientDAO getInstance()
    {
        if (patientDAO == null)
            patientDAO = new PatientDAO();

        return patientDAO;
    }

    @Override
    public void create(PatientSchema item, Callback callback)
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
        keyValues.put("amka", item.amka);
        /* We need the doctor id to associate the patient with the doctor [1 - N] relationship */
        keyValues.put("doctor_id", String.valueOf(item.doctorId));

        RequestFacade.postRequest(API.CREATE_PATIENT, keyValues, callback);
    }

    @Override
    public void update(Long id, PatientSchema item, Callback callback)
    {
        /* Prepare HashMap for [key: "value"] in POST request body */
        HashMap<String, String> keyValues = new HashMap<>(9);
        keyValues.put("id", String.valueOf(id));
        keyValues.put("username", item.username);
        keyValues.put("password", item.password);
        keyValues.put("name", item.name);
        keyValues.put("surname", item.surname);
        keyValues.put("email", item.email);
        keyValues.put("phone_number", item.phoneNumber);
        keyValues.put("city", item.city);
        keyValues.put("address", item.address);
        keyValues.put("postal_code", item.postalCode);
        keyValues.put("amka", item.amka);
        keyValues.put("doctor_id", String.valueOf(item.doctorId));

        RequestFacade.postRequest(API.EDIT_PATIENT, keyValues, callback);
    }

    @Override
    public void delete(Long id, Callback callback)
    {

    }

    @Override
    public void get(Long id, Callback callback)
    {
        RequestFacade.getRequest(API.GET_PATIENT + id, callback);
    }
    public void getPatientsOf(Long doctorId, Callback callback)
    {
        RequestFacade.getRequest(API.GET_PATIENTS_OF + doctorId, callback);
    }
}
