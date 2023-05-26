package com.mobile.physiolink.ui.patient.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.mobile.physiolink.model.appointment.Appointment;
import com.mobile.physiolink.model.user.Doctor;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.service.dao.DoctorDAO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PatientHomeViewModel
{
    private MutableLiveData<Doctor> doctor;
    private MutableLiveData<Appointment> upcomingAppointment;
    private MutableLiveData<Appointment> latestCompletedAppointment;


    public void loadDoctor(long doctorId)
    {
        DoctorDAO.getInstance().get(doctorId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();

                try
                {
                    if (res.contains(Error.RESOURCE_NOT_FOUND))
                        return;

                    JSONObject jsonDoctor = new JSONObject(res).getJSONObject("doctor");
                    Doctor doctorObj = new Doctor(doctorId, jsonDoctor.getString("username"),
                            "doctor", jsonDoctor.getString("name"),
                            jsonDoctor.getString("surname"),
                            jsonDoctor.getString("email"),
                            jsonDoctor.getString("phone_number"),
                            jsonDoctor.getString("afm"),
                            jsonDoctor.getString("city"),
                            jsonDoctor.getString("address"),
                            jsonDoctor.getString("postal_code"),
                            jsonDoctor.getString("physio_name"));
                    doctor.postValue(doctorObj);
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void loadUpcomingAppointment(long doctorId, long patientId)
    {
        RequestFacade.getRequest(API.GET_PATIENT_UPCOMING_APPOINTMENT +
                "doctor_id=" + doctorId +
                "&patient_id=" + patientId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public void loadLatestCompletedAppointment(long doctorId, long patientId)
    {

    }

    public MutableLiveData<Doctor> getDoctor() {
        if (doctor == null)
            doctor = new MutableLiveData<>();
        return doctor;
    }

    public MutableLiveData<Appointment> getUpcomingAppointment() {
        if (upcomingAppointment == null)
            upcomingAppointment = new MutableLiveData<>();
        return upcomingAppointment;
    }

    public MutableLiveData<Appointment> getLatestCompletedAppointment() {
        if (latestCompletedAppointment == null)
            latestCompletedAppointment = new MutableLiveData<>();
        return latestCompletedAppointment;
    }
}
