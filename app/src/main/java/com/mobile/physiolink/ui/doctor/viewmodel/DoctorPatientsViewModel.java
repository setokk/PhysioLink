package com.mobile.physiolink.ui.doctor.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.physiolink.model.user.Patient;
import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.service.dao.PatientDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DoctorPatientsViewModel extends ViewModel
{
    private MutableLiveData<Patient[]> doctorPatients;

    public MutableLiveData<Patient[]> getDoctorPatients()
    {
        if (doctorPatients == null)
            doctorPatients = new MutableLiveData<>();

        return doctorPatients;
    }

    public void loadDoctorPatients(long doctorId)
    {
        PatientDAO.getInstance().getPatientsOf(doctorId, new Callback()
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
                    JSONArray jsonPatients = new JSONObject(res).getJSONArray("patients");
                    Patient[] patients = new Patient[jsonPatients.length()];
                    for (int i = 0; i < jsonPatients.length(); ++i)
                    {
                        JSONObject element = jsonPatients.getJSONObject(i);
                        patients[i] = new Patient(element.getLong("id"),
                                element.getString("username"),
                                "patient", element.getString("name"),
                                element.getString("surname"),
                                element.getString("email"),
                                element.getString("phone_number"),
                                element.getString("amka"),
                                element.getString("city"),
                                element.getString("address"),
                                element.getString("postal_code"),
                                doctorId);
                    }
                    doctorPatients.postValue(patients);
                } catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }


            }
        });
    }
}
