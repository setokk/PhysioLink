package com.mobile.physiolink.ui.psf.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mobile.physiolink.model.user.Doctor;
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

public class ClinicsViewModel extends ViewModel {
    private MutableLiveData<List<Doctor>> doctors;

    public void loadDoctors() {
        RequestFacade.getRequest(API.GET_DOCTORS, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();

                if (res.contains((Error.RESOURCE_NOT_FOUND)));

                try {
                    JSONArray jsonDoctors = new JSONObject(res).getJSONArray("doctors");
                    List<Doctor> newDoctors = new ArrayList<>();

                    for (int i = 0; i < jsonDoctors.length(); i++) {
                        JSONObject element = jsonDoctors.getJSONObject(i);
                        Doctor doctor = new Doctor(element.getLong("id"),
                                "",
                                "doctor",
                                element.getString("name"),
                                element.getString("surname"),
                                element.getString("email"),
                                element.getString("phone_number"),
                                element.getString("afm"),
                                element.getString("city"),
                                element.getString("address"),
                                element.getString("postal_code"),
                                element.getString("physio_name"));
                        doctor.setImageURL(element.getString("image"));
                        newDoctors.add(doctor);
                    }
                    doctors.postValue(newDoctors);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public MutableLiveData<List<Doctor>> getDoctors() {
        if (doctors == null) {
            doctors = new MutableLiveData<>();
        }

        return doctors;
    }
}
