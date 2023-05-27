package com.mobile.physiolink.ui.doctor.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.physiolink.model.appointment.Appointment;
import com.mobile.physiolink.model.appointment.AppointmentBuilder;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.service.api.error.Error;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DoctorAppointmentsViewModel extends ViewModel {

    private MutableLiveData<Appointment[]> doctorAppointment;

    public MutableLiveData<Appointment[]> getDoctorAppointment()
    {
        if (doctorAppointment == null)
            doctorAppointment = new MutableLiveData<>();

        return doctorAppointment;
    }

    public void loadDoctorAppointments(long doctorId)
    {
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(currentCalendar.getTimeInMillis());

        String currentDate = currentCalendar.get(Calendar.YEAR) + "-" +
                (currentCalendar.get(Calendar.MONTH) + 1) + "-" +
                currentCalendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(currentDate);
        RequestFacade.getRequest(API.GET_CONFIRMED_APPOINTMENTS+
                "?doctor_id=" + doctorId +
                "&date=" , new Callback()
        {
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
                    JSONArray jsonAppointments = new JSONObject(res).getJSONArray("appointments");


                    int length = jsonAppointments.length();
                    Appointment[] appointments = new Appointment[length];

                    for (int i = 0; i < jsonAppointments.length(); ++i)
                    {//TODO: GET DATE AND HOUR FROM DB
                        JSONObject element = jsonAppointments.getJSONObject(i);
                        appointments[i] = new AppointmentBuilder()
                                .setId(element.getLong("appointment_id"))
                                .setDate("x/x/x")
                                .setHour("8")
                                .setPatName(element.getString("patient_name"))
                                .setPatSurname(element.getString("patient_surname"))
                                .setPatAmka(element.getString("amka"))
                                .build();
                    }
                    doctorAppointment.postValue(appointments);
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}


