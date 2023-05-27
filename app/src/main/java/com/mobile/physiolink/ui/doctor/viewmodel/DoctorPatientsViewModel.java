package com.mobile.physiolink.ui.doctor.viewmodel;

import androidx.lifecycle.MutableLiveData;

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

public class DoctorPatientsViewModel
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

                    }

                } catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }


            }
        });
    }
}
