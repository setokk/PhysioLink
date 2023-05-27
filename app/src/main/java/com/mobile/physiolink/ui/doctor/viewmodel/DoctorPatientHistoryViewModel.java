package com.mobile.physiolink.ui.doctor.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.physiolink.model.user.Patient;
import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.service.dao.PatientDAO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DoctorPatientHistoryViewModel extends ViewModel
{
    private MutableLiveData<Patient> selectedPatient;

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
                    selectedPatient.postValue(patient);
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
}
