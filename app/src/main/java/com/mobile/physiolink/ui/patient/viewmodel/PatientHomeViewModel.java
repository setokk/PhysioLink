package com.mobile.physiolink.ui.patient.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.physiolink.model.appointment.Appointment;
import com.mobile.physiolink.model.appointment.AppointmentBuilder;
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

public class PatientHomeViewModel extends ViewModel
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
                String res = response.body().string();
                if (res.contains(Error.RESOURCE_NOT_FOUND))
                    return;

                try
                {
                    JSONObject jsonUpcoming = new JSONObject(res).getJSONObject("appointment");
                    Appointment appointment = new AppointmentBuilder()
                            .setDate(jsonUpcoming.getString("date"))
                            .setHour(jsonUpcoming.getString("hour") + ":00")
                            .setDocCity(jsonUpcoming.getString("city"))
                            .setDocAddress(jsonUpcoming.getString("address"))
                            .setDocPostalCode(jsonUpcoming.getString("postal_code"))
                            .setMessage(jsonUpcoming.getString("message"))
                            .build();
                    upcomingAppointment.postValue(appointment);
                } catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void loadLatestCompletedAppointment(long doctorId, long patientId)
    {
        RequestFacade.getRequest(API.GET_PATIENT_LATEST_COMPLETED_APPOINTMENT +
                "doctor_id=" + doctorId +
                "&patient_id=" + patientId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                if (res.contains(Error.RESOURCE_NOT_FOUND))
                    return;

                try
                {
                    JSONObject jsonAppointment = new JSONObject(res).getJSONObject("appointment");
                    Appointment appointment = new AppointmentBuilder()
                            .setDate(jsonAppointment.getString("date"))
                            .setHour(jsonAppointment.getString("hour"))
                            .setServicePrice(jsonAppointment.getDouble("service_price"))
                            .setMessage(jsonAppointment.getString("service_title")) // TODO: CHANGE TITLE
                            .build();
                    latestCompletedAppointment.postValue(appointment);
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
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
