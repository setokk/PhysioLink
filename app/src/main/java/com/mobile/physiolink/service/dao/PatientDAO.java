package com.mobile.physiolink.service.dao;

import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.service.schemas.PatientSchema;

import java.io.IOException;
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
        keyValues.put("address", item.address);
        keyValues.put("amka", item.amka);
        /* We need the doctor id to associate the patient with the doctor [1 - N] relationship */
        keyValues.put("doctor_id", String.valueOf(item.doctorId));

        RequestFacade.postRequest(API.CREATE_PATIENT, keyValues, callback);
    }

    @Override
    public void update(Long id, PatientSchema item, Callback callback)
    {

    }

    @Override
    public void delete(Long id, Callback callback)
    {

    }

    @Override
    public void get(Long id, Callback callback)
    {
            RequestFacade.getRequest(API.GET_PATIENT + id, callback);

            /*JSONObject json = new JSONObject(response);
            if (json.toString().contains(Error.RESOURCE_NOT_FOUND))
                return new Patient(Error.RESOURCE_NOT_FOUND);

            JSONObject patient = json.getJSONObject("patient");
            return new Patient(patient.getLong("id"),
                    patient.getString("username"), "patient",
                    patient.getString("name"),
                    patient.getString("surname"),
                    patient.getString("email"),
                    patient.getString("phone_number"),
                    patient.getString("amka"),
                    patient.getString("address"),
                    patient.getLong("doctor_id"));

        } catch (IOException | JSONException e) {
            Log.i("ERROR", e.getMessage());
            return new Patient(Error.RESOURCE_NOT_FOUND);
        }*/
    }
    public void getPatientsOf(Long doctorId, Callback callback)
    {
        RequestFacade.getRequest(API.GET_PATIENTS_OF + doctorId, callback);
        /* Check if server sent back that this doctor has no patients */
        /*JSONObject res = new JSONObject(response);
        if (res.toString().contains(Error.RESOURCE_NOT_FOUND))
            return new Patient[0];

        *//* Get JSON patients array and return a Patient array *//*
        JSONArray array = res.getJSONArray("patients");
        Patient[] patients = new Patient[array.length()];
        for (int i = 0; i < array.length(); i++)
        {
            JSONObject element = array.getJSONObject(i);
            patients[i] = new Patient(element.getLong("id"),
                    element.getString("username"),
                    "patient",
                    element.getString("name"),
                    element.getString("surname"),
                    element.getString("email"),
                    element.getString("phone_number"),
                    element.getString("amka"),
                    element.getString("address"),
                    doctorId);
        }

        return patients;*/
    }
}
