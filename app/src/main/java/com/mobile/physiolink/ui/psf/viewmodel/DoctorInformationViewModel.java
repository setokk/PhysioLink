package com.mobile.physiolink.ui.psf.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.physiolink.model.user.Doctor;
import com.mobile.physiolink.service.api.error.Error;
import com.mobile.physiolink.service.dao.DoctorDAO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DoctorInformationViewModel extends ViewModel
{
    private MutableLiveData<Doctor> doctor;

    public void loadDoctor(long doctorId)
    {
        DoctorDAO.getInstance().get(doctorId, new Callback() {
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
                    JSONObject jsonDoctor = new JSONObject(res).getJSONObject("doctor");
                    Doctor newDoctor = new Doctor(doctorId, jsonDoctor.getString("username"),
                            "doctor", jsonDoctor.getString("name"),
                            jsonDoctor.getString("surname"),
                            jsonDoctor.getString("email"),
                            jsonDoctor.getString("phone_number"),
                            jsonDoctor.getString("afm"),
                            jsonDoctor.getString("city"),
                            jsonDoctor.getString("address"),
                            jsonDoctor.getString("postal_code"),
                            jsonDoctor.getString("physio_name"));
                    doctor.postValue(newDoctor);
                }
                catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public MutableLiveData<Doctor> getDoctor()
    {
        if (doctor == null)
            doctor = new MutableLiveData<>();

        return doctor;
    }
}
