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

    private MutableLiveData<Appointment[]> doctorAppointments;

    public MutableLiveData<Appointment[]> getDoctorAppointments()
    {
        if (doctorAppointments == null)
            doctorAppointments = new MutableLiveData<>();

        return doctorAppointments;
    }

    public void loadDoctorAppointments(long doctorId, String date)
    {
        RequestFacade.getRequest(API.GET_CONFIRMED_APPOINTMENTS +
                "?doctor_id=" + doctorId +
                "&date=" + date, new Callback()
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
                    Appointment[] appointments = new Appointment[jsonAppointments.length()];

                    for (int i = 0; i < jsonAppointments.length(); ++i)
                    {
                        JSONObject element = jsonAppointments.getJSONObject(i);
                        appointments[i] = new AppointmentBuilder()
                                .setId(element.getLong("appointment_id"))
                                .setDate(date.replace('-', '/'))
                                .setHour(element.getString("hour"))
                                .setPatName(element.getString("patient_name"))
                                .setPatSurname(element.getString("patient_surname"))
                                .setPatAmka(element.getString("amka"))
                                .setImageURL(element.getString("image"))
                                .build();
                    }
                    doctorAppointments.postValue(appointments);
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}


