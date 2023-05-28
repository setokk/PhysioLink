package com.mobile.physiolink.ui.doctor.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.physiolink.model.appointment.Appointment;
import com.mobile.physiolink.model.appointment.AppointmentBuilder;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.util.DateFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DoctorHomeViewModel extends ViewModel
{
    private static final int MAX_APPOINTMENTS = 3;
    private MutableLiveData<Appointment[]> latestAppointments;

    public void loadTodaysLatestAppointments(long doctorId)
    {
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(currentCalendar.getTimeInMillis());

        String currentDate = currentCalendar.get(Calendar.YEAR) + "-" +
                (currentCalendar.get(Calendar.MONTH) + 1) + "-" +
                currentCalendar.get(Calendar.DAY_OF_MONTH);
        System.out.println(currentDate);
        RequestFacade.getRequest(API.GET_CONFIRMED_LATEST_APPOINTMENTS +
                "?doctor_id=" + doctorId +
                "&date=" + currentDate, new Callback()
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

                    int length = MAX_APPOINTMENTS;
                    if (jsonAppointments.length() < 3)
                        length = jsonAppointments.length();
                    Appointment[] appointments = new Appointment[length];

                    for (int i = 0; i < length; ++i)
                    {//TODO: GET DATE AND HOUR FROM DB
                        JSONObject element = jsonAppointments.getJSONObject(i);
                        appointments[i] = new AppointmentBuilder()
                                .setId(element.getLong("appointment_id"))
                                .setDate(currentDate.replace('-', '/'))
                                .setHour("8")
                                .setPatName(element.getString("patient_name"))
                                .setPatSurname(element.getString("patient_surname"))
                                .setPatAmka(element.getString("amka"))
                                .build();
                    }
                    latestAppointments.postValue(appointments);
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public MutableLiveData<Appointment[]> getLatestAppointments()
    {
        if (latestAppointments == null)
            latestAppointments = new MutableLiveData<>();

        return latestAppointments;
    }
}
