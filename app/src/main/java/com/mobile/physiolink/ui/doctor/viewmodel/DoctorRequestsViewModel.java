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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DoctorRequestsViewModel extends ViewModel
{
    private MutableLiveData<Appointment[]> requestAppointments;

    public void loadRequestAppointments(long doctorId)
    {
        RequestFacade.getRequest(API.GET_PENDING_APPOINTMENTS + doctorId, new Callback() {
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
                    JSONArray jsonAppointments = new JSONObject(res)
                            .getJSONArray("appointments");
                    Appointment[] appointments = new Appointment[jsonAppointments.length()];

                    for (int i = 0; i < jsonAppointments.length(); ++i)
                    { //TODO: CHANGE DB
                        JSONObject element = jsonAppointments.getJSONObject(i);
                        appointments[i] = new AppointmentBuilder()
                                .setId(element.getLong("appointment_id"))
                                .setDate("x/x/x")
                                .setHour("8")
                                .setPatName(element.getString("patient_name"))
                                .setPatSurname(element.getString("patient_surname"))
                                .setPatAmka(element.getString("amka"))
                                .setMessage("")
                                .build();
                    }
                    requestAppointments.postValue(appointments);
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public MutableLiveData<Appointment[]> getRequestAppointments()
    {
        if (requestAppointments == null)
            requestAppointments = new MutableLiveData<>();

        return requestAppointments;
    }
}
