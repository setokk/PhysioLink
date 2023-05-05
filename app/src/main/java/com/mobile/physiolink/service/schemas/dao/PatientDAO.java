package com.mobile.physiolink.service.schemas.dao;

import com.mobile.physiolink.model.user.Patient;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.Error;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.service.schemas.PatientSchema;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class PatientDAO implements InterfaceDAO<Long, PatientSchema, Patient>
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
    public void create(PatientSchema item) throws IOException
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

        RequestFacade.postRequest(API.CREATE_PATIENT, keyValues);
    }

    @Override
    public void update(Long id, PatientSchema item)
    {

    }

    @Override
    public void delete(Long id)
    {

    }

    @Override
    public Patient get(Long id)
    {
        return null;
    }

    public Patient[] getPatientsOf(Long doctorId) throws IOException, JSONException
    {
        String response = RequestFacade.getRequest(API.GET_PATIENTS_OF + doctorId)
                .body().string();

        /* Check if server sent back that this doctor has no patients */
        JSONObject res = new JSONObject(response);
        if (res.toString().contains(Error.PATIENTS_NOT_FOUND))
            return new Patient[0];

        /* Get JSON patients array and return a Patient array */
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

        return patients;
    }
}
