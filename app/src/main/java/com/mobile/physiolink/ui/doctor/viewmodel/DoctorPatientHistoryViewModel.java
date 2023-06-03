package com.mobile.physiolink.ui.doctor.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.physiolink.model.appointment.Appointment;
import com.mobile.physiolink.model.appointment.AppointmentBuilder;
import com.mobile.physiolink.model.user.Patient;
import com.mobile.physiolink.service.api.API;
import com.mobile.physiolink.service.api.RequestFacade;
import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.service.dao.PatientDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DoctorPatientHistoryViewModel extends ViewModel
{
    private MutableLiveData<Patient> selectedPatient;
    private MutableLiveData<Appointment[]> historyAppointments;

    public void loadPatient(long patientId)
    {
        PatientDAO.getInstance().get(patientId, new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                if (res.contains(Error.RESOURCE_NOT_FOUND))
                {
                    selectedPatient.postValue(new Patient(Error.RESOURCE_NOT_FOUND));
                    return;
                }

                try
                {
                    JSONObject jsonPatient = new JSONObject(res).getJSONObject("patient");
                    Patient patient = new Patient(patientId, jsonPatient.getString("username"),
                            "patient", jsonPatient.getString("name"),
                            jsonPatient.getString("surname"),
                            jsonPatient.getString("email"),
                            jsonPatient.getString("phone_number"),
                            jsonPatient.getString("amka"),
                            jsonPatient.getString("city"),
                            jsonPatient.getString("address"),
                            jsonPatient.getString("postal_code"),
                            jsonPatient.getLong("doctor_id"));

                    String imageURL = jsonPatient.getString("image");
                    patient.setImageURL(imageURL);

                    selectedPatient.postValue(patient);
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void loadPatientHistoryAppointments(long patientId)
    {
        RequestFacade.getRequest(API.GET_PATIENT_HISTORY_APPOINTMENTS + patientId, new Callback()
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
                    JSONArray jsonAppointments = new JSONObject(res)
                            .getJSONObject("history")
                            .getJSONArray("appointments");
                    Appointment[] appointments = new Appointment[jsonAppointments.length()];

                    for (int i = 0; i < jsonAppointments.length(); ++i)
                    {
                        JSONObject element = jsonAppointments.getJSONObject(i);
                        appointments[i] = new AppointmentBuilder()
                                .setDate(element.getString("date"))
                                .setHour(element.getString("hour"))
                                .setServiceTitle(element.getString("service_title"))
                                .setMessage(element.getString("message"))
                                .setServicePrice(element.getDouble("price"))
                                .build();
                    }
                    historyAppointments.postValue(appointments);
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public MutableLiveData<Patient> getSelectedPatient()
    {
        if (selectedPatient == null)
            selectedPatient = new MutableLiveData<>();

        return selectedPatient;
    }

    public MutableLiveData<Appointment[]> getHistoryAppointments()
    {
        if (historyAppointments == null)
            historyAppointments = new MutableLiveData<>();

        return historyAppointments;
    }
}
