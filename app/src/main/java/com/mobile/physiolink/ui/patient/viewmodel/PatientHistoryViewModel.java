package com.mobile.physiolink.ui.patient.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.physiolink.model.appointment.Appointment;
import com.mobile.physiolink.model.appointment.AppointmentBuilder;
import com.mobile.physiolink.model.service.Service;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.service.api.error.Error;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PatientHistoryViewModel extends ViewModel {

    private MutableLiveData<List<Appointment>> appointments;
    private MutableLiveData<Double> totalPayment;

    public void loadAppointments(long patientId){
        RequestFacade.getRequest(API.GET_PATIENT_HISTORY_APPOINTMENTS + patientId, new Callback() {
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
                    JSONObject jsonHistory = new JSONObject(res).getJSONObject("history");
                    JSONArray jsonAppointments = jsonHistory.getJSONArray("appointments");
                    Double jsonTotalPayment = jsonHistory.getDouble("total_payment");
                    List<Appointment> appointmentsFinal = new ArrayList<>();

                    for(int i=0;i<jsonAppointments.length();i++){
                        JSONObject element = jsonAppointments.getJSONObject(i);

                        appointmentsFinal.add(new AppointmentBuilder()
                                .setDate(element.getString("date"))
                                .setHour(element.getString("hour"))
                                .setServiceTitle(element.getString("service_title"))
                                .setMessage(element.getString("message"))
                                .setServicePrice(element.getDouble("price"))
                                .build());
                    }
                    appointments.postValue(appointmentsFinal);
                    totalPayment.postValue(jsonTotalPayment);
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public MutableLiveData<List<Appointment>> getAppointments() {
        if (appointments == null)
            appointments = new MutableLiveData<>();
        return appointments;
    }

    public MutableLiveData<Double> getTotalPayment(){
        if(totalPayment == null)
            totalPayment = new MutableLiveData<>();
        return totalPayment;
    }
}
