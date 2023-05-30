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
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DoctorPatientsViewModel extends ViewModel
{
    private MutableLiveData<List<Patient>> doctorPatients;

    public MutableLiveData<List<Patient>> getDoctorPatients()
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
                    List<Patient> patients = new ArrayList<>();
                    for (int i = 0; i < jsonPatients.length(); ++i)
                    {
                        JSONObject element = jsonPatients.getJSONObject(i);
                        patients.add(new Patient(element.getLong("id"),
                                element.getString("username"),
                                "patient", element.getString("name"),
                                element.getString("surname"),
                                element.getString("email"),
                                element.getString("phone_number"),
                                element.getString("amka"),
                                element.getString("city"),
                                element.getString("address"),
                                element.getString("postal_code"), doctorId));
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
